package user.domain;

public class Basket {

    private int basketId;
    private String userId;
    private int total;

    public Basket(String userId) {
        this.userId = userId;
        this.total = 0;
    }

    public Basket(int basketId, String userId, int total) {
        this.basketId = basketId;
        this.userId = userId;
        this.total = total;
    }

    public int getBasketId() {
        return basketId;
    }

    public String getUserId() {
        return userId;
    }

    public int getTotal() {
        return total;
    }

    public void updateTotal(int total) {
        this.total += total;
    }

}
