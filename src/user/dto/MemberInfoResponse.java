package user.dto;

public class MemberInfoResponse {

    private final String email;
    private final String name;
    private final String phone;
    private final String role;

    private MemberInfoResponse(String email, String name, String phone, String role) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public static MemberInfoResponse of(String email, String name, String phone, String role) {
        return new MemberInfoResponse(email, name, phone, role);
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
