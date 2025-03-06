CREATE database inventory;
USE INVENTORY;
CREATE TABLE products (
    prod_id INT NOT NULL PRIMARY KEY,
    prod_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    cost_price DOUBLE NOT NULL,
    sell_price DOUBLE NOT NULL,
    profit DOUBLE NOT NULL
);

INSERT INTO products (prod_id, prod_name, quantity, cost_price, sell_price, profit) 
VALUES (101, 'Mouse', 10, 250.0, 350.0, 100.0);

 INSERT INTO products (prod_id, prod_name, quantity, cost_price, sell_price, profit) VALUES
(111, 'Wireless Mouse', 50, 500.00, 750.00, 250.00),
(102, 'Mechanical Keyboard', 30, 2000.00, 2800.00, 800.00),
(103, 'Gaming Headset', 20, 1500.00, 2200.00, 700.00),
(104, 'USB-C Hub', 40, 800.00, 1200.00, 400.00),
(105, 'Webcam 1080p', 25, 1200.00, 1800.00, 600.00),
(106, 'Portable SSD 1TB', 15, 5000.00, 6500.00, 1500.00),
(107, 'Bluetooth Speaker', 35, 1800.00, 2500.00, 700.00),
(108, 'Smartwatch', 20, 3500.00, 4500.00, 1000.00),
(109, 'Laptop Cooling Pad', 50, 700.00, 1100.00, 400.00),
(110, 'Wireless Charger', 45, 900.00, 1400.00, 500.00);

select * from products order by prod_id;

