package basket.domain;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    private String basketId;
    private String userId;
    private List<BasketItem> items = new ArrayList<>();

    public Basket(String userId) {
        this.userId = userId;
    }
}
