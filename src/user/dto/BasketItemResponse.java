package user.dto;

import user.domain.Basket;
import user.domain.BasketItem;

import java.util.List;

public class BasketResponse {

    private final List<BasketItem> items;
    private final int price;
    private final int number;
    private final int total;

    private BasketResponse(List<BasketItem> items, int price, int number) {
        this.items = items;
        this.price = price;
        this.number = number;
        this.total = price * number;
    }

    public static BasketResponse of(Basket basket) {
        return new BasketResponse(basket.getItems(), baske.get)
    }
}
