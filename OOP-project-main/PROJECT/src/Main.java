import model.*;
import service.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShopService shopservice = new ShopService();
        ReviewService reviewService = new ReviewService();
        LocationService locateService = new LocationService();
        CartService cartService = new CartService();
        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();
        CategoryService categoryService = new CategoryService();
        ProductService productService = new ProductService();
        int myUserId = 1; // Default user ID for testing

        while (true) {
            System.out.println("===== MENU CHINH =====");
            System.out.println("1. Goc nguoi mua (Gio hang, Don hang, Danh gia, Dia chi)");
            System.out.println("2. Goc nguoi ban (Quan ly gian hang, Don hang, Thanh toan)");
            System.out.println("0. Thoat");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    // GÄ‚â€Ă¢â‚¬ÂÄ‚Â¢Ă¢â€Â¬Ă…â€œC
                    // NGĂ„â€Ă¢â‚¬Â Ä‚â€Ă‚Â¯Ă„â€Ă‚Â¡Ä‚â€Ă‚Â»Ä‚â€¦Ă¢â‚¬Å“I MUA
                    boolean buyerMenu = true;
                    while (buyerMenu) {
                        System.out.println("\n--- GOC NGUOI MUA ---");
                        System.out.println("1. Quan ly gio hang & Chot don");
                        System.out.println("2. Xem lich su mua hang & Bien lai");
                        System.out.println("3. Quan ly danh gia (Review)");
                        System.out.println("4. Quan ly dia chi nhan hang");
                        System.out.println("5. Tim kiem & Loc san pham");
                        System.out.println("0. Quay lai Menu chinh");
                        System.out.print("Chon chuc nang: ");
                        int buyerChoice = sc.nextInt();
                        sc.nextLine();

                        switch (buyerChoice) {
                            case 1:
                                manageCartAndCheckout(sc, cartService, orderService, paymentService, locateService,
                                        myUserId);
                                break;
                            case 2:
                                viewBuyerHistory(orderService, paymentService, myUserId);
                                break;
                            case 3:
                                manageReviews(sc, reviewService);
                                break;
                            case 4:
                                manageLocations(sc, locateService);
                                break;
                            case 5:
                                searchProducts(sc, productService, categoryService);
                                break;
                            case 0:
                                buyerMenu = false;
                                break;
                            default:
                                System.out.println("Lua chon khong hop le!");
                        }
                    }
                    break;
                case 2:
                    // GÄ‚â€Ă¢â‚¬ÂÄ‚Â¢Ă¢â€Â¬Ă…â€œC
                    // NGĂ„â€Ă¢â‚¬Â Ä‚â€Ă‚Â¯Ă„â€Ă‚Â¡Ä‚â€Ă‚Â»Ä‚â€¦Ă¢â‚¬Å“I
                    // BÄ‚â€Ă¢â‚¬ÂÄ‚â€Ă‚ÂN
                    boolean adminMenu = true;
                    while (adminMenu) {
                        System.out.println("\n--- GOC NGUOI BAN (ADMIN/SELLER) ---");
                        System.out.println("1. Quan ly gian hang (Shop)");
                        System.out.println("2. Xem tat ca don hang");
                        System.out.println("3. Cap nhat trang thai don hang");
                        System.out.println("4. Cap nhat trang thai thu tien (Bien lai)");
                        System.out.println("5. Quan ly danh muc nganh hang");
                        System.out.println("6. Quan ly san pham");
                        System.out.println("0. Quay lai Menu chinh");
                        System.out.print("Chon chuc nang: ");
                        int adminChoice = sc.nextInt();
                        sc.nextLine();

                        switch (adminChoice) {
                            case 1:
                                manageShops(sc, shopservice);
                                break;
                            case 2:
                                viewBuyerHistory(orderService, paymentService, myUserId);
                                break;
                            case 3:
                                System.out.print("Nhap Ma don hang can cap nhat: ");
                                int orderId = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Chon trang thai moi:");
                                System.out.println("1. Cho xac nhan");
                                System.out.println("2. Dang giao hang");
                                System.out.println("3. Da hoan thanh");
                                System.out.println("4. Da huy");
                                System.out.print("Lua chon: ");
                                int statusChoice = sc.nextInt();
                                sc.nextLine();
                                String newStatus = "";
                                if (statusChoice == 1)
                                    newStatus = "Cho xac nhan";
                                else if (statusChoice == 2)
                                    newStatus = "Dang giao hang";
                                else if (statusChoice == 3)
                                    newStatus = "Da hoan thanh";
                                else if (statusChoice == 4)
                                    newStatus = "Da huy";
                                else {
                                    System.out.println("Lua chon khong hop le!");
                                    break;
                                }
                                orderService.updateStatus(orderId, newStatus);
                                break;
                            case 4:
                                System.out.print("Nhap Ma Bien Lai can cap nhat: ");
                                int paymentId = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Chon trang thai thu tien moi:");
                                System.out.println("1. Chua thanh toan");
                                System.out.println("2. Da thanh toan");
                                System.out.print("Lua chon: ");
                                int payChoice = sc.nextInt();
                                sc.nextLine();
                                String payStatus = "";
                                if (payChoice == 1)
                                    payStatus = "Chua thanh toan";
                                else if (payChoice == 2)
                                    payStatus = "Da thanh toan";
                                else {
                                    System.out.println("Lua chon khong hop le!");
                                    break;
                                }
                                paymentService.updatePaymentStatus(paymentId, payStatus);
                                break;
                            case 5:
                                manageCategories(sc, categoryService);
                                break;
                            case 6:
                                manageProducts(sc, productService, categoryService, shopservice);
                                break;
                            case 0:
                                adminMenu = false;
                                break;
                            default:
                                System.out.println("Lua chon khong hop le!");
                        }
                    }
                    break;
                case 0:
                    return;
            }
        }
    }

    // --- CÄ‚â€Ă¢â‚¬ÂÄ‚â€Ă‚ÂC HÄ‚â€Ă¢â‚¬ÂÄ‚Â¢Ă¢â‚¬ÂĂ‚Â¬M
    // QUĂ„â€Ă‚Â¡Ä‚â€Ă‚ÂºÄ‚â€Ă‚Â¢N LÄ‚â€Ă¢â‚¬ÂÄ‚â€Ă‚Â GIAN
    // HÄ‚â€Ă¢â‚¬ÂÄ‚Â¢Ă¢â‚¬ÂĂ‚Â¬NG, Ă„â€Ă¢â‚¬ÂÄ‚â€Ă‚ÂÄ‚â€Ă¢â‚¬ÂÄ‚â€Ă‚ÂNH
    // GIÄ‚â€Ă¢â‚¬ÂÄ‚â€Ă‚Â, Ă„â€Ă¢â‚¬ÂÄ‚â€Ă‚ÂĂ„â€Ă‚Â¡Ä‚â€Ă‚Â»Ä‚â€Ă‚ÂA
    // CHĂ„â€Ă‚Â¡Ä‚â€Ă‚Â»Ä‚â€¹Ă¢â‚¬Â  TĂ„â€Ă‚Â¡Ä‚â€Ă‚Â»Ä‚â€Ă‚Âª
    // NGĂ„â€Ă¢â‚¬Â Ä‚â€Ă‚Â¯Ă„â€Ă‚Â¡Ä‚â€Ă‚Â»Ä‚â€¦Ă¢â‚¬Å“I
    // DÄ‚â€Ă¢â‚¬ÂÄ‚Â¢Ă¢â‚¬ÂĂ‚Â¢NG HIĂ„â€Ă‚Â¡Ä‚â€Ă‚Â»Ä‚Â¢Ă¢â€Â¬Ă‚Â N
    // TĂ„â€Ă‚Â¡Ä‚â€Ă‚ÂºÄ‚â€Ă‚Â I ---
    private static void manageShops(Scanner sc, ShopService shopservice) {
        while (true) {
            System.out.println("\n=== Quan ly gian hang ===");
            System.out.println("1. Them 1 shop moi");
            System.out.println("2. Xem tat ca cac shop");
            System.out.println("3. Cap nhat shop");
            System.out.println("4. Dong cua shop");
            System.out.println("5. Xoa shop");
            System.out.println("0. Thoat");
            System.out.print("Lua chon: ");
            int option = sc.nextInt();
            sc.nextLine();
            if (option == 0)
                break;
            switch (option) {
                case 1:
                    System.out.print("Ten shop: ");
                    String name = sc.nextLine();
                    System.out.print("Nhap mo ta: ");
                    String mota = sc.nextLine();
                    System.out.print("Nhap dia chi: ");
                    String address = sc.nextLine();
                    Shop s = new Shop(name, mota, address);
                    shopservice.createShop(s);
                    break;
                case 2:
                    System.out.println("Danh sach cac shop: ");
                    shopservice.getAll()
                            .forEach(shop -> System.out
                                    .println(shop.getShopId() + " - " + shop.getName() + " - " + shop.getStatus()));
                    break;
                case 3:
                    System.out.print("Nhap id Shop can sua: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Mo ta moi: ");
                    String desc = sc.nextLine();
                    System.out.print("Dia chi moi: ");
                    String adr = sc.nextLine();
                    Shop update = new Shop();
                    update.setShopId(id);
                    update.setDescription(desc);
                    update.setAddress(adr);
                    shopservice.update(update);
                    break;
                case 4:
                    System.out.print("Nhap id Shop can dong: ");
                    int closed = sc.nextInt();
                    shopservice.close(closed);
                    break;
                case 5:
                    System.out.print("Nhap id Shop can xoa: ");
                    int idremove = sc.nextInt();
                    shopservice.remove(idremove);
                    break;
            }
        }
    }

    private static void manageReviews(Scanner sc, ReviewService reviewService) {
        while (true) {
            System.out.println("\n=== Quan ly danh gia ===");
            System.out.println("1. Them 1 danh gia moi");
            System.out.println("2. Xem danh gia cua shop");
            System.out.println("3. Sua danh gia/Review");
            System.out.println("4. Xoa danh gia/Review");
            System.out.println("0. Thoat");
            System.out.print("Lua chon: ");
            int option2 = sc.nextInt();
            sc.nextLine();
            if (option2 == 0)
                break;
            switch (option2) {
                case 1:
                    System.out.print("Id nguoi danh gia: ");
                    int uId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Shop ID: ");
                    int shopId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Danh gia (tu 1 ->5): ");
                    int rating = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Comment: ");
                    String com = sc.nextLine();
                    Review r = new Review(uId, shopId, rating, com);
                    reviewService.add(r);
                    break;
                case 2:
                    System.out.print("Nhap id shop can lay thong tin: ");
                    int sid = sc.nextInt();
                    reviewService.getbyShopId(sid).forEach(rv -> System.out
                            .println(rv.getUserId() + "-" + rv.getRating() + "*-" + rv.getComment()));
                    break;
                case 3:
                    System.out.print("Id review can sua: ");
                    int rid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("userId review can sua: ");
                    int urid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Rating moi: ");
                    int newrate = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Comment moi: ");
                    String newcom = sc.nextLine();
                    Review reviewmoi = new Review();
                    reviewmoi.setReviewId(rid);
                    reviewmoi.setUserId(urid);
                    reviewmoi.setRating(newrate);
                    reviewmoi.setComment(newcom);
                    reviewService.update(reviewmoi);
                    break;
                case 4:
                    System.out.print("Nhap Id review muon xoa: ");
                    int iddelete = sc.nextInt();
                    sc.nextLine();
                    reviewService.deleteR(iddelete);
                    break;
            }
        }
    }

    private static void manageLocations(Scanner sc, LocationService locateService) {
        while (true) {
            System.out.println("\n=== Quan ly dia chi ===");
            System.out.println("1. Them dia chi theo id nguoi dung");
            System.out.println("2. Lay dia chi theo id nguoi dung");
            System.out.println("3. Thay doi dia chi");
            System.out.println("4. Xoa dia chi");
            System.out.println("0. Thoat");
            System.out.print("Lua chon: ");
            int option3 = sc.nextInt();
            sc.nextLine();
            if (option3 == 0)
                break;
            switch (option3) {
                case 1:
                    System.out.print("Nhap Id nguoi dung: ");
                    int userid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nhap mo ta chi tiet: ");
                    String motadiachi = sc.nextLine();
                    System.out.print("Dien so dien thoai: ");
                    String sdt = sc.nextLine();
                    Location lo = new Location(userid, motadiachi, sdt);
                    locateService.add(lo);
                    break;
                case 2:
                    System.out.print("Nhap id nguoi dung can lay dia chi: ");
                    int idngdung = sc.nextInt();
                    sc.nextLine();
                    locateService.getAll(idngdung).forEach(location -> System.out.println(
                            location.getUserId() + "-" + location.getDetail() + "-" + location.getPhone()));
                    break;
                case 3:
                    System.out.print("Nhap id dia chi can thay doi: ");
                    int idlo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nhap id nguoi dung muon thay doi dia chi: ");
                    int useridlo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nhap mo ta moi: ");
                    String newdetail = sc.nextLine();
                    System.out.print("Nhap sdt moi: ");
                    String newphone = sc.nextLine();
                    Location loca = new Location();
                    loca.setLocationId(idlo);
                    loca.setUserId(useridlo);
                    loca.setPhone(newphone);
                    loca.setDetail(newdetail);
                    locateService.updateLocate(loca);
                    break;
                case 4:
                    System.out.print("Nhap id dia chi can xoa: ");
                    int iddele = sc.nextInt();
                    sc.nextLine();
                    locateService.delteLocate(iddele);
                    break;
            }
        }
    }

    private static void manageCartAndCheckout(Scanner scanner, CartService cartService, OrderService orderService,
            PaymentService paymentService, LocationService locateService, int myUserId) {
        boolean cartMenu = true;
        while (cartMenu) {
            System.out.println("\n=== QUAN LY GIO HANG ===");
            System.out.println("1. Them san pham vao gio");
            System.out.println("2. Xem chi tiet gio hang");
            System.out.println("3. Xoa san pham khoi gio");
            System.out.println("4. Cap nhat so luong");
            System.out.println("5. CHOT DON (Checkout) & Thanh toan");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Nhap ID san pham (Vi du: 101, 102): ");
                    int spId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nhap so luong: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine();
                    cartService.addToCart(new Cart(myUserId, spId, qty));
                    break;
                case 2:
                    cartService.viewCart(myUserId);
                    break;
                case 3:
                    cartService.viewCart(myUserId);
                    System.out.print("Nhap 'Ma dong' can xoa: ");
                    int delId = scanner.nextInt();
                    scanner.nextLine();
                    cartService.removeFromCart(delId);
                    break;
                case 4:
                    cartService.viewCart(myUserId);
                    System.out.print("Nhap 'Ma dong' can cap nhat: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nhap so luong moi: ");
                    int newQty = scanner.nextInt();
                    scanner.nextLine();
                    cartService.updateQuantity(updateId, newQty);
                    break;
                case 5:
                    System.out.println("\n--- TIEN HANH CHOT DON (CHECKOUT) ---");
                    System.out.println("Danh sach dia chi cua ban:");
                    List<Location> locs = locateService.getAll(myUserId);
                    if (locs.isEmpty()) {
                        System.out.println(
                                "Ban chua co dia chi nao! Vui long vao Menu -> Quan ly dia chi de them truoc.");
                        break;
                    }
                    for (Location l : locs) {
                        System.out.println(l.getLocationId() + " - " + l.getDetail() + " - " + l.getPhone());
                    }
                    int locId = -1;
                    while (true) {
                        System.out.print("Chon ID dia chi de giao hang: ");
                        locId = scanner.nextInt();
                        scanner.nextLine();
                        boolean found = false;
                        for (Location l : locs) {
                            if (l.getLocationId() == locId) {
                                found = true;
                                break;
                            }
                        }
                        if (found)
                            break;
                        System.out.println("ID dia chi khong hop le!");
                    }

                    double totalMoney = 1500000; // Gia lap tong tien
                    Order newOrder = new Order(myUserId, totalMoney, "Cho xac nhan");
                    int generatedOrderId = orderService.createOrder(newOrder);

                    if (generatedOrderId > 0) {
                        System.out.println("=> Tao don hang thanh cong! Ma don: #" + generatedOrderId);
                        List<Cart> currentCartItems = cartService.getCartItems(myUserId);
                        orderService.saveOrderDetails(generatedOrderId, currentCartItems);
                        cartService.clearCart(myUserId);

                        System.out.println("\n--- CHON PHUONG THUC THANH TOAN ---");
                        System.out.println("1. Chuyen khoan (BANK_TRANSFER)");
                        System.out.println("2. Tra tien mat khi nhan hang (COD)");
                        System.out.print("Lua chon: ");
                        int payChoice = scanner.nextInt();
                        scanner.nextLine();
                        String method = (payChoice == 1) ? "BANK_TRANSFER" : "COD";

                        Payment pay = new Payment(generatedOrderId, method, totalMoney);
                        paymentService.createPayment(pay);
                        System.out.println("=> Da ghi nhan thanh toan! Quy trinh dat hang hoan tat.");
                    }
                    cartMenu = false;
                    break;
                case 0:
                    cartMenu = false;
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // --- HÄ‚â€Ă¢â‚¬ÂÄ‚Â¢Ă¢â‚¬ÂĂ‚Â¬M TÄ‚â€Ă¢â‚¬ÂÄ‚â€Ă‚ÂCH
    // RIÄ‚â€Ă¢â‚¬ÂÄ‚â€Ă‚ÂNG: XEM LĂ„â€Ă‚Â¡Ä‚â€Ă‚Â»Ä‚â€Ă‚ÂCH
    // SĂ„â€Ă‚Â¡Ä‚â€Ă‚Â»Ä‚â€Ă‚Â¬ TĂ„â€Ă‚Â¡Ä‚â€Ă‚Â»Ä‚â€Ă‚Âª
    // BĂ„â€Ă‚Â¡Ä‚â€Ă‚ÂºÄ‚â€Ă‚Â N TRONG NHÄ‚â€Ă¢â‚¬ÂÄ‚Â¢Ă¢â€Â¬Ă…â€œM ---
    private static void viewBuyerHistory(OrderService orderService, PaymentService paymentService, int myUserId) {
        System.out.println("\n--- LICH SU DON HANG CUA USER " + myUserId + " ---");
        List<Order> history = orderService.getOrderHistory(myUserId);
        if (history != null && !history.isEmpty()) {
            for (Order o : history) {
                String dateStr = (o.getOrderDate() != null)
                        ? new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(o.getOrderDate())
                        : "N/A";
                System.out.printf("Don hang #%d | Tong tien: %,.0f | Trang thai: %s | Ngay: %s\n",
                        o.getOrderId(), o.getTotalAmount(), o.getStatus(), dateStr);
            }
        } else {
            System.out.println("Chua co don hang nao.");
        }

        System.out.println("\n--- BIEN LAI THANH TOAN ---");
        paymentService.viewReceipts();
    }

    // --- HĂ„â€Ă¢â€Â¬M TĂ„â€Ă‚ÂCH RIĂ„â€Ă‚ÂNG: QU?N L? DANH M?C (T?
    // Ä‚â€Ă‚Â?C) ---
    private static void manageCategories(Scanner sc, CategoryService catManager) {
        int catChoice;
        do {
            System.out.println("\n--- [MENU] QUAN LY DANH MUC NGANH HANG ---");
            System.out.println("1. Xem danh sach danh muc");
            System.out.println("2. Them moi danh muc");
            System.out.println("3. Cap nhat danh muc");
            System.out.println("4. Xoa danh muc");
            System.out.println("0. Quay lai Menu chinh");
            System.out.print("Chon chuc nang (0-4): ");
            catChoice = inputInt(sc);

            switch (catChoice) {
                case 1 -> catManager.displayAll();
                case 2 -> {
                    System.out.print("Nhap ten danh muc: ");
                    String name = sc.nextLine();
                    System.out.print("Nhap mo ta: ");
                    String desc = sc.nextLine();
                    catManager.createCategory(new Category(0, name, desc));
                }
                case 3 -> {
                    System.out.print("Nhap ID danh muc can sua: ");
                    int id = inputInt(sc);
                    if (!catManager.checkIdExists(id)) {
                        System.out.println("Loi: Khong tim thay danh muc co ID = " + id);
                        continue;
                    }
                    System.out.print("Ten moi: ");
                    String name = sc.nextLine();
                    System.out.print("Mo ta moi: ");
                    String desc = sc.nextLine();
                    catManager.editCategory(new Category(id, name, desc));
                }
                case 4 -> {
                    System.out.print("Nhap ID danh muc can xoa: ");
                    int id = inputInt(sc);
                    catManager.removeCategory(id);
                }
            }
        } while (catChoice != 0);
    }

    // --- HÀM TÁCH RIÊNG: QUẢN LÝ SẢN PHẨM ---
    private static void manageProducts(Scanner sc, ProductService prodManager, CategoryService catManager,
            ShopService shopservice) {
        int prodChoice;
        do {
            System.out.println("\n--- [MENU] QUAN LY SAN PHAM ---");
            System.out.println("1. Xem danh sach san pham");
            System.out.println("2. Them moi san pham");
            System.out.println("3. Cap nhat san pham (Gia, so luong...)");
            System.out.println("4. Xoa san pham");
            System.out.println("0. Quay lai Menu chinh");
            System.out.print("Chon chuc nang (0-4): ");
            prodChoice = inputInt(sc);

            switch (prodChoice) {
                case 1 -> prodManager.displayAll();
                case 2 -> {
                    System.out.print("Ten san pham: ");
                    String name = sc.nextLine();
                    while (name.trim().isEmpty() || isAllNumbers(name)) {
                        if (name.trim().isEmpty()) {
                            System.out.print("Ten khong duoc de trong, nhap lai: ");
                        } else {
                            System.out.print("Ten san pham khong duoc chi chua so, nhap lai: ");
                        }
                        name = sc.nextLine();
                    }
                    double price;
                    while (true) {
                        System.out.print("Gia ban: ");
                        price = inputDouble(sc);
                        if (price > 0)
                            break;
                        else
                            System.out.println("Gia ban phai lon hon 0! Vui long nhap lai.");
                    }

                    int qty;
                    while (true) {
                        System.out.print("So luong kho: ");
                        qty = inputInt(sc);
                        if (qty > 0)
                            break;
                        else
                            System.out.println("So luong phai lon hon 0! Vui long nhap lai.");
                    }
                    int catId;
                    while (true) {
                        System.out.print("Nhap ma danh muc (Category ID): ");
                        catId = inputInt(sc);
                        if (catManager.checkIdExists(catId))
                            break;
                        else
                            System.out.println("Ma danh muc khong ton tai! Vui long nhap lai.");
                    }
                    int shopId;
                    while (true) {
                        System.out.print("Nhap ID Gian hang cua ban (Shop ID): ");
                        shopId = inputInt(sc);
                        boolean found = false;
                        for (Shop s : shopservice.getAll()) {
                            if (s.getShopId() == shopId) {
                                found = true;
                                break;
                            }
                        }
                        if (found)
                            break;
                        System.out.println("ID Gian hang khong hop le hoac khong ton tai!");
                    }
                    prodManager.createProduct(new Product(0, name, price, qty, catId, shopId));
                }
                case 3 -> {
                    System.out.print("Nhap ID san pham can sua: ");
                    int id = inputInt(sc);
                    if (!prodManager.checkIdExists(id)) {
                        System.out.println("Loi: Khong tim thay san pham co ID = " + id + " de cap nhat!");
                        continue;
                    }
                    System.out.print("Ten moi: ");
                    String name = sc.nextLine();
                    System.out.print("Gia moi: ");
                    double price = inputDouble(sc);
                    System.out.print("So luong kho moi: ");
                    int qty = inputInt(sc);
                    int catId;
                    while (true) {
                        System.out.print("Nhap ma danh muc moi (Category ID): ");
                        catId = inputInt(sc);
                        if (catManager.checkIdExists(catId))
                            break;
                        else
                            System.out.println("Ma danh muc khong ton tai! Vui long nhap lai.");
                    }
                    int shopId;
                    while (true) {
                        System.out.print("Nhap ID Gian hang moi (Shop ID): ");
                        shopId = inputInt(sc);
                        boolean found = false;
                        for (Shop s : shopservice.getAll()) {
                            if (s.getShopId() == shopId) {
                                found = true;
                                break;
                            }
                        }
                        if (found)
                            break;
                        System.out.println("ID Gian hang khong hop le hoac khong ton tai!");
                    }
                    prodManager.editProduct(new Product(id, name, price, qty, catId, shopId));
                }
                case 4 -> {
                    System.out.print("Nhap ID san pham can xoa: ");
                    int id = inputInt(sc);
                    prodManager.removeProduct(id);
                }
            }
        } while (prodChoice != 0);
    }

    // --- HĂ„â€Ă¢â€Â¬M TĂ„â€Ă‚ÂCH RIĂ„â€Ă‚ÂNG: T?M KI?M VĂ„â€Ă¢â€Â¬ L?C S?N
    // PH?M ---
    private static void searchProducts(Scanner sc, ProductService prodManager, CategoryService catManager) {
        int filterChoice;
        do {
            System.out.println("\n--- CHON CHE DO LOC SAN PHAM ---");
            System.out.println("1. Tim kiem theo ten san pham");
            System.out.println("2. Loc theo khoang gia");
            System.out.println("3. Loc theo danh muc san pham");
            System.out.println("0. Tro ve");
            System.out.print("Lua chon (0-3): ");
            filterChoice = inputInt(sc);

            switch (filterChoice) {
                case 1 -> {
                    System.out.print("Nhap tu khoa ten san pham: ");
                    prodManager.searchProductsByName(sc.nextLine());
                }
                case 2 -> {
                    System.out.print("Gia toi thieu: ");
                    double min = inputDouble(sc);
                    System.out.print("Gia toi da: ");
                    double max = inputDouble(sc);
                    prodManager.filterProductsByPrice(min, max);
                }
                case 3 -> {
                    int catId;
                    while (true) {
                        System.out.print("Nhap ID danh muc de loc: ");
                        catId = inputInt(sc);
                        if (catManager.checkIdExists(catId))
                            break;
                        else
                            System.out.println("ID danh muc khong ton tai! Nhap lai.");
                    }
                    prodManager.filterProductsByCategory(catId);
                }
            }
        } while (filterChoice != 0);
    }

    private static boolean isAllNumbers(String str) {
        return str.trim().matches("-?\\d+(\\.\\d+)?");
    }

    private static int inputInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Loi! Vui long nhap so nguyen: ");
            }
        }
    }

    private static double inputDouble(Scanner sc) {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Loi! Vui long nhap so thuc (VD: 15.5): ");
            }
        }
    }
}
