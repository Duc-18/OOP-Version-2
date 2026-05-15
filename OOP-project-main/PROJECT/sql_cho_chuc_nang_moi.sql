-- SQL Dành riêng cho Use Case Mới (Quản lý Danh mục & Sản phẩm)
-- Nếu bạn đã chạy file tmdt_tables.sql cũ trước đó, bảng product của bạn sẽ bị thiếu cột categoryId.
-- Hãy chạy file này để cập nhật thêm bảng category và sửa lại bảng product.

-- 1. Tạo bảng category (Danh mục sản phẩm)
CREATE TABLE IF NOT EXISTS category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Thêm danh mục mặc định
INSERT INTO category (name, description) 
SELECT * FROM (SELECT 'Danh mục mặc định', 'Danh mục cơ bản') AS tmp
WHERE NOT EXISTS (SELECT name FROM category WHERE name = 'Danh mục mặc định') LIMIT 1;

-- 2. Cập nhật bảng product (Thêm cột categoryId nếu bạn chưa xóa bảng cũ)
-- LƯU Ý: Nếu lệnh ALTER TABLE này báo lỗi "Duplicate column name", nghĩa là bạn đã có cột này rồi, bạn có thể bỏ qua.
ALTER TABLE product 
ADD COLUMN categoryId INT DEFAULT 1;

ALTER TABLE product 
ADD FOREIGN KEY (categoryId) REFERENCES category(id) ON DELETE SET NULL;

-- --- HOẶC NẾU BẠN MUỐN XÓA BẢNG CŨ LÀM LẠI TỪ ĐẦU ---
-- Hãy bôi đen chạy đoạn code dưới đây (Bỏ comment -- đi)
/*
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS orderDetails;
DROP TABLE IF EXISTS product;

CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    categoryId INT DEFAULT 1,
    FOREIGN KEY (categoryId) REFERENCES category(id) ON DELETE SET NULL
);
*/
