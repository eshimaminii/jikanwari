package model;

import java.time.LocalDate;

/**
 * {@code User} クラスは、users テーブルの1レコードを表すモデルクラスです。<br>
 * ユーザーの基本情報（ID・パスワード・氏名・生年月日）を保持します。
 *
 * <p>主な用途：</p>
 * <ul>
 *   <li>{@link dao.UsersDAO} によるユーザー登録・ログイン処理</li>
 *   <li>アプリケーション内でのユーザー情報の保持やセッション管理</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * User u = new User("test@example.com", "pass123", "田中太郎", LocalDate.of(1998, 5, 10));
 * System.out.println(u.getName() + " さんの誕生日: " + u.getBirthday());
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class User {

    /** ユーザーID（メールアドレスなど） */
    private String userId;

    /** パスワード（ハッシュ化前提） */
    private String password;

    /** ユーザー名 */
    private String name;

    /** 生年月日 */
    private LocalDate birthday;

    /** デフォルトコンストラクタ */
    public User() {}

    /**
     * 全フィールドを初期化するコンストラクタ。
     *
     * @param userId   ユーザーID
     * @param password パスワード
     * @param name     ユーザー名
     * @param birthday 生年月日
     */
    public User(String userId, String password, String name, LocalDate birthday) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
    }

    /**
     * ログイン用など、ユーザーIDとパスワードのみを指定する簡易コンストラクタ。
     *
     * @param userId   ユーザーID
     * @param password パスワード
     */
    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    /** ユーザーIDを取得します。 */
    public String getUserId() { return userId; }

    /** ユーザーIDを設定します。 */
    public void setUserId(String userId) { this.userId = userId; }

    /** パスワードを取得します。 */
    public String getPassword() { return password; }

    /** パスワードを設定します。 */
    public void setPassword(String password) { this.password = password; }

    /** ユーザー名を取得します。 */
    public String getName() { return name; }

    /** ユーザー名を設定します。 */
    public void setName(String name) { this.name = name; }

    /** 生年月日を取得します。 */
    public LocalDate getBirthday() { return birthday; }

    /** 生年月日を設定します。 */
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
}
