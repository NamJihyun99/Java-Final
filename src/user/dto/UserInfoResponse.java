package user.dto;

public class UserInfoResponse {

    private final String email;
    private final String name;
    private final String phone;
    private final String role;

    private UserInfoResponse(String email, String name, String phone, String role) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public static UserInfoResponse of(String email, String name, String phone, String role) {
        return new UserInfoResponse(email, name, phone, role);
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }
}
