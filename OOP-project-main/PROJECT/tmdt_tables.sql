-- SQL Script tạo các bảng cần thiết cho module Giỏ hàng, Đơn hàng, Thanh toán, Danh mục, Sản phẩm
-- Vui lòng chạy script này trong HeidiSQL trên database 'btl'

-- 0. Bảng category (Danh mục sản phẩm)
CREATE TABLE IF NOT EXISTS category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Thêm danh mục mặc định
INSERT INTO category (name, description) 
SELECT * FROM (SELECT 'Danh mục mặc định', 'Danh mục cơ bản') AS tmp
WHERE NOT EXISTS (SELECT name FROM category WHERE name = 'Danh mục mặc định') LIMIT 1;

-- 1. Bảng product (Sản phẩm)
CREATE TABLE IF NOT EXISTS product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    categoryId INT DEFAULT 1,
    FOREIGN KEY (categoryId) REFERENCES category(id) ON DELETE SET NULL
);

-- 2. Bảng cart (Giỏ hàng)
CREATE TABLE IF NOT EXISTS cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    UNIQUE KEY unique_user_product (userId, productId),
    FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE
);

-- 3. Bảng orders (Đơn hàng chính)
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    totalAmount DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Bảng orderDetails (Chi tiết đơn hàng)
CREATE TABLE IF NOT EXISTS orderDetails (
    orderId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (orderId) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE
);

-- 5. Bảng payments (Biên lai thanh toán)
CREATE TABLE IF NOT EXISTS payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT NOT NULL,
    paymentMethod VARCHAR(50) NOT NULL,
    amount DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    paymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (orderId) REFERENCES orders(id) ON DELETE CASCADE
);

-- (Tùy chọn) Thêm một số dữ liệu mẫu cho bảng product để bạn test thử
INSERT INTO product (name, price, stock, categoryId) 
SELECT * FROM (SELECT 'Sản phẩm Test 1', 150000.0, 100, 1) AS tmp
WHERE NOT EXISTS (SELECT name FROM product WHERE name = 'Sản phẩm Test 1') LIMIT 1;

INSERT INTO product (name, price, stock, categoryId) 
SELECT * FROM (SELECT 'Sản phẩm Test 2', 300000.0, 50, 1) AS tmp
WHERE NOT EXISTS (SELECT name FROM product WHERE name = 'Sản phẩm Test 2') LIMIT 1;
