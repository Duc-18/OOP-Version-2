package dao;

import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Productdao {

    public boolean findById(int id) {
        try (Connection conn = DBconnection.getConnection()) {
            String sql = "SELECT productId FROM product WHERE productId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void add(Product p) {
        try (Connection conn = DBconnection.getConnection()) {
            String sql = "INSERT INTO product(name, price, stock, categoryId, shopId) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getCategoryId());
            ps.setInt(5, p.getShopId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Product p) {
        try (Connection conn = DBconnection.getConnection()) {
            String sql = "UPDATE product SET name = ?, price = ?, stock = ?, categoryId = ?, shopId = ? WHERE productId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getCategoryId());
            ps.setInt(5, p.getShopId());
            ps.setInt(6, p.getProductId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection conn = DBconnection.getConnection()) {
            String sql = "DELETE FROM product WHERE productId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBconnection.getConnection()) {
            String sql = "SELECT * FROM product";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("productId"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("categoryId"), rs.getInt("shopId")
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

// Tìm kiếm sản phẩm theo tên
    public List<Product> searchByName(String keyword) {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBconnection.getConnection()) {
            String sql = "SELECT * FROM product WHERE name LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt("productId"), rs.getString("name"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("categoryId"), rs.getInt("shopId")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Tìm kiếm sản phẩm theo giá
    public List<Product> filterByPrice(double min, double max) {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBconnection.getConnection()) {
            String sql = "SELECT * FROM product WHERE price BETWEEN ? AND ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, min);
            ps.setDouble(2, max);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt("productId"), rs.getString("name"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("categoryId"), rs.getInt("shopId")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Tìm kiếm sản phẩm theo ID danh mục
    public List<Product> filterByCategoryId(int catId) {
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBconnection.getConnection()) {
            String sql = "SELECT * FROM product WHERE categoryId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, catId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt("productId"), rs.getString("name"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("categoryId"), rs.getInt("shopId")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

}
