package view;

import common.ReaderUtil;
import user.dto.LoginRequest;
import user.dto.MemberInfoResponse;
import user.dto.PasswordUpdateRequest;
import user.dto.UserUpdateRequest;

public class UserView {

    public void printMyName(String name) {
        System.out.printf("%s님 환영합니다.\n", name);
    }

    public int selectMenu() {
        System.out.println("1. 상품 목록 | 2. 장바구니 조회 | 3. 회원 정보 조회 | 4. 회원 정보 수정 | 5. 로그아웃(처음으로) | 6. 회원 탈퇴");
        return ReaderUtil.getValidOption(6);
    }

    public void printMemberInfo(MemberInfoResponse response) {
        System.out.println("이름 : " + response.getName());
        System.out.println("이메일 : " + response.getEmail());
        System.out.println("휴대폰 : " + response.getPhone());
    }

    public int updateInfoMenu() {
        System.out.println("1. 비밀번호 변경 | 2. 이름 및 전화번호 수정 | 3. 뒤로 가기");
        return ReaderUtil.getValidOption(3);
    }

    public PasswordUpdateRequest getPasswordUpdateRequest() {
        System.out.print("기존 비밀번호 >> ");
        String oldPassword = ReaderUtil.read();
        System.out.print("새 비밀번호 >> ");
        String password1 = ReaderUtil.read();
        System.out.print("새 비밀번호 다시 입력 >> ");
        String password2 = ReaderUtil.read();

        return new PasswordUpdateRequest(oldPassword, password1, password2);
    }

    public UserUpdateRequest getUpdateRequest() {
        System.out.println("수정할 이름과 전화번호를 입력해주세요.");
        System.out.print("이름 >> ");
        String name = ReaderUtil.read();
        System.out.print("전화번호 >> ");
        String phone = ReaderUtil.read();

        return new UserUpdateRequest(name, phone);
    }

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
        } else if (! answer.equals("n")) {
            System.out.println("잘못된 입력입니다.");
        }
        return null;
    }
}
