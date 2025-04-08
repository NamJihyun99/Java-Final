package user.dao;

import user.domain.User;

public interface UserDao {

    User save(User user);
    User findById(String email);
    void updatePassword(String userId, String newPassword);
    void flush(User user);
    void quit(String email);
}
