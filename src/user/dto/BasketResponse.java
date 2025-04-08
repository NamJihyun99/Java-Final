package user.dto;

import java.util.List;

public class BasketResponse {
    private final List<BasketItemResponse> items;
    private int total;

    public BasketResponse(List<BasketItemResponse> items) {
        this.items = items;
        this.total = 0;
        for (BasketItemResponse item : items) {
            this.total += item.getTotal();
        }
    }

    public List<BasketItemResponse> getItems() {
        return items;
    }

    public int getTotal() {
        return total;
    }
}
