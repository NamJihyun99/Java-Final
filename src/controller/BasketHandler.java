package controller;

import product.dto.ProductInfoResponse;
import product.dto.ProductsResponse;
import product.service.ProductService;
import user.domain.User;
import user.dto.BasketItemSaveRequest;
import user.dto.BasketItemUpdateRequest;
import user.dto.BasketResponse;
import user.service.BasketService;
import view.MemberView;

import java.util.List;

public class BasketHandler {

    private final MemberView memberView;
    private final ProductService productService;
    private final BasketService basketService;

    public BasketHandler(MemberView memberView, ProductService productService, BasketService basketService) {
        this.memberView = memberView;
        this.productService = productService;
        this.basketService = basketService;
    }

    List<ProductsResponse> getProducts() {
        return productService.getProducts();
    }

    // 1. 상품 목록
     void readProducts(String userId, List<ProductsResponse> products) {
        String productId = memberView.pickProduct(products);
        ProductInfoResponse product = productService.getProduct(productId);
        memberView.printProductInfo(product);
        // 장바구니에 담을지 선택
        String answer = memberView.addBasket();
        if ("y".equals(answer)) {
            BasketItemSaveRequest saveRequest = memberView.getBasketItemSaveRequest(productId);
            basketService.addItem(userId, saveRequest);
            memberView.printBasketItemSaveMsg();
        } else if (!"n".equals(answer)) {
            throw new IllegalArgumentException("잘못된 입력입니다. y 또는 n을 입력하세요");
        }
    }

    // 2. 상품 검색 후 목록 조회
    void search(String userId) {
        String keyword = memberView.getSearchKeyword();
        List<ProductsResponse> products = productService.getProductsByKeyword(keyword);
        readProducts(userId, products);
    }

    // 3. 장바구니 조회
    void readBasket(User member) {
        BasketResponse basket = basketService.findAllItems(member.getUserId());
        int pick = memberView.selectBasketMenu(basket);
        try {
            switch (pick) {
                case 1 -> updateBasketItem();
                case 2 -> removeBasketItem();
                case 3 -> clearBasket(member);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    // 3 - (1) 장바구니 품목 수정
    void updateBasketItem() {
        BasketItemUpdateRequest updateRequest = memberView.getItemUpdateRequest();
        basketService.updateItem(updateRequest);
        memberView.showItemUpdateSuccessMsg();
    }

    // 3 - (2) 장바구니 품목 삭제
    void removeBasketItem() {
        int itemId = memberView.pickItemToRemove();
        String yn = memberView.askItemToRemove();
        if ("y".equals(yn)) {
            basketService.removeItem(itemId);
            System.out.println("성공적으로 삭제되었습니다.");
        } else if (!"n".equals(yn)) {
            System.out.println("잘못된 입력입니다.");
        }
    }

    // 3 - (3) 장바구니 비우기
    void clearBasket(User member) {
        String answer = memberView.clearBasket();
        if ("y".equals(answer)) {
            basketService.clearItems(member.getUserId());
        } else if (!"n".equals(answer)) {
            throw new IllegalArgumentException("올바르지 않은 입력입니다");
        }
    }
}
