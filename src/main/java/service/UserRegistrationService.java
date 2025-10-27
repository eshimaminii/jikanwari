package service;

import java.sql.SQLException;

import dao.UsersDAO;
import model.User;

/**
 * {@code UserRegistrationService} クラスは、
 * 新規ユーザー登録処理を担当するサービスクラスです。<br>
 * DAO層（{@link dao.UsersDAO}）を利用して、フォームから入力された情報を
 * {@link model.User} オブジェクトに変換し、データベースへ登録します。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>フォーム入力値からの {@link User} オブジェクト生成</li>
 *   <li>users テーブルへの新規登録処理</li>
 *   <li>文字列形式の生年月日を {@code LocalDate} へ変換</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * UserRegistrationService service = new UserRegistrationService();
 * boolean result = service.registerUser(
 *     "test@example.com", "pass123", "田中太郎", "1998-05-10"
 * );
 * 
 * if (result) {
 *     System.out.println("登録成功！");
 * } else {
 *     System.out.println("登録失敗…");
 * }
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class UserRegistrationService {

    /** ユーザーデータ操作用DAO */
    private static UsersDAO usersDAO;

    /** コンストラクタでDAOを初期化します。 */
    public UserRegistrationService() {
        usersDAO = new UsersDAO();
    }

    /**
     * ユーザーを新規登録します。<br>
     * 文字列で渡された生年月日を {@code LocalDate} に変換し、
     * {@link dao.UsersDAO#insertUser(User)} を呼び出して登録を行います。
     *
     * @param userId    ユーザーID（メールアドレスなど）
     * @param password  パスワード
     * @param name      氏名
     * @param birthdayStr 生年月日（"yyyy-MM-dd" 形式）
     * @return 登録成功時は {@code true}、失敗時は {@code false}
     */
    public boolean registerUser(String userId, String password, String name, String birthdayStr) {
        try {
            // birthdayStr → LocalDate に変換
            java.time.LocalDate birthday = java.time.LocalDate.parse(birthdayStr);

            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            user.setName(name);
            user.setBirthday(birthday);

            // DAOで登録実行
            return usersDAO.insertUser(user);

        } catch (SQLException e) {
            System.err.println("❌ SQLエラー: ユーザー登録中に問題が発生しました。");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("❌ 予期せぬエラーが発生しました。");
            e.printStackTrace();
            return false;
        }
    }
}
