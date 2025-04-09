package user.dao;

import user.domain.BasketItem;

import java.util.List;

public interface BasketItemDao {

    void save(BasketItem basketItem, int basketId);
    BasketItem findById(int basketItemId);
    void flush(BasketItem basketItem);
    List<BasketItem> findAllByUserId(String userId);
    void clear(String userId);
    void delete(int itemId);
    void deleteByProductId(String id);
}
