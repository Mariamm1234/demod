
-- Drop the currently open database (disconnect first)
\connect postgres
DROP DATABASE IF EXISTS test3;

-- Create the database and connect to it
CREATE DATABASE test3;
\connect test3;

-- Create the role
DROP ROLE IF EXISTS taskuser3;
CREATE ROLE taskuser3 WITH LOGIN PASSWORD 'password';

-- Set default privileges for the taskuser role
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO taskuser3;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO taskuser3;

CREATE TABLE "user" (
    user_id SERIAL PRIMARY KEY,
    full_name VARCHAR(50) NOT NULL,
    user_email VARCHAR(30) NOT NULL,
    user_password TEXT NOT NULL,
    user_type VARCHAR(20)  NOT NULL,
    token TEXT NOT NULL UNIQUE
);

-- Create cart table
CREATE TABLE cart (
    cart_id SERIAL PRIMARY KEY,
    amount INTEGER NOT NULL,
    total_price NUMERIC(10,2),
    user_id INTEGER NOT NULL,
    CONSTRAINT cart_user_fk FOREIGN KEY (user_id) REFERENCES "user"(user_id)ON DELETE CASCADE
);

-- Create product table
CREATE TABLE product (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(30) NOT NULL,
    product_type VARCHAR(20) NOT NULL,
    vendor_id INTEGER NOT NULL,
    amount INTEGER NOT NULL,
    product_price NUMERIC(10,2) NOT NULL,
    descrip TEXT,
    CONSTRAINT product_user_fk FOREIGN KEY (vendor_id) REFERENCES "user"(user_id)ON DELETE CASCADE
);

-- Create cart_item table
CREATE TABLE cart_item (
    id SERIAL PRIMARY KEY,
    cart_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    add_time TIMESTAMP,
    CONSTRAINT cart_product_fk FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE,
    CONSTRAINT product_cart_fk FOREIGN KEY (product_id) REFERENCES product(product_id)ON DELETE CASCADE
);

-- Create order table (quoted because 'order' is a reserved word)
CREATE TABLE "order" (
    order_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
      order_state VARCHAR(20)NOT NULL,
    CONSTRAINT order_user_fk FOREIGN KEY (user_id) REFERENCES "user"(user_id) ON DELETE CASCADE
);

-- Create order_item table
CREATE TABLE order_item (
    id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    CONSTRAINT order_product_fk FOREIGN KEY (order_id) REFERENCES "order"(order_id) ON DELETE CASCADE,
    CONSTRAINT product_order_fk FOREIGN KEY (product_id) REFERENCES product(product_id)ON DELETE CASCADE
);

-- Create transactions table
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    order_status VARCHAR(20) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    product_id INTEGER NOT NULL,
    occurance_time TIMESTAMP,
    CONSTRAINT transactions_user_fk FOREIGN KEY (user_id) REFERENCES "user"(user_id) ON DELETE CASCADE ,
     CONSTRAINT transactions_product_fk FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE

);

--Create checkout table
CREATE TABLE checkout(
   id SERIAL PRIMARY KEY,
   cart_id INTEGER NOT NULL,
   user_id INTEGER NOT NULL,
   total NUMERIC(10,2) NOT NULL,
   tax_percent VARCHAR(20),
   Tax_total  NUMERIC(10,2),
    CONSTRAINT chaeckout_cart_fk FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE ,
    CONSTRAINT chaeckout_user_fk FOREIGN KEY (user_id) REFERENCES "user"(user_id) ON DELETE CASCADE 


);

-- Create sequences
CREATE SEQUENCE user_sequence INCREMENT 1 START 10;
CREATE SEQUENCE cart_sequence INCREMENT 1 START 10;
CREATE SEQUENCE product_sequence INCREMENT 1 START 10;
CREATE SEQUENCE cart_item_sequence INCREMENT 1 START 10;
CREATE SEQUENCE order_sequence INCREMENT 1 START 10;
CREATE SEQUENCE order_item_sequence INCREMENT 1 START 10;
CREATE SEQUENCE transactions_sequence INCREMENT 1 START 10;
CREATE SEQUENCE chackout_sequence INCREMENT 1 START 10;
