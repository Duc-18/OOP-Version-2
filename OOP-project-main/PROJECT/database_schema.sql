-- SQL Script tổng hợp cho toàn bộ dự án
-- Đã đồng bộ các thuộc tính và khóa ngoại cho tất cả các bảng.

-- 1. Xóa các bảng cũ nếu tồn tại (theo thứ tự để tránh lỗi khóa ngoại)
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS orderDetails;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS shop;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS users;

-- 2. Bảng users (Người dùng)
-- Bảng cơ sở cần thiết cho các bảng khác tham chiếu userId
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'user'
);

-- 3. Bảng category (Danh mục sản phẩm)
CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- 4. Bảng shop (Cửa hàng)
CREATE TABLE shop (
    shopId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    address VARCHAR(255),
    status VARCHAR(50) DEFAULT 'active'
);

-- 5. Bảng product (Sản phẩm)
CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    categoryId INT DEFAULT 1,
    shopId INT,
    FOREIGN KEY (categoryId) REFERENCES category(id) ON DELETE SET NULL,
    FOREIGN KEY (shopId) REFERENCES shop(shopId) ON DELETE SET NULL
);

-- 6. Bảng location (Địa chỉ của người dùng)
CREATE TABLE location (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    detail VARCHAR(255),
    phone VARCHAR(20),
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE
);

-- 7. Bảng review (Đánh giá cửa hàng)
CREATE TABLE review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    shopId INT NOT NULL,
    rating INT NOT NULL,
    comment TEXT,
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (shopId) REFERENCES shop(shopId) ON DELETE CASCADE
);

-- 8. Bảng cart (Giỏ hàng)
-- Có unique key trên userId và productId để dùng chức năng ON DUPLICATE KEY UPDATE trong Cartdao
CREATE TABLE cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    UNIQUE KEY unique_user_product (userId, productId),
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE
);

-- 9. Bảng orders (Đơn hàng)
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    totalAmount DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE
);

-- 10. Bảng orderDetails (Chi tiết đơn hàng)
CREATE TABLE orderDetails (
    orderId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (orderId, productId),
    FOREIGN KEY (orderId) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE
);

-- 11. Bảng payments (Biên lai thanh toán)
CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT NOT NULL,
    paymentMethod VARCHAR(50) NOT NULL,
    amount DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    paymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (orderId) REFERENCES orders(id) ON DELETE CASCADE
);

-- 12. Thêm dữ liệu mẫu cơ bản (Tùy chọn)
INSERT INTO users (username, password, role) VALUES ('admin', 'admin', 'admin'), ('user1', '123456', 'user');
INSERT INTO category (name, description) VALUES ('Danh mục mặc định', 'Danh mục cơ bản');
INSERT INTO shop (name, description, address, status) VALUES ('Shop Test', 'Shop bán hàng thử nghiệm', 'Hà Nội', 'active');
INSERT INTO product (name, price, stock, categoryId, shopId) VALUES 
('Sản phẩm Test 1', 150000.0, 100, 1, 1),
('Sản phẩm Test 2', 300000.0, 50, 1, 1);
