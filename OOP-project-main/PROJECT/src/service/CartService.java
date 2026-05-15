package service;

import dao.Cartdao;
import model.Cart;
import java.util.List;

public class CartService {
    private Cartdao dao = new Cartdao();

    public void addToCart(Cart cart) {
        if (cart.getQuantity() <= 0) {
            System.out.println("Số lượng không hợp lệ!");
            return;
        }

        // Lấy số lượng tồn kho và số lượng đã có trong giỏ
        int availableStock = dao.getAvailableStock(cart.getProductId());
        int currentQtyInCart = dao.getQuantityInCart(cart.getUserId(), cart.getProductId());

        // Kiểm tra xem tổng số lượng (đã có + thêm mới) có vượt quá tồn kho không
        // Chú ý: Nếu bạn chưa có bảng product hoặc chưa cấu hình cột tồn kho, 
        // availableStock sẽ trả về 0 và chặn việc thêm. Bạn có thể tạm comment đoạn check này khi test.
        if (currentQtyInCart + cart.getQuantity() > availableStock) {
            System.out.println("Thất bại! Số lượng sản phẩm không đủ. Kho hiện còn: " + availableStock);
            return;
        }

        dao.addToCart(cart);
    }

    public void updateQuantity(int cartId, int newQty) {
        if (newQty <= 0) {
            System.out.println("Số lượng phải lớn hơn 0!");
            return;
        }

        int productId = dao.getProductIdByCartId(cartId);
        if (productId != -1) {
            int availableStock = dao.getAvailableStock(productId);
            if (newQty > availableStock) {
                System.out.println("Thất bại! Số lượng cập nhật vượt quá tồn kho. Kho hiện còn: " + availableStock);
                return;
            }
        }

        dao.updateQuantity(cartId, newQty);
    }

    public void viewCart(int userId) {
        dao.viewCart(userId);
    }

    public void removeFromCart(int cartId) {
        dao.removeFromCart(cartId);
    }

    public double calculateTotalAmount(int userId) {
        return dao.calculateTotalAmount(userId);
    }

    public List<Cart> getCartItems(int userId) {
        return dao.getCartItems(userId);
    }

    public void clearCart(int userId) {
        dao.clearCart(userId);
    }
}
