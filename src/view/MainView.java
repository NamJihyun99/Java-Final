package app;

import app.dto.UserSaveRequest;

public class MainView {

    public void printMain() {
        System.out.println("[환영합니다!] 1. 회원 가입 | 2. 로그인 | 3. 상품 목록");
    }

    public UserSaveRequest getSaveRequest() {
        System.out.println("회원가입을 위한 정보를 입력하세요.");
        System.out.print("이름을 입력하세요 >> ");
        String name = TextReader.read();
        System.out.print("이메일을 입력하세요 >> ");
        String email = TextReader.read();
        System.out.print("비밀번호를 입력하세요 >> ");
        String password = TextReader.read();
        System.out.print("휴대폰 번호를 입력하세요 >> ");
        String phone = TextReader.read();
        return new UserSaveRequest(name, password, email, phone);
    }
}
