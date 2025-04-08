package app.dto;

import user.domain.User;

public class UserSaveRequest {

    private final String name;
    private final String password;
    private final String email;
    private final String phone;;

    public UserSaveRequest(String name, String password, String email, String phone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public static User toUser(UserSaveRequest saveRequest) {
        return new User(saveRequest.name, saveRequest.password, saveRequest.email, saveRequest.phone);
    }

    // todo: 유효성 검사
}
