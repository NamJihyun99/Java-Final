package common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionUtil {

    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    // 인스턴스화 방지
    private DBConnectionUtil() {
    }

    static {
        String resource = "application.properties";
        Properties properties = new Properties();

        try (InputStream inputStream = DBConnectionUtil.class.getClassLoader().getResourceAsStream(resource)) {
            properties.load(inputStream);
            DB_URL = properties.getProperty("database.url");
            DB_USER = properties.getProperty("database.user");
            DB_PASSWORD = properties.getProperty("database.password");
        } catch (IOException e) {
            throw new RuntimeException("DB 설정 파일 로딩 실패" + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
             return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL 드라이버 로딩 실패 : " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다 : " + e.getMessage());
        }
    }

}
