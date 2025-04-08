package user.domain;

import java.time.LocalDate;

public class User {

    private String userId;
    private String name;
    private String password;
    private String phone;
    private final String email;
    private Status status;
    private Role role;
    private final LocalDate createdDate;

    public User(String name, String password, String email, String phone, LocalDate createdDate) {
        this.userId = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.status = Status.NORMAL;
        this.role = Role.MEMBER;
        this.createdDate = createdDate;
    }

    public User(String userId, String name, String password, String phone, String email, Status status, Role role, LocalDate createdDate) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.role = role;
        this.createdDate = createdDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Status getStatus() {
        return status;
    }

    public Role getRole() {
        return role;
    }

    public boolean canSignIn(String passwordInput) {
        return this.password.equals(passwordInput);
    }

    public boolean isAdmin() {
        return this.role.equals(Role.ADMIN);
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public void updatePhone(String newPhone) {
        this.phone = newPhone;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
