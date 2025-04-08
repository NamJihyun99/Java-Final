import common.AppConfig;
import controller.MainController;

public class Main {

    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        MainController app = config.mainController();
        app.run();
    }
}
