import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InventoryManagement extends JFrame {

    private JTextField txtProdID, txtProdName, txtQuantity, txtCostPrice, txtSellPrice, txtProfit;
    private JButton btnAdd, btnUpdate, btnDelete;
    private JTable table;
    private DefaultTableModel model;
    private Connection con;
    private PreparedStatement pst;

    public InventoryManagement() {
        setTitle("Inventory Management System");
        setSize(800, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table Panel
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Product ID", "Product Name", "Quantity", "Cost Price", "Sell Price", "Profit"});
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.NORTH);

        // **Input Panel with Centered Fields**
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtProdID = new JTextField(10);
        txtProdName = new JTextField(10);
        txtQuantity = new JTextField(5);
        txtCostPrice = new JTextField(7);
        txtSellPrice = new JTextField(7);
        txtProfit = new JTextField(7);
        txtProfit.setEditable(false);

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Product ID:"), gbc);
        gbc.gridx = 1; inputPanel.add(txtProdID, gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 1; inputPanel.add(txtProdName, gbc);
        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1; inputPanel.add(txtQuantity, gbc);
        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(new JLabel("Cost Price:"), gbc);
        gbc.gridx = 1; inputPanel.add(txtCostPrice, gbc);
        gbc.gridx = 0; gbc.gridy = 4; inputPanel.add(new JLabel("Sell Price:"), gbc);
        gbc.gridx = 1; inputPanel.add(txtSellPrice, gbc);
        gbc.gridx = 0; gbc.gridy = 5; inputPanel.add(new JLabel("Profit:"), gbc);
        gbc.gridx = 1; inputPanel.add(txtProfit, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // **Button Panel (Moved Slightly Up)**
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // 10px padding from bottom
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        // Adding a slight margin between buttons and bottom
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.NORTH); // Moves buttons slightly up
        add(bottomPanel, BorderLayout.SOUTH);

        // Make UI Visible
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Event Listeners
        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        txtSellPrice.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calculateProfit();
            }
        });

        // Database Connection
        connectDatabase();
        createTable();
        loadData();
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbUrl = "jdbc:mysql://localhost:3306/inventory";
            String dbUser = "root";
            String dbPassword = "root"; // Replace with actual password
            con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            System.out.println("✅ Database connected successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error connecting to database: " + e.getMessage());
        }
    }

    private void createTable() {
        try {
            Statement st = con.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS products (" +
                    "prod_id INT NOT NULL PRIMARY KEY, " +
                    "prod_name VARCHAR(255), " +
                    "quantity INT, " +
                    "cost_price DOUBLE, " +
                    "sell_price DOUBLE, " +
                    "profit DOUBLE)");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error creating table: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM products");
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("prod_id"),
                        rs.getString("prod_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("cost_price"),
                        rs.getDouble("sell_price"),
                        rs.getDouble("profit")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error loading data: " + e.getMessage());
        }
    }

    private void addProduct() {
        try {
            int prodID = Integer.parseInt(txtProdID.getText());
            String prodName = txtProdName.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double costPrice = Double.parseDouble(txtCostPrice.getText());
            double sellPrice = Double.parseDouble(txtSellPrice.getText());
            double profit = sellPrice - costPrice;

            pst = con.prepareStatement("INSERT INTO products (prod_id, prod_name, quantity, cost_price, sell_price, profit) VALUES (?, ?, ?, ?, ?, ?)");
            pst.setInt(1, prodID);
            pst.setString(2, prodName);
            pst.setInt(3, quantity);
            pst.setDouble(4, costPrice);
            pst.setDouble(5, sellPrice);
            pst.setDouble(6, profit);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Product Added Successfully!");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error adding product: " + e.getMessage());
        }
    }

    private void updateProduct() {
        try {
            int prodID = Integer.parseInt(txtProdID.getText());
            String prodName = txtProdName.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double costPrice = Double.parseDouble(txtCostPrice.getText());
            double sellPrice = Double.parseDouble(txtSellPrice.getText());
            double profit = sellPrice - costPrice;

            pst = con.prepareStatement("UPDATE products SET prod_name=?, quantity=?, cost_price=?, sell_price=?, profit=? WHERE prod_id=?");
            pst.setString(1, prodName);
            pst.setInt(2, quantity);
            pst.setDouble(3, costPrice);
            pst.setDouble(4, sellPrice);
            pst.setDouble(5, profit);
            pst.setInt(6, prodID);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Product Updated Successfully!");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error updating product: " + e.getMessage());
        }
    }

    private void deleteProduct() {
        try {
            int prodID = Integer.parseInt(txtProdID.getText());

            pst = con.prepareStatement("DELETE FROM products WHERE prod_id=?");
            pst.setInt(1, prodID);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Product Deleted Successfully!");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error deleting product: " + e.getMessage());
        }
    }

    private void calculateProfit() {
        try {
            double costPrice = Double.parseDouble(txtCostPrice.getText());
            double sellPrice = Double.parseDouble(txtSellPrice.getText());
            double profit = sellPrice - costPrice;
            txtProfit.setText(String.valueOf(profit));
        } catch (NumberFormatException ignored) {
        }
    }

    public static void main(String[] args) {
        new InventoryManagement();
    }
}
