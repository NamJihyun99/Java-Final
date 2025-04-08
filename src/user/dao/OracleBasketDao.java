package user.dao;

import common.DBConnectionUtil;
import user.domain.Basket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleBasketDao implements BasketDao {

    public void save(Basket basket) {
        String sql = "INSERT INTO tb_basket (nb_basket, no_user) VALUES (seq_tb_basket.nextval, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setQueryTimeout(10);
            pstmt.setString(1, basket.getUserId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("장바구니 생성 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public Basket findByUserId(String userId) {
        String sql = "SELECT * FROM tb_basket WHERE no_user = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Basket(rs.getInt("nb_basket"),
                            rs.getString("no_user"),
                            rs.getInt("qt_basket_amount"));
                } else {
                    throw new IllegalStateException("해당 품목을 찾을 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("장바구니 조회 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public void flush(Basket basket) {
        String sql = "UPDATE tb_basket SET qt_basket_amount = ? WHERE no_user = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setQueryTimeout(10);
            pstmt.setInt(1, basket.getTotal());
            pstmt.setString(2, basket.getUserId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("장바구니 업데이트 중 오류 발생 : " + e.getMessage());
        }
    }
}
