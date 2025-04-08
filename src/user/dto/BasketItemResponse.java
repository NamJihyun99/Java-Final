package user.dto;

import user.domain.BasketItem;

public class BasketItemResponse {

    private final int basketItemId;
    private final String productName;
    private final int price;
    private final int number;
    private final int total;

    public BasketItemResponse(int basketItemId, String name, int price, int number, int total) {
        this.basketItemId = basketItemId;
        this.productName = name;
        this.price = price;
        this.number = number;
        this.total = total;
    }

    public static BasketItemResponse of(BasketItem item, String productName) {
        return new BasketItemResponse(item.getId(), productName, item.getPrice(), item.getQuantity(), item.getTotal());
    }

    public int getId() {
        return basketItemId;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public int getTotal() {
        return total;
    }
}
