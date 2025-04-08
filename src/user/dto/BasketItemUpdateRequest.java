package user.dto;

public class BasketItemUpdateRequest {

    private final int basketItemId;
    private final int quantity;

    public BasketItemUpdateRequest(int basketItemId, int quantity) {
        this.basketItemId = basketItemId;
        this.quantity = quantity;
    }

    public int getBasketItemId() {
        return basketItemId;
    }

    public int getQuantity() {
        return quantity;
    }
}
