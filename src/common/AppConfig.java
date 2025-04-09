package common;

import controller.*;
import product.dao.OracleProductDao;
import product.dao.ProductDao;
import product.service.ProductService;
import user.dao.*;
import user.service.*;
import view.*;

public class AppConfig {

    public MainController mainController() {
        return new MainController(memberController(), mainView(), userService(), productService());
    }

    public MainView mainView() {
        return new MainView();
    }

    public MemberView memberView() {
        return new MemberView();
    }

    public UserService userService() {
        return new UserService(userDao(), basketDao());
    }

    public UserDao userDao() {
        return new OracleUserDao();
    }

    public BasketDao basketDao() {
        return new OracleBasketDao();
    }

    public ProductService productService() {
        return new ProductService(productDao(), basketItemDao());
    }

    public ProductDao productDao() {
        return new OracleProductDao();
    }

    public BasketService basketService() {
        return new BasketService(productDao(), basketItemDao(), basketDao());
    }

    public BasketItemDao basketItemDao() {
        return new OracleBasketItemDao();
    }

    public BasketHandler basketHandler() {
        return new BasketHandler(memberView(), productService(), basketService());
    }

    public UserHandler userHandler() {
        return new UserHandler(memberView(), userService());
    }

    public AdminHandler adminHandler() {
        return new AdminHandler(memberView(), productService());
    }

    public MemberController memberController() {
        return new MemberController(
                memberView(),
                basketHandler(),
                userHandler(),
                adminHandler()
        );
    }
}

