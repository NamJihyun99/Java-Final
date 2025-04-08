package user.dto;

import user.domain.User;

import java.time.LocalDate;

public class UserSaveRequest {

    private final String name;
    private final String email;
    private final String password;
    private final String phone;;

    public UserSaveRequest(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public static User toUser(UserSaveRequest saveRequest, LocalDate now) {
        return new User(saveRequest.name, saveRequest.password, saveRequest.email, saveRequest.phone, now);
    }
}
