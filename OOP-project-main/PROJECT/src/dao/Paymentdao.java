package dao;

import model.Payment;
import java.sql.*;

public class Paymentdao {

    // 1. Tạo biên lai thanh toán (Mục 9)
    public void createPayment(Payment p) throws Exception {
        // Xác định trạng thái thanh toán dựa trên phương thức
        String status = p.getMethod().equals("COD") ? "Chưa thu tiền" : "Đã thu tiền";

        // Thêm cột status vào câu lệnh INSERT
        String sql = "INSERT INTO payments (orderId, paymentMethod, amount, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getOrderId());
            ps.setString(2, p.getMethod());
            ps.setDouble(3, p.getAmount());
            ps.setString(4, status); // Truyền thêm trạng thái
            ps.executeUpdate();
            System.out.println("=> Đã tạo biên lai cho đơn hàng #" + p.getOrderId() + " | Trạng thái: " + status);
        } catch (Exception e) {
            System.out.println("Lỗi createPayment: " + e.getMessage());
        }
    }

    // 2. Xem biên lai lịch sử (Mục 9)
    public void viewReceipts() throws Exception {
        String sql = "SELECT * FROM payments";
        try (Connection conn = DBconnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- DANH SÁCH BIÊN LAI ---");
            boolean coDuLieu = false;
            while (rs.next()) {
                coDuLieu = true;
                java.sql.Timestamp date = rs.getTimestamp("paymentDate");
                String dateStr = (date != null) ? new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) : "N/A";
                System.out.printf("Mã BL: %d | Đơn: %d | Phương thức: %s | Tiền: %,.0f VND | Ngày: %s\n",
                        rs.getInt("paymentId"),
                        rs.getInt("orderId"),
                        rs.getString("paymentMethod"),
                        rs.getDouble("amount"),
                        dateStr); // Lấy thêm cột ngày
            }
            if (!coDuLieu)
                System.out.println("Chưa có giao dịch nào!");
        }
    }

    // 3. Cập nhật trạng thái thu tiền (Mục 9)
    public void updatePaymentStatus(int paymentId, String newStatus) throws Exception {
        String sql = "UPDATE payments SET status = ? WHERE paymentId = ?";
        try (Connection conn = DBconnection.getConnection();) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setInt(2, paymentId);
            ps.executeUpdate();
            System.out.println("=> Đã cập nhật trạng thái thanh toán #" + paymentId + " thành: " + newStatus);
        }
    }
}
