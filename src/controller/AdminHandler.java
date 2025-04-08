package controller;

import product.dto.ProductSaveRequest;
import product.dto.ProductUpdateRequest;
import product.service.ProductService;
import view.MemberView;

import java.time.LocalDate;

public class AdminHandler {

    private final MemberView memberView;
    private final ProductService productService;

    public AdminHandler(MemberView memberView, ProductService productService) {
        this.memberView = memberView;
        this.productService = productService;
    }

    // 8. 상품 관리
    void handleProducts() {
        while (true) {
            try {
                int option = memberView.selectAdminMenu();
                switch (option) {
                    case 1 -> saveNewProduct(); // (1) 상품 등록
                    case 2 -> updateProductInfo();  // (2) 상품 수정
                    case 3 -> removeProductInfo();  // (3) 상품 삭제
                    case 4 -> updateProductQuantity();  // (4) 재고 관리
                    case 5 -> {
                        return;
                    }   // (5) 뒤로 가기
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // (1) 상품 등록
    private void saveNewProduct() {
        ProductSaveRequest request = memberView.getProductSaveRequest();
        if (request != null) productService.save(request, LocalDate.now());
    }

    // (2) 상품 수정
    private void updateProductInfo() {
        memberView.printProductsForAdmin(productService.getProductsForAdmin());
        String id = memberView.pickProductToUpdate();
        ProductUpdateRequest updateRequest = memberView.getProductUpdateRequest();
        productService.update(id, updateRequest);
    }

    // (3) 상품 삭제
    private void removeProductInfo() {
        memberView.printProductsForAdmin(productService.getProductsForAdmin());
        String id = memberView.pickProductToRemove();
        String yn = memberView.askProductToRemove();
        if ("y".equals(yn)) {
            productService.delete(id);
        } else if (!"n".equals(yn)) {
            throw new IllegalArgumentException("잘못된 입력입니다. y 또는 n을 입력해주세요.");
        }
    }

    // (4) 재고 관리
    private void updateProductQuantity() {
        memberView.printProductsForAdmin(productService.getProductsForAdmin());
        String id = memberView.pickProductToUpdateQuantity();
        int quantity = memberView.readProductQuantity();
        productService.update(id, quantity);
        memberView.printUpdateSuccessMsg();
    }
}
