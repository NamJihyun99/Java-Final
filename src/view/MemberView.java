package view;

import common.ReaderUtil;
import product.dto.*;
import user.dto.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MemberView {

    public void printMyName(boolean isAdmin, String name) {
        if (isAdmin) {
            System.out.print("관리자 ");
        }
        System.out.printf("%s님 환영합니다.\n", name);
    }

    /**
     * Member Menu
     */

    public int selectMenu(boolean isAdmin) {
        System.out.println("========================== 회원 메뉴 ============================");
        String menu = "1. 상품 목록\n2. 상품명 검색\n3. 장바구니 조회\n4. 회원 정보 조회\n5. 회원 정보 수정\n6. 로그아웃(처음으로)\n7. 회원 탈퇴";
        int option = 7;
        if (isAdmin) {
            menu += "\n8. 관리자 메뉴";
            option = 8;
        }
        System.out.println(menu);
        return ReaderUtil.getValidOption(option);
    }

    // 1. 상품 목록
    public String pickProduct(List<ProductsResponse> products) {
        printProducts(products);
        System.out.print("조회할 상품의 ID를 입력하세요 (Enter는 뒤로 가기) >> ");
        return validateStringEmpty(ReaderUtil.read());
    }

    public void printProducts(List<ProductsResponse> products) {
        System.out.println("==================== 구매 가능한 상품 목록 =========================");
        System.out.printf("%10s\t%20s\t%10s\n", "ID", "상품명", "가격");
        products.forEach(product -> System.out.printf("%10s\t%20s\t%10d\n", product.getId(), product.getName(), product.getSalePrice()));
        System.out.println("===============================================================");
    }

    public String getSearchKeyword() {
        System.out.print("검색어를 입력하세요 >> ");
        return validateStringEmpty(ReaderUtil.read());
    }

    // 1 - (1) 상품 상세 조회
    public void printProductInfo(ProductInfoResponse product) {
        System.out.println("\n====================== 제품 상세 정보 ============================");
        System.out.println("상품명 : " + product.getProductName());
        System.out.println("설명  : " + product.getDetail());
        System.out.println("가격  : " + product.getPrice());
        System.out.println("===============================================================");
    }

    // 1 - (2) 장바구니 담기
    public String addBasket() {
        System.out.print("이 상품을 장바구니에 담으시겠습니까? (y/n) >> ");
        return ReaderUtil.read();
    }

    // 장바구니에 담을 상품의 개수
    public BasketItemSaveRequest getBasketItemSaveRequest(String productId) {
        System.out.print("담을 개수를 입력해주세요 >> ");
        int quantity = validateNumber(ReaderUtil.read());
        return new BasketItemSaveRequest(productId, quantity);
    }

    // 2. 장바구니 조회
    public int selectBasketMenu(BasketResponse basket) {
        System.out.println("======================== 나의 장바구니 ===========================");
        System.out.printf("%5s\t%15s\t%10s\t%10s\t%10s\n", "ID", "상품명", "가격", "개수", "총액");
        basket.getItems().forEach(item ->
                System.out.printf("%5s\t%15s\t%10d\t%10s\t%10s\n",
                        item.getId(), item.getProductName(), item.getPrice(), item.getNumber(), item.getTotal()));
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%5s%55d\n", "합계", basket.getTotal());
        System.out.println("===============================================================");
        System.out.println("1. 품목 수정\n2. 품목 삭제\n3. 장바구니 비우기\n4. 뒤로 가기");
        return ReaderUtil.getValidOption(4);
    }

    // 2 - (1) 장바구니 품목 수정
    public BasketItemUpdateRequest getItemUpdateRequest() {
        System.out.print("수정할 품목의 ID를 선택하세요 >> ");
        int itemId = validateNumber(ReaderUtil.read());
        System.out.print("개수를 입력하세요 >> ");
        int newQuantity = validateNumber(ReaderUtil.read());
        return new BasketItemUpdateRequest(itemId, newQuantity);
    }

    public void showItemUpdateSuccessMsg() {
        System.out.println("성공적으로 수정되었습니다.");
    }

    // 2 - (2) 장바구니 품목 삭제
    public int pickItemToRemove() {
        System.out.print("삭제할 품목의 ID를 선택하세요 >> ");
        return validateNumber(ReaderUtil.read());
    }

    public String askItemToRemove() {
        System.out.print("이 품목을 지우시겠습니까? (y/n) >> ");
        return ReaderUtil.read();
    }

    // 2 - (3)
    public String clearBasket() {
        System.out.print("정말 비우시겠습니까? (y/n) >> ");
        return ReaderUtil.read();
    }

    // 3. 회원 정보 조회
    public void printMemberInfo(MemberInfoResponse response) {
        System.out.println("이름 : " + response.getName());
        System.out.println("이메일 : " + response.getEmail());
        System.out.println("휴대폰 : " + response.getPhone());
    }

    // 4. 회원 정보 수정
    public int updateInfoMenu() {
        System.out.println("1. 비밀번호 변경 | 2. 이름 및 전화번호 수정 | 3. 뒤로 가기");
        return ReaderUtil.getValidOption(3);
    }

    // 4 - (1) 비밀 번호 변경
    public PasswordUpdateRequest getPasswordUpdateRequest() {
        System.out.print("기존 비밀번호 >> ");
        String oldPassword = ReaderUtil.read();
        System.out.print("새 비밀번호 >> ");
        String password1 = ReaderUtil.read();
        System.out.print("새 비밀번호 다시 입력 >> ");
        String password2 = ReaderUtil.read();

        return new PasswordUpdateRequest(oldPassword, password1, password2);
    }

    // 4 - (2) 이름 및 전화번호 수정
    public UserUpdateRequest getUpdateRequest() {
        System.out.println("수정할 이름과 전화번호를 입력해주세요.");
        System.out.print("이름 >> ");
        String name = ReaderUtil.read();
        System.out.print("전화번호 >> ");
        String phone = ReaderUtil.read();

        return new UserUpdateRequest(name, phone);
    }

    // 6. 회원 탈퇴
    public LoginRequest getRemoveRequest() {
        System.out.print("정말 탈퇴하시겠습니까?(y/n) >> ");
        String answer = ReaderUtil.read().toLowerCase();
        if (answer.equals("y")) {
            System.out.println("재로그인이 필요합니다.");
            System.out.print("이메일을 입력하세요 >> ");
            String email = ReaderUtil.read();
            System.out.print("비밀번호를 입력하세요 >> ");
            String password = ReaderUtil.read();

            return new LoginRequest(email, password);
        } else if (!answer.equals("n")) {
            System.out.println("잘못된 입력입니다.");
        }
        return null;
    }

    /**
     * Admin Menu
     */

    // 7. 상품 관리
    public int selectAdminMenu() {
        System.out.println("========================= 관리자 메뉴 ===========================");
        System.out.println("1. 상품 등록 | 2. 상품 수정 | 3. 상품 삭제 | 4. 재고 관리 | 5. 뒤로 가기");
        System.out.println("===============================================================");
        return ReaderUtil.getValidOption(5);
    }

    // 관리자용 상품 목록 조회
    public void printProductsForAdmin(List<ProductsAdminResponse> products) {
        System.out.println("================================= 재고 현황 =======================================");
        System.out.printf("%10s\t%15s\t%10s\t%5s\t%10s\t%10s\n", "ID", "상품명", "가격", "재고", "시작일자", "종료일자");
        products.forEach(product ->
                System.out.printf("%10s\t%15s\t%10d\t%5d\t%12s\t%12s\n",
                        product.getId(), product.getName(), product.getSalePrice(), product.getQuantity(), product.getStartDate(), product.getEndDate())
        );
        System.out.println("=================================================================================");
    }

    // 7 - (1) 상품 등록
    public ProductSaveRequest getProductSaveRequest() {
        try {
            System.out.println("새로운 상품을 등록합니다.");
            System.out.print("상품 이름 >> ");
            String name = ReaderUtil.read();

            System.out.print("상세 설명 >> ");
            String detail = ReaderUtil.read();

            System.out.print("판매 시작 일자 (yyyy-MM-dd) >> ");
            LocalDate startDate = validateDate(ReaderUtil.read());

            System.out.print("판매 종료 일자 (yyyy-MM-dd) >> ");
            LocalDate endDate = validateDate(ReaderUtil.read());

            validateDuration(startDate, endDate);

            System.out.print("가격 >> ");
            int salePrice = validateNumber(ReaderUtil.read());

            System.out.print("재고 수량 >> ");
            int quantity = validateNumber(ReaderUtil.read());

            return new ProductSaveRequest(name, detail, startDate, endDate, salePrice, quantity);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // 7 - (2) 상품 정보 수정
    public String pickProductToUpdate() {
        System.out.print("정보를 수정할 상품의 ID를 입력하세요 (Enter는 뒤로 가기) >> ");
        return validateStringEmpty(ReaderUtil.read());
    }

    public ProductUpdateRequest getProductUpdateRequest() {
        System.out.println("상품 정보를 수정합니다.");

        System.out.print("상품 이름 >> ");
        String name = ReaderUtil.read();

        System.out.print("상세 설명 >> ");
        String detail = ReaderUtil.read();

        System.out.print("가격 >> ");
        int salePrice = validateNumber(ReaderUtil.read());

        return new ProductUpdateRequest(name, detail, salePrice);
    }

    private int validateNumber(String input) {
        try {
            int value = Integer.parseInt(input);
            if (value < 0) {
                throw new IllegalArgumentException("0 또는 양수를 입력하세요.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new RuntimeException("숫자를 입력하세요");
        }
    }

    private String validateStringEmpty(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("빈 값 입니다");
        }
        return input;
    }

    private LocalDate validateDate(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("날짜 형식에 맞게 입력하세요");
        }
    }

    private void validateDuration(LocalDate startDate, LocalDate endDate) {
        if (!startDate.isBefore(endDate)) {
            throw new IllegalArgumentException("시작일자와 종료일자가 올바르지 않습니다. 종료일자는 시작일자 이후여야 합니다");
        }
    }

    // 7 - (3) 상품 정보 삭제
    public String pickProductToRemove() {
        System.out.print("삭제할 상품의 ID를 입력하세요 (Enter는 뒤로 가기) >> ");
        return validateStringEmpty(ReaderUtil.read());
    }

    public String askProductToRemove() {
        System.out.print("정말 삭제하시겠습니까? (y/n) >> ");
        return validateStringEmpty(ReaderUtil.read());
    }

    // 7 - (4) 재고 관리 (재고 업데이트 및 품절 처리)
    public String pickProductToUpdateQuantity() {
        System.out.print("재고를 업데이트하거나 품절 처리할 상품의 ID를 입력하세요 (Enter는 뒤로 가기) >> ");
        return validateStringEmpty(ReaderUtil.read());
    }

    public int readProductQuantity() {
        System.out.print("업데이트할 개수를 입력하세요(0은 품절) >> ");
        return validateNumber(ReaderUtil.read());
    }

    public void printUpdateSuccessMsg() {
        System.out.println("성공적으로 업데이트되었습니다.");
    }
}
