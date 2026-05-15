package service;

import dao.Productdao;
import dao.Categorydao;
import model.Product;
import java.util.List;

public class ProductService {
    Productdao dao = new Productdao();
    Categorydao catDao = new Categorydao();

    public boolean checkIdExists(int id) {
        if (id <= 0) return false;
        return dao.findById(id);
    }

    public void createProduct(Product p) {
        // 1. Kiểm tra tên, giá, số lượng
        if (p.getName() == null || p.getName().isEmpty()) {
            System.out.println("Lỗi: Tên sản phẩm không được để trống!");
            return;
        }
        if (p.getPrice() < 0) {
            System.out.println("Lỗi: Giá sản phẩm không được âm!");
            return;
        }

        // 2. KIỂM TRA ID DANH MỤC CÓ TỒN TẠI KHÔNG
        if (!catDao.findById(p.getCategoryId())) {
            System.out.println("Lỗi: Mã danh mục (ID = " + p.getCategoryId() + ") không tồn tại trong hệ thống!");
            return;
        }

        // 3. Mọi thứ hợp lệ mới thêm vào DB
        dao.add(p);
        System.out.println("Đã thêm sản phẩm thành công.");
    }

    public void editProduct(Product p) {
        // Kiểm tra dữ liệu đầu vào cơ bản
        if (p.getName() == null || p.getName().isEmpty()) {
            System.out.println("Lỗi: Tên sản phẩm không được để trống!");
            return;
        }
        if (p.getPrice() < 0 || p.getStock() < 0) {
            System.out.println("Lỗi: Giá hoặc số lượng sản phẩm không được âm!");
            return;
        }

        // Kiểm tra lại khóa ngoại danh mục một lần nữa cho chắc chắn
        if (!catDao.findById(p.getCategoryId())) {
            System.out.println("Lỗi nghiệp vụ: Mã danh mục " + p.getCategoryId() + " không tồn tại!");
            return;
        }

        // Thực thi cập nhật xuống Database
        dao.update(p);
        System.out.println("Đã cập nhật sản phẩm thành công.");
    }

    public void removeProduct(int id) {
        if (id <= 0) {
            System.out.println("ID không hợp lệ!");
            return;
        }
        if (!dao.findById(id)) {
            System.out.println("Lỗi: Không tìm thấy sản phẩm có ID = " + id + " để xóa!");
            return;
        }
        dao.delete(id);
        System.out.println("Đã xóa sản phẩm thành công.");
    }

    public void displayAll() {
        List<Product> list = dao.getAll();
        if (list.isEmpty()) {
            System.out.println("Gian hàng hiện tại trống.");
        } else {
            System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
            for (Product p : list) {
                System.out.println("ID: " + p.getProductId() + " | Tên: " + p.getName() +
                        " | Giá: " + p.getPrice() + " | SL: " + p.getStock() +
                        " | Mã DM: " + p.getCategoryId());
            }
        }
    }

    // Hàm phụ trợ dùng chung để in danh sách sản phẩm khi tìm kiếm/lọc
    private void printSearchResult(List<Product> list) {
        if (list.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm nào khớp với điều kiện lọc.");
        } else {
            System.out.println("\n--- KẾT QUẢ TÌM KIẾM/LỌC ---");
            for (Product p : list) {
                System.out.println("ID: " + p.getProductId() + " | Tên: " + p.getName() +
                        " | Giá: " + p.getPrice() + " | SL: " + p.getStock() +
                        " | Mã DM: " + p.getCategoryId());
            }
        }
    }

    public void searchProductsByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Từ khóa tìm kiếm không hợp lệ!");
            return;
        }
        List<Product> list = dao.searchByName(keyword.trim());
        printSearchResult(list);
    }

    public void filterProductsByPrice(double min, double max) {
        if (min < 0 || max < min) {
            System.out.println("Khoảng giá lọc không hợp lệ (Giá tối thiểu không được âm, giá tối đa phải lớn hơn giá tối thiểu)!");
            return;
        }
        List<Product> list = dao.filterByPrice(min, max);
        printSearchResult(list);
    }

    public void filterProductsByCategory(int catId) {
        if (catId <= 0) {
            System.out.println("Mã danh mục không hợp lệ!");
            return;
        }
        List<Product> list = dao.filterByCategoryId(catId);
        printSearchResult(list);
    }
}
