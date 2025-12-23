# 🛒 E-Commerce CLI Application

A Java Spring Boot e-commerce application with an interactive Command Line Interface (CLI). This guide will help you set up and run the project from scratch, even if you have no technical background.

---

## 📋 Table of Contents

1. [What You'll Need](#-what-youll-need)
2. [Step 1: Install Java](#-step-1-install-java)
3. [Step 2: Install PostgreSQL](#-step-2-install-postgresql)
4. [Step 3: Set Up the Database](#-step-3-set-up-the-database)
5. [Step 4: Run the Application](#-step-4-run-the-application)
6. [Step 5: Using the CLI](#-step-5-using-the-cli)
7. [Step 6: Verifying Data in PostgreSQL](#-step-6-verifying-data-in-postgresql)
8. [Complete Walkthrough Example](#-complete-walkthrough-example)
9. [Troubleshooting](#-troubleshooting)

---

## 🔧 What You'll Need

Before starting, make sure you have:
- A computer running **macOS**, **Windows**, or **Linux**
- An internet connection (for downloading software)
- About 30 minutes of time

---

## ☕ Step 1: Install Java

This application requires **Java 17** or higher.

### For macOS:

1. Open **Terminal** (search for "Terminal" in Spotlight or find it in Applications → Utilities)

2. Install Homebrew (a package manager) if you don't have it:
   ```bash
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```

3. Install Java 17:
   ```bash
   brew install openjdk@17
   ```

4. Add Java to your path:
   ```bash
   echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
   source ~/.zshrc
   ```

5. Verify installation:
   ```bash
   java -version
   ```
   You should see something like: `openjdk version "17.x.x"`

### For Windows:

1. Download Java 17 from: https://adoptium.net/temurin/releases/?version=17
2. Choose **Windows x64** and download the **.msi** installer
3. Run the installer and follow the prompts
4. Open **Command Prompt** and verify:
   ```cmd
   java -version
   ```

### For Linux (Ubuntu/Debian):

```bash
sudo apt update
sudo apt install openjdk-17-jdk
java -version
```

---

## 🐘 Step 2: Install PostgreSQL

### For macOS:

1. Using Homebrew:
   ```bash
   brew install postgresql@15
   ```

2. Start PostgreSQL:
   ```bash
   brew services start postgresql@15
   ```

3. Add to path:
   ```bash
   echo 'export PATH="/opt/homebrew/opt/postgresql@15/bin:$PATH"' >> ~/.zshrc
   source ~/.zshrc
   ```

### For Windows:

1. Download PostgreSQL from: https://www.postgresql.org/download/windows/
2. Run the installer
3. **Important:** During installation, remember the password you set for the `postgres` user
4. Keep the default port as **5432**
5. Complete the installation

### For Linux (Ubuntu/Debian):

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

---

## 🗄️ Step 3: Set Up the Database

### 3.1 Create the Database

#### For macOS/Linux:

1. Open Terminal and connect to PostgreSQL:
   ```bash
   psql postgres
   ```

2. Create a new database:
   ```sql
   CREATE DATABASE e_com;
   ```

3. Set the password for postgres user (use `sherupass` to match the application config):
   ```sql
   ALTER USER postgres WITH PASSWORD 'sherupass';
   ```

4. Exit PostgreSQL:
   ```sql
   \q
   ```

#### For Windows:

1. Open **SQL Shell (psql)** from the Start Menu
2. Press Enter to accept defaults until you reach the password prompt
3. Enter your postgres password
4. Create the database:
   ```sql
   CREATE DATABASE e_com;
   ```
5. Set the password (if different):
   ```sql
   ALTER USER postgres WITH PASSWORD 'sherupass';
   ```
6. Exit:
   ```sql
   \q
   ```

### 3.2 Add Sample Data (Categories and Products)

The application needs some products to work with. Let's add them:

1. Connect to the database:
   ```bash
   psql -U postgres -d e_com
   ```
   (Enter password: `sherupass` when prompted)

2. Run these SQL commands to add categories:
   ```sql
   INSERT INTO category_table (category_id, category_name) VALUES (1, 'Electronics');
   INSERT INTO category_table (category_id, category_name) VALUES (2, 'Clothing');
   INSERT INTO category_table (category_id, category_name) VALUES (3, 'Books');
   ```

3. Add some products:
   ```sql
   INSERT INTO product_table (product_id, product_name, description, price, stock_present, category_id, created_at) 
   VALUES (1, 'iPhone 15', 'Latest Apple smartphone', 999.99, 50, 1, NOW());
   
   INSERT INTO product_table (product_id, product_name, description, price, stock_present, category_id, created_at) 
   VALUES (2, 'Samsung Galaxy S24', 'Android flagship phone', 899.99, 30, 1, NOW());
   
   INSERT INTO product_table (product_id, product_name, description, price, stock_present, category_id, created_at) 
   VALUES (3, 'Cotton T-Shirt', 'Comfortable cotton t-shirt', 29.99, 100, 2, NOW());
   
   INSERT INTO product_table (product_id, product_name, description, price, stock_present, category_id, created_at) 
   VALUES (4, 'Java Programming Book', 'Learn Java from scratch', 49.99, 25, 3, NOW());
   ```

4. Update sequences (important for auto-generated IDs):
   ```sql
   SELECT setval('category_table_category_id_seq', 10);
   SELECT setval('product_table_product_id_seq', 10);
   ```

5. Exit:
   ```sql
   \q
   ```

---

## 🚀 Step 4: Run the Application

### 4.1 Navigate to Project Folder

Open Terminal (macOS/Linux) or Command Prompt (Windows) and navigate to the project:

```bash
cd /Users/sunilkumarmahakur/Downloads/ecommerce4-Updated
```

**Note:** Replace the path with your actual project location.

### 4.2 Run the Application

#### Using Maven Wrapper (Recommended):

**For macOS/Linux:**
```bash
./mvnw spring-boot:run
```

**For Windows:**
```cmd
mvnw.cmd spring-boot:run
```

#### First Run Notes:
- The first run will download dependencies (this may take 2-5 minutes)
- Wait until you see the CLI menu appear:

```
==== E-COMMERCE CLI ====
1. Register
2. Login
3. View All Products
...
```

---

## 🖥️ Step 5: Using the CLI

The application provides an interactive menu. Here's what each option does:

| Option | Description |
|--------|-------------|
| 1. Register | Create a new user account |
| 2. Login | Log in to your account |
| 3. View All Products | See available products |
| 4. Add Product to Cart | Add a product to your shopping cart |
| 5. View Cart | See items in your cart |
| 6. Checkout / Place Order | Create an order from cart items |
| 7. Initiate Payment | Start the payment process |
| 8. Verify Payment | Complete the payment |
| 9. Exit | Close the application |

### Typical Flow:

1. **Register** → Create your account
2. **Login** → Access your account
3. **View All Products** → Browse available items
4. **Add Product to Cart** → Select items to buy
5. **View Cart** → Review your selections
6. **Checkout** → Place your order
7. **Initiate Payment** → Start payment
8. **Verify Payment** → Complete transaction

---

## 🔍 Step 6: Verifying Data in PostgreSQL

To see what's happening in the database as you use the CLI, open a **second terminal window** for PostgreSQL.

### 6.1 Connect to Database

```bash
psql -U postgres -d e_com
```
Password: `sherupass`

### 6.2 Useful SQL Commands

#### View All Tables:
```sql
\dt
```

#### View All Users:
```sql
SELECT id, name, email, phone_number FROM user_table;
```

#### View All Products:
```sql
SELECT product_id, product_name, price, stock_present FROM product_table;
```

#### View All Categories:
```sql
SELECT * FROM category_table;
```

#### View User Addresses:
```sql
SELECT address_id, street, city, state, zipcode, country, user_id FROM addresses;
```

#### View Shopping Carts:
```sql
SELECT * FROM carts;
```

#### View Cart Items:
```sql
SELECT ci.cart_item_id, ci.quantity, p.product_name, p.price 
FROM cart_items ci 
JOIN product_table p ON ci.product_id = p.product_id;
```

#### View All Orders:
```sql
SELECT order_id, total_amount, status, created_at, user_id FROM orders;
```

#### View Order Items:
```sql
SELECT oi.order_item_id, p.product_name, oi.quantity, oi.price 
FROM order_items oi 
JOIN product_table p ON oi.product_id = p.product_id;
```

#### View Payments:
```sql
SELECT payment_id, amount, status, transaction_id, order_id FROM payments;
```

### 6.3 Quick Reference Card

Keep this handy while testing:

```
┌─────────────────────────────────────────────────────────────┐
│                    QUICK SQL COMMANDS                        │
├─────────────────────────────────────────────────────────────┤
│ Connect:     psql -U postgres -d e_com                      │
│ List tables: \dt                                            │
│ Users:       SELECT * FROM user_table;                      │
│ Products:    SELECT * FROM product_table;                   │
│ Carts:       SELECT * FROM carts;                           │
│ Cart Items:  SELECT * FROM cart_items;                      │
│ Orders:      SELECT * FROM orders;                          │
│ Payments:    SELECT * FROM payments;                        │
│ Exit:        \q                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 📝 Complete Walkthrough Example

Here's a step-by-step example with what you'll type and what you'll see:

### Terminal 1: Run the Application

```bash
cd /Users/sunilkumarmahakur/Downloads/ecommerce4-Updated
./mvnw spring-boot:run
```

### Terminal 2: PostgreSQL (keep open for verification)

```bash
psql -U postgres -d e_com
```

---

### Step A: Register a New User

**In CLI (Terminal 1):**
```
Select option: 1
Enter name: John Doe
Enter email: john@example.com
Enter password: password123
Enter phone: 9876543210
```

**Verify in PostgreSQL (Terminal 2):**
```sql
SELECT id, name, email, phone_number FROM user_table;
```

Expected output:
```
 id |   name   |      email       | phone_number
----+----------+------------------+--------------
  1 | John Doe | john@example.com | 9876543210
```

---

### Step B: Login

**In CLI:**
```
Select option: 2
Enter email: john@example.com
Enter password: password123
```

You'll see: `Login successful! Your User ID = 1`

---

### Step C: View Products

**In CLI:**
```
Select option: 3
```

You'll see the list of products with their IDs.

---

### Step D: Add Product to Cart

**In CLI:**
```
Select option: 4
Enter product ID to add: 1
```

**Verify in PostgreSQL:**
```sql
SELECT * FROM carts;
SELECT ci.*, p.product_name FROM cart_items ci 
JOIN product_table p ON ci.product_id = p.product_id;
```

---

### Step E: Add an Address (Required for Checkout)

Before checkout, you need to add an address. Use this SQL in PostgreSQL:

```sql
INSERT INTO addresses (street, city, state, zipcode, country, user_id) 
VALUES ('123 Main Street', 'New York', 'NY', '10001', 'USA', 1);
```

**Verify:**
```sql
SELECT * FROM addresses;
```
Note the `address_id` (usually 1).

---

### Step F: Checkout / Place Order

**In CLI:**
```
Select option: 6
Enter addressId: 1
Enter payment method (COD / UPI / CARD): UPI
```

**Verify in PostgreSQL:**
```sql
SELECT * FROM orders;
SELECT oi.*, p.product_name FROM order_items oi 
JOIN product_table p ON oi.product_id = p.product_id;
```

---

### Step G: Initiate Payment

**In CLI:**
```
Select option: 7
Enter Order ID to initiate payment: 1
Enter Payment method to initiate payment: UPI
```

You'll receive a Payment ID and Transaction ID.

**Verify in PostgreSQL:**
```sql
SELECT * FROM payments;
```

Expected: Status = `PENDING`

---

### Step H: Verify Payment

**In CLI:**
```
Select option: 8
Enter Payment ID: 1
Enter Transaction ID: [copy from previous step]
Was the payment successful? (yes/no): yes
```

**Verify in PostgreSQL:**
```sql
SELECT * FROM payments;
SELECT order_id, status FROM orders;
```

Expected: Payment Status = `SUCCESS`, Order Status = `CONFIRMED`

---

## ❗ Troubleshooting

### Problem: "Connection refused" error

**Solution:** Make sure PostgreSQL is running:

```bash
# macOS
brew services start postgresql@15

# Linux
sudo systemctl start postgresql

# Windows
# Start PostgreSQL from Services app
```

### Problem: "Database does not exist"

**Solution:** Create the database:
```bash
psql postgres
CREATE DATABASE e_com;
\q
```

### Problem: "Authentication failed"

**Solution:** Update the password:
```bash
psql postgres
ALTER USER postgres WITH PASSWORD 'sherupass';
\q
```

Or update `src/main/resources/application.properties` with your password:
```properties
spring.datasource.password=YOUR_PASSWORD
```

### Problem: "Port 8080 already in use"

**Solution:** Stop the other application using port 8080 or change the port in `application.properties`:
```properties
server.port=8081
```

### Problem: No products showing

**Solution:** Make sure you've added sample data (see Step 3.2)

### Problem: Maven not found

**Solution:** The project includes Maven wrapper. Use:
- macOS/Linux: `./mvnw`
- Windows: `mvnw.cmd`

---

## 📊 Database Schema Overview

```
┌──────────────────┐     ┌──────────────────┐
│   user_table     │     │  category_table  │
├──────────────────┤     ├──────────────────┤
│ id (PK)          │     │ category_id (PK) │
│ name             │     │ category_name    │
│ email            │     └────────┬─────────┘
│ password         │              │
│ phone_number     │              │
└────────┬─────────┘              │
         │                        │
         │                        ▼
         │              ┌──────────────────┐
         │              │  product_table   │
         │              ├──────────────────┤
         │              │ product_id (PK)  │
         │              │ product_name     │
         │              │ description      │
         │              │ price            │
         │              │ stock_present    │
         │              │ category_id (FK) │
         │              └──────────────────┘
         │
         ▼
┌──────────────────┐     ┌──────────────────┐
│    addresses     │     │      carts       │
├──────────────────┤     ├──────────────────┤
│ address_id (PK)  │     │ cart_id (PK)     │
│ street           │     │ user_id (FK)     │
│ city             │     └────────┬─────────┘
│ state            │              │
│ zipcode          │              ▼
│ country          │     ┌──────────────────┐
│ user_id (FK)     │     │   cart_items     │
└──────────────────┘     ├──────────────────┤
                         │ cart_item_id(PK) │
                         │ quantity         │
                         │ cart_id (FK)     │
                         │ product_id (FK)  │
                         └──────────────────┘
         │
         ▼
┌──────────────────┐     ┌──────────────────┐
│     orders       │────▶│    payments      │
├──────────────────┤     ├──────────────────┤
│ order_id (PK)    │     │ payment_id (PK)  │
│ total_amount     │     │ amount           │
│ status           │     │ status           │
│ created_at       │     │ transaction_id   │
│ user_id (FK)     │     │ order_id (FK)    │
└────────┬─────────┘     └──────────────────┘
         │
         ▼
┌──────────────────┐
│   order_items    │
├──────────────────┤
│ order_item_id(PK)│
│ quantity         │
│ price            │
│ order_id (FK)    │
│ product_id (FK)  │
└──────────────────┘
```

---

## 🎉 Success!

If you've followed all the steps, you should now have:

✅ Java 17 installed  
✅ PostgreSQL running  
✅ Database `e_com` created with sample data  
✅ Application running with CLI interface  
✅ Ability to verify all changes in PostgreSQL  

Enjoy exploring the e-commerce application! 🛍️

---

## 📞 Need Help?

If you encounter issues not covered in troubleshooting:

1. Check if all services are running (Java, PostgreSQL)
2. Verify database connection settings in `src/main/resources/application.properties`
3. Make sure you're in the correct project directory
4. Try restarting the application

---

*Last updated: December 2024*

