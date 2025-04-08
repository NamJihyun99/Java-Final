package user.dao;

import common.DBConnectionUtil;
import user.domain.Role;
import user.domain.Status;
import user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public User save(User user) {
        String sql = "INSERT INTO tb_user (id_user, nm_user, nm_paswd, no_mobile, nm_email, st_status, cd_user_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getStatus().value());
            pstmt.setString(7, user.getRole().value());

            System.out.println("INSERT 실행 직전");
            pstmt.executeUpdate();
            System.out.println("실행완료");

        } catch (SQLException e) {
            throw new RuntimeException("유저 저장 중 오류 발생 : " + e.getMessage());
        }
        System.out.println("user.getEmail(): " + user.getEmail());
        User result = findById(user.getEmail());
        System.out.println("조회된 사용자: " + result);
        return result;
    }

    public User findById(String email) {
        String sql = "SELECT * FROM tb_user WHERE id_user = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
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
            throw new RuntimeException("유저 조회 중 오류 발생", e);
        }
    }

    public void delete(String email) {
        String sql = "DELETE FROM tb_user WHERE id_user = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("유저 조회 중 오류 발생", e);
        }
    }
}
