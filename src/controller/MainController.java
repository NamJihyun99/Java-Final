package controller;

import common.LoginSession;
import product.dto.ProductsResponse;
import product.service.ProductService;
import user.domain.User;
import user.dto.LoginRequest;
import user.dto.UserSaveRequest;
import user.service.UserService;
import view.MainView;

import java.time.LocalDate;
import java.util.List;

public class MainController {

    private final MemberController memberController;
    private final MainView mainView;
    private final UserService userService;
    private final ProductService productService;

    public MainController(MemberController memberController, MainView mainView, UserService userService, ProductService productService) {
        this.memberController = memberController;
        this.mainView = mainView;
        this.userService = userService;
        this.productService = productService;
    }

    public void run() {
        while (true) {
            try {
                int pick = mainView.selectMenu();
                // [환영합니다!] 1. 회원 가입 | 2. 로그인 | 3. 상품 목록 | 4. 종료
                switch (pick) {
                    case 1 -> handleSignUp();
                    case 2 -> handleSignIn();
                    case 3 -> showAnonymousProducts(productService.getProducts());
                    case 4 -> {
                        return;
                    }
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleSignUp() {
        UserSaveRequest saveRequest = mainView.getSaveRequest();
        userService.save(saveRequest, LocalDate.now());
        mainView.printSignUpSuccessMsg();
    }

    private void handleSignIn() {
        LoginRequest loginRequest = mainView.getLoginRequest();
        if (loginRequest == null) return;
        User user = userService.checkSignIn(loginRequest);
        LoginSession.login(user);
        mainView.printLoginSuccessMsg();
        memberController.run(user);
        LoginSession.logout();
    }

    public void showAnonymousProducts(List<ProductsResponse> products) {
        System.out.println("==================== 구매 가능한 상품 목록 =========================");
        System.out.printf("%10s\t%20s\t%10s\n", "ID", "상품명", "가격");
        products.forEach(product -> System.out.printf("%10s\t%20s\t%10d\n", product.getId(), product.getName(), product.getSalePrice()));
        System.out.println("===============================================================");
    }
}
