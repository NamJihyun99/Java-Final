package user.dao;

import common.DBConnectionUtil;
import user.domain.BasketItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleBasketItemDao implements BasketItemDao {

    @Override
    public void save(BasketItem basketItem, int basketId) {
        String sql = """
                   INSERT INTO tb_basket_item (nb_basket_item, nb_basket, cn_basket_item_order, no_product, no_user, qt_basket_item_price, qt_basket_item, qt_basket_item_amount) 
                    VALUES (seq_tb_basket_item.nextval, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setInt(1, basketId);
            pstmt.setInt(2, basketItem.getOrder());
            pstmt.setString(3, basketItem.getProductId());
            pstmt.setString(4, basketItem.getUserId());
            pstmt.setInt(5, basketItem.getPrice());
            pstmt.setInt(6, basketItem.getQuantity());
            pstmt.setInt(7, basketItem.getTotal());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("장바구니 품목 생성 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public BasketItem findById(int basketItemId) {
        String sql = "SELECT * FROM tb_basket_item WHERE nb_basket_item = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setInt(1, basketItemId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new BasketItem(
                            rs.getInt("nb_basket_item"),
                            rs.getInt("cn_basket_item_order"),
                            rs.getString("no_product"),
                            rs.getString("no_user"),
                            rs.getInt("qt_basket_item_price"),
                            rs.getInt("qt_basket_item"),
                            rs.getInt("qt_basket_item_amount")
                    );
                } else {
                    throw new IllegalStateException("해당 품목을 찾을 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("장바구니 품목 조회 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public List<BasketItem> findAllByUserId(String userId) {
        String sql = "SELECT * FROM tb_basket_item WHERE no_user = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, userId);
            List<BasketItem> items = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    items.add(new BasketItem(
                            rs.getInt("nb_basket_item"),
                            rs.getInt("cn_basket_item_order"),
                            rs.getString("no_product"),
                            rs.getString("no_user"),
                            rs.getInt("qt_basket_item_price"),
                            rs.getInt("qt_basket_item"),
                            rs.getInt("qt_basket_item_amount")
                    ));
                }
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("장바구니 품목 리스트 조회 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public void flush(BasketItem basketItem) {
        String sql = """
                   UPDATE tb_basket_item SET qt_basket_item = ?, qt_basket_item_amount = ? WHERE nb_basket_item = ?
                """;
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setInt(1, basketItem.getQuantity());
            pstmt.setInt(2, basketItem.getQuantity() * basketItem.getPrice());
            pstmt.setInt(3, basketItem.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("장바구니 품목 수정 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public void clear(String userId) {
        String sql = "DELETE FROM tb_basket_item WHERE no_user = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("장바구니 비우기 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public void delete(int itemId) {
        String sql = "DELETE FROM tb_basket_item WHERE nb_basket_item = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setInt(1, itemId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("장바구니 품목 삭제 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public void deleteByProductId(String productId) {
        String sql = "DELETE FROM tb_basket_item WHERE no_product = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("장바구니 품목 삭제 중 오류 발생 : " + e.getMessage());
        }
    }
}
