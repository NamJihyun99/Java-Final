package user.dto;

public class UserUpdateRequest {

    private final String name;
    private final String phone;

    public UserUpdateRequest(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
