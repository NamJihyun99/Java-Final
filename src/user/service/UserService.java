package user.service;

import user.dao.BasketDao;
import user.dao.UserDao;
import user.domain.Basket;
import user.dto.LoginRequest;
import user.dto.UserSaveRequest;
import user.domain.User;
import user.dto.UserUpdateRequest;

import java.time.LocalDate;

public class UserService {

    private final UserDao userDao;
    private final BasketDao basketDao;

    public UserService(UserDao userDao, BasketDao basketDao) {
        this.userDao = userDao;
        this.basketDao = basketDao;
    }

    public void save(UserSaveRequest saveRequest, LocalDate now) {
        User newUser = UserSaveRequest.toUser(saveRequest, now);
        User user = userDao.save(newUser);
        basketDao.save(new Basket(user.getUserId()));
    }

    public User checkSignIn(LoginRequest loginRequest) {
        User user = findByEmail(loginRequest.getEmail());
        if (!user.canSignIn(loginRequest.getPassword())) {
            throw new IllegalArgumentException("올바른 회원 정보가 아닙니다.");
        }
        return user;
    }

    public void quit(User user, LoginRequest loginRequest) {
        checkSignIn(loginRequest);
        userDao.quit(user.getEmail());
    }

    public void updateInfo(User user, UserUpdateRequest updateRequest) {
        user.updateName(updateRequest.getName());
        user.updatePhone(updateRequest.getPhone());
        userDao.flush(user);
    }

    public void updatePassword(User user, String newPassword) {
        user.updatePassword(newPassword);
        userDao.updatePassword(user.getUserId(), newPassword);
    }

    private User findByEmail(String email) {
        User user = userDao.findById(email);
        if (user == null) {
            throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
        }
        return user;
    }
}
