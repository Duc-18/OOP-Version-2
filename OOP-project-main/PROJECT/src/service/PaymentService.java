package service;

import dao.Paymentdao;
import model.Payment;

public class PaymentService {
    private Paymentdao dao = new Paymentdao();

    public void createPayment(Payment p) {
        try {
            if (p.getAmount() <= 0) {
                System.out.println("Số tiền thanh toán không hợp lệ!");
                return;
            }
            dao.createPayment(p);
        } catch (Exception e) {
            System.err.println("Lỗi khi tạo thanh toán: " + e.getMessage());
        }
    }

    public void viewReceipts() {
        try {
            dao.viewReceipts();
        } catch (Exception e) {
            System.err.println("Lỗi khi xem biên lai: " + e.getMessage());
        }
    }

    public void updatePaymentStatus(int paymentId, String newStatus) {
        try {
            dao.updatePaymentStatus(paymentId, newStatus);
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật trạng thái thanh toán: " + e.getMessage());
        }
    }
}
