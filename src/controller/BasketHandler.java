package controller;

import product.dto.ProductInfoResponse;
import product.dto.ProductsResponse;
import product.service.ProductService;
import user.dto.BasketItemSaveRequest;
import user.service.BasketService;
import view.MemberView;

import java.util.List;

public class ProductHandler {

    private final MemberView memberView;
    private final ProductService productService;
    private final BasketService basketService;

    // 1. 상품 목록
    private void readProducts(String userId, List<ProductsResponse> products) {
        String productId = memberView.pickProduct(products);
        ProductInfoResponse product = productService.getProduct(productId);
        memberView.printProductInfo(product);
        // 장바구니에 담을지 선택
        String answer = memberView.addBasket();
        if ("y".equals(answer)) {
            BasketItemSaveRequest saveRequest = memberView.getBasketItemSaveRequest(productId);
            basketService.addItem(userId, saveRequest);
        } else if (!"n".equals(answer)) {
            throw new IllegalArgumentException("잘못된 입력입니다. y 또는 n을 입력하세요");
        }
    }


}
