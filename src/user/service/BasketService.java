package user.service;

import product.dao.ProductDao;
import product.domain.Product;
import user.dao.BasketDao;
import user.dao.BasketItemDao;
import user.domain.Basket;
import user.domain.BasketItem;
import user.dto.BasketItemResponse;
import user.dto.BasketItemSaveRequest;
import user.dto.BasketItemUpdateRequest;
import user.dto.BasketResponse;

import java.util.List;
import java.util.Map;

public class BasketService {

    private final BasketItemDao basketItemDao;
    private final ProductDao productDao;
    private final BasketDao basketDao;

    public BasketService(ProductDao productDao, BasketItemDao basketItemDao, BasketDao basketDao) {
        this.productDao = productDao;
        this.basketItemDao = basketItemDao;
        this.basketDao = basketDao;
    }

    public void addItem(String userId, BasketItemSaveRequest saveRequest) {
        Basket basket = basketDao.findByUserId(userId);
        Product product = productDao.findById(saveRequest.getProductId());

        checkQuantities(saveRequest.getQuantity(), product);

        basket.updateTotal(saveRequest.getQuantity() * product.getSalePrice());
        basketDao.flush(basket);

        List<BasketItem> items = basketItemDao.findAllByUserId(userId);
        for (BasketItem item : items) {
            // 이미 존재하는지 확인
            if (item.getProductId().equals(product.getId())) {
                item.increaseQuantity(saveRequest.getQuantity());
                basketItemDao.flush(item);
                return;
            }
        }
        // 신규 저장
        basketItemDao.save(saveRequest.toBasketItem(product, userId), basket.getBasketId());
    }

    public BasketResponse findAllItems(String userId) {
        List<BasketItem> items = basketItemDao.findAllByUserId(userId);
        if (items.isEmpty()) {
            return new BasketResponse(List.of());
        }

        List<String> productIds = items.stream().map(BasketItem::getProductId).toList();
        Map<String, Product> productsById = productDao.findAllByIds(productIds);
        List<BasketItemResponse> responses = items.stream()
                .map(item -> BasketItemResponse.of(item, productsById.get(item.getProductId()).getName()))
                .toList();
        return new BasketResponse(responses);
    }

    public void updateItem(BasketItemUpdateRequest updateRequest) {
        BasketItem basketItem = basketItemDao.findById(updateRequest.getBasketItemId());
        Product product = productDao.findById(basketItem.getProductId());
        checkQuantities(updateRequest.getQuantity(), product);
        basketItem.updateQuantity(updateRequest.getQuantity());
        basketItemDao.flush(basketItem);
    }

    private void checkQuantities(int updateRequest, Product product) {
        if (updateRequest > product.getQuantity()) {
            throw new IllegalArgumentException("현재 남아 있는 재고보다 큰 값을 입력할 수 없습니다.");
        }
    }

    public void removeItem(int itemId) {
        basketItemDao.delete(itemId);
    }

    public void clearItems(String userId) {
        basketItemDao.clear(userId);
    }
}
