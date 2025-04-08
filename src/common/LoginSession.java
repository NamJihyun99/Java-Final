package common;

import user.domain.User;

public class LoginSession {

    private LoginSession() {}

    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }

    public static User getCurrentMember() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
