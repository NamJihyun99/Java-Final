package user.dao;

import common.DBConnectionUtil;
import user.domain.Basket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BasketDao {

    public void save(Basket basket) {
        String sql = "INSERT INTO tb_basket (nb_basket, no_user) VALUES ('BK' || LPAD(seq_tb_basket.nextval, 7, '0') AS id_basket, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, basket.getUserId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
