package user.dto;

import user.domain.User;

public class PasswordUpdateRequest {

    private final String oldPassword;
    private final String newPassword;
    private final String confirmPassword;

    public PasswordUpdateRequest(String oldPassword, String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public void validate(User user) {
        if (!user.canSignIn(oldPassword)) {
            throw new IllegalArgumentException("기존 비밀번호가 잘못되었습니다.");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("새 비밀번호를 다시 정확하게 입력해주세요.");
        }
    }

    public String getNewPassword() {
        return newPassword;
    }
}
