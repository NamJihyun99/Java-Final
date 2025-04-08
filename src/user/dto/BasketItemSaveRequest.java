package user.dto;

import product.domain.Product;
import user.domain.BasketItem;

public class BasketItemSaveRequest {

    private final String productId;
    private final int quantity;

    public BasketItemSaveRequest(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BasketItem toBasketItem(Product product, String userId) {
        return new BasketItem(0, productId, userId, product.getSalePrice(), quantity);
    }
}
