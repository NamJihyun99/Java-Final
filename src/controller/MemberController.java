package controller;

import user.domain.User;
import view.MemberView;

public class MemberController {

    private final MemberView memberView;
    private final BasketHandler basketHandler;
    private final UserHandler userHandler;
    private final AdminHandler adminHandler;

    public MemberController(MemberView memberView, BasketHandler basketHandler, UserHandler userHandler, AdminHandler adminHandler) {
        this.memberView = memberView;
        this.basketHandler = basketHandler;
        this.userHandler = userHandler;
        this.adminHandler = adminHandler;
    }

    public void run(User member) {
        memberView.printMyName(member.isAdmin(), member.getName());
        while (true) {
            try {
                int pick = memberView.selectMenu(member.isAdmin());
                switch (pick) {
                    case 1 -> basketHandler.readProducts(member.getUserId(), basketHandler.getProducts());   // 1. 상품 목록
                    case 2 -> basketHandler.search(member.getUserId()); // 2. 상품 검색
                    case 3 -> basketHandler.readBasket(member);   //  3. 장바구니 조회
                    case 4 -> userHandler.print(member);    // 4. 회원 정보 조회
                    case 5 -> userHandler.update(member);   // 5. 회원 정보 수정
                    case 6 -> {                 // 6. 로그아웃(처음으로)
                        System.out.println("로그아웃 합니다.");
                        return;
                    }
                    case 7 -> {       // 7. 회원 탈퇴
                        userHandler.quit(member);
                        return;
                    }
                    case 8 -> adminHandler.handleProducts(); // 8. 상품 관리
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
