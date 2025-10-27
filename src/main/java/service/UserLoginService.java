package service;

import dao.UsersDAO;
import model.User;

/**
 * {@code UserLoginService} クラスは、ユーザーのログイン認証処理を担当する
 * サービスクラスです。<br>
 * DAO層（{@link dao.UsersDAO}）を利用して、入力されたユーザー情報を照合し、
 * 認証結果を返します。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>ユーザーIDとパスワードによるログイン認証</li>
 *   <li>認証成功時のユーザー情報取得</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * UserLoginService service = new UserLoginService();
 * User loginUser = new User("test@example.com", "pass123");
 * User result = service.execute(loginUser);
 * 
 * if (result != null) {
 *     System.out.println("ログイン成功：" + result.getName());
 * } else {
 *     System.out.println("ログイン失敗");
 * }
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class UserLoginService {

    /**
     * ユーザーのログイン認証を実行します。<br>
     * 入力されたユーザー情報（ID・パスワード）を基に、
     * {@link dao.UsersDAO#login(User)} を呼び出して認証を行います。
     *
     * @param loginUser ログイン試行中のユーザー情報（ユーザーID・パスワードを設定済み）
     * @return 認証成功時はユーザー情報を保持する {@link User} オブジェクト、
     *         認証失敗時は {@code null}
     */
    public User execute(User loginUser) {
        UsersDAO dao = new UsersDAO();
        return dao.login(loginUser);
    }
}
