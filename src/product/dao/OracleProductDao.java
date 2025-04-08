package product.dao;

import common.DBConnectionUtil;
import oracle.jdbc.driver.OracleConnection;
import product.domain.Product;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleProductDao implements ProductDao {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");

    @Override
    public void save(Product product) {
        String sql = """
                    INSERT INTO tb_product (no_product, nm_product, nm_detail_explain, dt_start_date, dt_end_date, qt_sale_price, qt_stock)
                    VALUES ('PT' || LPAD(seq_tb_product.nextval, 7, '0'), ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDetail());
            pstmt.setString(3, product.getStartDate().format(formatter));
            pstmt.setString(4, product.getEndDate().format(formatter));
            pstmt.setInt(5, product.getSalePrice());
            pstmt.setInt(6, product.getQuantity());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("상품 등록 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM tb_product ORDER BY qt_sale_price DESC";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            List<Product> products = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getString("no_product"),
                            rs.getString("nm_product"),
                            rs.getString("nm_detail_explain"),
                            LocalDate.parse(rs.getString("dt_start_date"), formatter),
                            LocalDate.parse(rs.getString("dt_end_date"), formatter),
                            rs.getInt("qt_sale_price"),
                            rs.getInt("qt_stock"),
                            rs.getTimestamp("da_first_date").toLocalDateTime().toLocalDate()
                    ));
                }
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("상품 목록 조회 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public List<Product> findEnables() {
        String sql = """
                SELECT * FROM tb_product 
                         WHERE qt_stock>0 
                            AND TO_DATE(dt_start_date, 'YY/MM/DD') <= SYSDATE
                            AND TO_DATE(dt_end_date, 'YY/MM/DD') >= SYSDATE
                         ORDER BY qt_sale_price DESC
                """;
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            List<Product> products = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getString("no_product"),
                            rs.getString("nm_product"),
                            rs.getString("nm_detail_explain"),
                            LocalDate.parse(rs.getString("dt_start_date"), formatter),
                            LocalDate.parse(rs.getString("dt_end_date"), formatter),
                            rs.getInt("qt_sale_price"),
                            rs.getInt("qt_stock"),
                            rs.getTimestamp("da_first_date").toLocalDateTime().toLocalDate()
                    ));
                }
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("상품 목록 조회 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public List<Product> findByKeyword(String keyword) {
        String sql = """
                SELECT * FROM tb_product 
                         WHERE qt_stock>0 
                           AND TO_DATE(dt_start_date, 'YY/MM/DD') <= SYSDATE
                            AND TO_DATE(dt_end_date, 'YY/MM/DD') >= SYSDATE
                           AND nm_product LIKE '%' || ? || '%' 
                         ORDER BY qt_sale_price DESC
                """;
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, keyword);
            List<Product> products = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getString("no_product"),
                            rs.getString("nm_product"),
                            rs.getString("nm_detail_explain"),
                            LocalDate.parse(rs.getString("dt_start_date"), formatter),
                            LocalDate.parse(rs.getString("dt_end_date"), formatter),
                            rs.getInt("qt_sale_price"),
                            rs.getInt("qt_stock"),
                            rs.getTimestamp("da_first_date").toLocalDateTime().toLocalDate()
                    ));
                }
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("상품 목록 조회 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public Product findById(String id) {
        String sql = "SELECT * FROM tb_product WHERE no_product = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getString("no_product"),
                            rs.getString("nm_product"),
                            rs.getString("nm_detail_explain"),
                            LocalDate.parse(rs.getString("dt_start_date"), formatter),
                            LocalDate.parse(rs.getString("dt_end_date"), formatter),
                            rs.getInt("qt_sale_price"),
                            rs.getInt("qt_stock"),
                            rs.getTimestamp("da_first_date").toLocalDateTime().toLocalDate()
                    );
                } else {
                    throw new IllegalStateException("해당 ID의 상품을 찾을 수 없습니다");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("상품 조회 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public Map<String, Product> findAllByIds(List<String> ids) {
        String sql = """
                    SELECT * FROM tb_product
                    WHERE no_product IN (
                        SELECT * FROM TABLE(CAST(? AS SYS.ODCIVARCHAR2LIST))
                    )
                """;

        try (Connection conn = DBConnectionUtil.getConnection();
             OracleConnection oraConn = (OracleConnection) conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Array array = oraConn.createOracleArray("SYS.ODCIVARCHAR2LIST", ids.toArray(new String[0]));
            pstmt.setArray(1, array);

            ResultSet rs = pstmt.executeQuery();
            Map<String, Product> result = new HashMap<>();

            while (rs.next()) {
                String productId = rs.getString("no_product");
                result.put(productId, new Product(
                        productId,
                        rs.getString("nm_product"),
                        rs.getString("nm_detail_explain"),
                        LocalDate.parse(rs.getString("dt_start_date"), formatter),
                        LocalDate.parse(rs.getString("dt_end_date"), formatter),
                        rs.getInt("qt_sale_price"),
                        rs.getInt("qt_stock"),
                        rs.getTimestamp("da_first_date").toLocalDateTime().toLocalDate()
                ));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("ProductId 별 품목 수집 중 오류 발생" + e.getMessage());
        }
    }

    @Override
    public void updateInfo(Product product) {
        String sql = " UPDATE tb_product SET nm_product = ?, nm_detail_explain = ?, qt_sale_price = ? WHERE no_product = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDetail());
            pstmt.setInt(3, product.getSalePrice());
            pstmt.setString(4, product.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("회원 정보 수정 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public void updateQuantity(Product product) {
        String sql = "UPDATE tb_product SET qt_stock = ? WHERE no_product = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setInt(1, product.getQuantity());
            pstmt.setString(2, product.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("회원 정보 수정 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM tb_product WHERE no_product = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("회원 정보 수정 중 오류 발생 : " + e.getMessage());
        }
    }
}