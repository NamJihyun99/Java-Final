package user.domain;

public class BasketItem {

    private int id;
    private String productId;
    private int order;
    private String userId;
    private int price;
    private int quantity;
    private int total;

    public BasketItem(int order, String productId, String userId, int price, int quantity) {
        this.productId = productId;
        this.userId = userId;
        this.order = order;
        this.price = price;
        this.quantity = quantity;
        this.total = quantity * price;
    }

    public BasketItem(int id, int order, String productId, String userId, int price, int quantity, int total) {
        this.id = id;
        this.order = order;
        this.productId = productId;
        this.userId = userId;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

    public int getOrder() {
        return order;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotal() {
        return total;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}
