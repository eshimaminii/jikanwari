package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

/**
 * {@code UsersDAO} クラスは、users テーブルに対するデータアクセス操作を行うDAOクラスです。<br>
 * ユーザーの新規登録およびログイン認証を担当します。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>ユーザー登録（{@link #insertUser(User)}）</li>
 *   <li>ログイン認証（{@link #login(User)}）</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * UsersDAO dao = new UsersDAO();
 * User user = new User("test@example.com", "password123");
 * User loginUser = dao.login(user);
 * if (loginUser != null) {
 *     System.out.println("ログイン成功：" + loginUser.getName());
 * }
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class UsersDAO {

    /**
     * 新しいユーザーを登録します。<br>
     * 登録成功時は {@code true} を返します。
     *
     * @param user 登録するユーザー情報
     * @return 登録成功時は {@code true}、失敗時は {@code false}
     * @throws SQLException データベースアクセスエラーが発生した場合
     */
    public boolean insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users(user_id, password, name, birthday) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserId());  // メールアドレスを user_id として登録
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setDate(4, java.sql.Date.valueOf(user.getBirthday()));

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    /**
     * ユーザーID（メールアドレス）とパスワードを基にログイン認証を行います。<br>
     * 一致するユーザーが存在する場合、そのユーザー情報を返します。
     *
     * @param user ログイン試行中のユーザー情報（IDとパスワードを設定）
     * @return 認証成功時は {@link User} オブジェクト、失敗時は {@code null}
     */
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
