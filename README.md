# 📦 Inventory Management System

## Overview
The **Inventory Management System** is a Java-based desktop application designed to help businesses efficiently manage their inventory. It provides an intuitive graphical user interface (GUI) for tracking stock levels, adding new products, updating product details, and deleting items from the database.

## Features
✅ **Product Management** – Add, update, and delete products with details such as name, quantity, cost price, and selling price.
✅ **Profit Calculation** – Automatically calculates profit based on cost and selling price.
✅ **Database Integration** – Uses MySQL to store and retrieve inventory data.
✅ **User-Friendly Interface** – Built using Java Swing for an intuitive experience.
✅ **Real-Time Data Handling** – Fetches and updates data dynamically from the database.

## Tech Stack
- **Programming Language**: Java (Swing for UI)
- **Database**: MySQL
- **JDBC**: Used for database connectivity

## Installation & Setup
### Prerequisites
- Java Development Kit (JDK) installed
- MySQL Server installed and running
- MySQL Connector/J (JDBC Driver)

### Steps to Run the Project
1️⃣ Clone the repository:
```bash
git clone https://github.com/your-username/inventory-management.git
```
2️⃣ Import the project into an IDE (such as IntelliJ IDEA or Eclipse).
3️⃣ Create a MySQL database and run the following SQL command:
```sql
CREATE DATABASE inventory;
USE inventory;

CREATE TABLE products (
    prod_id INT NOT NULL PRIMARY KEY,
    prod_name VARCHAR(255),
    quantity INT,
    cost_price DOUBLE,
    sell_price DOUBLE,
    profit DOUBLE
);
```
4️⃣ Update the database credentials in the `connectDatabase()` method of `InventoryManagement.java`:
```java
String dbUrl = "jdbc:mysql://localhost:3306/inventory";
String dbUser = "root";
String dbPassword = "your_password";
```
5️⃣ Compile and run the project:
```bash
javac InventoryManagement.java
java InventoryManagement
```

## Usage
- Enter product details in the input fields and click **Add** to save them.
- Select a row in the table and click **Update** to modify product details.
- Select a row and click **Delete** to remove a product from the inventory.
- Profit is automatically calculated when entering cost and sell price.

## Contributing
Contributions are welcome! Feel free to fork the repository, create a new branch, and submit a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

🔗 **GitHub Repository**: [Your Repo Link]

