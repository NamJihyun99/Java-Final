package view;

import common.ReaderUtil;
import user.dto.LoginRequest;
import user.dto.UserSaveRequest;

import java.util.regex.Pattern;

public class MainView {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{11}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");


    public int selectMenu() {
        System.out.println("===============================================================");
        System.out.println("[환영합니다!] 1. 회원 가입 | 2. 로그인 | 3. 상품 목록 | 4. 종료");
        System.out.println("===============================================================");
        return ReaderUtil.getValidOption(4);
    }

    public UserSaveRequest getSaveRequest() {
        System.out.println("회원가입을 위한 정보를 입력하세요.");
        System.out.print("이름을 입력하세요 >> ");
        String name = validateStringEmpty(ReaderUtil.read());
        System.out.print("이메일을 입력하세요 >> ");
        String email = validateEmail(ReaderUtil.read().trim());
        System.out.print("비밀번호를 입력하세요 >> ");
        String password = validateStringEmpty(ReaderUtil.read().trim());
        System.out.print("휴대폰 번호를 입력하세요 >> ");
        String phone = validatePhoneNumber(ReaderUtil.read());

        return new UserSaveRequest(name, email, password, phone);
    }

    public LoginRequest getLoginRequest() {
        System.out.println("로그인을 위한 정보를 입력하세요.");
        System.out.print("이메일을 입력하세요 >> ");
        String email = validateEmail(ReaderUtil.read().trim());
        if (email.isBlank()) {
            return null;
        }
        System.out.print("비밀번호를 입력하세요 >> ");
        String password = validateStringEmpty(ReaderUtil.read().trim());

        return new LoginRequest(email, password);
    }

    public void printSignUpSuccessMsg() {
        System.out.println("가입되었습니다. 다시 로그인해주세요.");
    }

    public void printLoginSuccessMsg() {
        System.out.println("성공적으로 로그인하였습니다.\n");
    }

    private String validateStringEmpty(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("빈 문자열 입니다");
        }
        return input;
    }

    private String validateEmail(String emailInput) {
        validateStringEmpty(emailInput);
        if (!EMAIL_PATTERN.matcher(emailInput).matches()) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
        return emailInput;
    }

    private String validatePhoneNumber(String input) {
        validateStringEmpty(input);
        if (!PHONE_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("올바른 번호 형식이 아닙니다. 숫자 11자리를 입력해주세요.");
        }
        return input;
    }
}
