package user.dao;

import common.DBConnectionUtil;
import user.domain.Role;
import user.domain.Status;
import user.domain.User;

import java.sql.*;

public class OracleUserDao implements UserDao {

    public User save(User user) {
        String sql = """
                    INSERT INTO tb_user (id_user, nm_user, nm_paswd, no_mobile, nm_email, st_status, cd_user_type) 
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getStatus().value());
            pstmt.setString(7, user.getRole().value());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("유저 저장 중 오류 발생 : " + e.getMessage());
        }
        return findById(user.getEmail());
    }

    public User findById(String email) {
        String sql = "SELECT * FROM tb_user WHERE id_user = ? and st_status = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, email);
            pstmt.setString(2, "ST01");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("id_user"),
                            rs.getString("nm_user"),
                            rs.getString("nm_paswd"),
                            rs.getString("no_mobile"),
                            rs.getString("nm_email"),
                            Status.of(rs.getString("st_status")),
                            Role.of(rs.getString("cd_user_type")),
                            rs.getTimestamp("da_first_date").toLocalDateTime().toLocalDate()
                    );
                } else {
                    throw new IllegalStateException("해당 사용자를 찾을 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("유저 조회 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public void updatePassword(String userId, String newPassword) {
        String sql = """
                   UPDATE tb_user SET nm_paswd = ? WHERE id_user = ?
                """;
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("회원 비밀번호 수정 중 오류 발생 : " + e.getMessage());
        }
    }

    @Override
    public void flush(User user) {
        String sql = """
                   UPDATE tb_user SET nm_user = ?, no_mobile = ? WHERE id_user = ?
                """;
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPhone());
            pstmt.setString(3, user.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("회원 정보 수정 중 오류 발생 : " + e.getMessage());
        }
    }

    public void quit(String email) {
        String sql = "UPDATE tb_user SET st_status = ? WHERE id_user = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setQueryTimeout(10);
            pstmt.setString(1, "ST02");
            pstmt.setString(2, email);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("유저 조회 중 오류 발생", e);
        }
    }
}
