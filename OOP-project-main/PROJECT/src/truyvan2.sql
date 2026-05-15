CREATE TABLE review (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    shopId INT,
    rating INT,
    comment TEXT
);