package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UsersDAO {

    // ユーザー登録
	public boolean insertUser(User user) throws SQLException {
	    String sql = "INSERT INTO users(user_id, password, name, birthday) VALUES (?, ?, ?, ?)";

	    try (Connection conn = DBManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, user.getUserId());  // ← emailをuserIdとしてセット
	        ps.setString(2, user.getPassword());
	        ps.setString(3, user.getName());
	        ps.setDate(4, java.sql.Date.valueOf(user.getBirthday()));

	        int rows = ps.executeUpdate();
	        return rows > 0;
	    }
	}
	
	//ログイン
	public User login(User user) {
	    String sql = "SELECT user_id, name, birthday FROM users WHERE user_id = ? AND password = ?";

	    try (Connection conn = DBManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, user.getUserId());
	        ps.setString(2, user.getPassword());

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                User loginUser = new User();
	                loginUser.setUserId(rs.getString("user_id"));
	                loginUser.setName(rs.getString("name"));

	                Date sqlDate = rs.getDate("birthday");
	                if (sqlDate != null) {
	                    loginUser.setBirthday(sqlDate.toLocalDate());
	                }

	                return loginUser;
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}


}
