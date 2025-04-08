package user.dao;

import user.domain.Basket;

public interface BasketDao {

    void save(Basket basket);
    Basket findByUserId(String userId);
    void flush(Basket basket);
}
