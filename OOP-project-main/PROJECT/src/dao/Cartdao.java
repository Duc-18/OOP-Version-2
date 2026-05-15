package dao;

import model.Cart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class Cartdao {
    // 1. Hàm thêm sản phẩm vào giỏ hàng (Đã sửa để cộng dồn nếu trùng SP)
    public void addToCart(Cart cart) {
        // Sử dụng ON DUPLICATE KEY để nếu user thêm trùng SP thì tự tăng số lượng
        String sql = "INSERT INTO cart (userId, productId, quantity) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
        try (Connection conn = DBconnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cart.getUserId());
            ps.setInt(2, cart.getProductId());
            ps.setInt(3, cart.getQuantity());
            ps.setInt(4, cart.getQuantity());

            ps.executeUpdate();
            System.out.println("=> Đã thêm sản phẩm vào giỏ hàng!");
        } catch (Exception e) {
            System.out.println("Lỗi addToCart: " + e.getMessage());
        }
    }

    // 2. Hàm cập nhật số lượng (Yêu cầu mục 7)
    public void updateQuantity(int cartId, int newQty) {
        String sql = "UPDATE cart SET quantity = ? WHERE cartId = ?";
        try (Connection conn = DBconnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQty);
            ps.setInt(2, cartId);
            ps.executeUpdate();
            System.out.println("=> Đã cập nhật số lượng dòng " + cartId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Hàm xem chi tiết giỏ hàng (Yêu cầu mục 7)
    public void viewCart(int userId) {
        String sql = "SELECT * FROM cart WHERE userId = ?";
        try (Connection conn = DBconnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- GIỎ HÀNG CỦA BẠN ---");
            while (rs.next()) {
                System.out.printf("Mã dòng: %d | SP: %d | Số lượng: %d\n",
                        rs.getInt("cartId"), rs.getInt("productId"), rs.getInt("quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. Hàm xóa sản phẩm khỏi giỏ (Yêu cầu mục 7)
    public void removeFromCart(int cartId) {
        String sql = "DELETE FROM cart WHERE cartId = ?";
        try (Connection conn = DBconnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.executeUpdate();
            System.out.println("=> Đã xóa sản phẩm khỏi giỏ hàng!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. Hàm tính tổng tiền giỏ hàng (Yêu cầu phải có bảng product với cột price)
    public double calculateTotalAmount(int userId) {
        double total = 0;
        // Lưu ý: Lệnh JOIN này yêu cầu CSDL phải có bảng 'product' và cột 'price'.
        // Khi ghép code SQL với nhóm, đảm bảo bảng sản phẩm đúng cấu trúc này.
        String sql = "SELECT SUM(c.quantity * p.price) AS total_money " +
                     "FROM cart c JOIN product p ON c.productId = p.productId " +
                     "WHERE c.userId = ?";
        try (Connection conn = DBconnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total_money");
            }
        } catch (Exception e) {
            System.out.println("Lỗi tính tổng tiền: " + e.getMessage());
        }
        return total;
    }

    // 6. Hàm lấy danh sách sản phẩm trong giỏ hàng (Để tạo Order Details)
    public List<Cart> getCartItems(int userId) {
        List<Cart> list = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE userId = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cart c = new Cart();
                c.setCartId(rs.getInt("cartId"));
                c.setUserId(rs.getInt("userId"));
                c.setProductId(rs.getInt("productId"));
                c.setQuantity(rs.getInt("quantity"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 7. Hàm làm sạch giỏ hàng (Sau khi thanh toán xong)
    public void clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE userId = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
            System.out.println("=> Đã làm sạch giỏ hàng của user " + userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- CÁC HÀM HỖ TRỢ KIỂM TRA SỐ LƯỢNG TỒN KHO ---

    // Lấy số lượng tồn kho của sản phẩm từ database
    public int getAvailableStock(int productId) {
        // Tạm để giá trị lớn nếu lúc chưa ghép code (bảng product chưa chuẩn) để không bị chặn lỗi
        int stock = 9999; 
        // Thống nhất với Đức dùng cột 'stock' cho bảng product
        String sql = "SELECT stock FROM product WHERE productId = ?"; 
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stock = rs.getInt(1);
            }
        } catch (Exception e) {
            // Khi chưa ghép code với Đức, có thể bảng product chưa có cột stock. 
            // In ra cảnh báo thay vì làm sập chương trình.
            System.out.println("=> [Cảnh báo] Không lấy được số lượng tồn kho (Có thể do bảng product hoặc cột stock chưa đúng). Cho phép thêm tạm thời...");
        }
        return stock;
    }

    // Lấy số lượng của một sản phẩm đã có trong giỏ hàng của user
    public int getQuantityInCart(int userId, int productId) {
        int qty = 0;
        String sql = "SELECT quantity FROM cart WHERE userId = ? AND productId = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                qty = rs.getInt("quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qty;
    }

    // Lấy productId từ cartId để phục vụ updateQuantity
    public int getProductIdByCartId(int cartId) {
        int productId = -1;
        String sql = "SELECT productId FROM cart WHERE cartId = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                productId = rs.getInt("productId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productId;
    }
}
