package servlet;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;
import service.UserLoginService;

/**
 * {@code LoginServlet} クラスは、
 * ユーザーのログイン処理を制御するサーブレットです。<br>
 * 入力されたメールアドレス・パスワードをもとに認証を行い、
 * 成功時はユーザー情報をセッションへ保存します。
 *
 * <p>主な処理の流れ：</p>
 * <ol>
 *   <li><b>GET</b>：ログインフォームを表示</li>
 *   <li><b>POST</b>：入力内容をもとに認証処理を実行</li>
 *   <li>認証成功 → ログイン完了画面へ遷移</li>
 *   <li>認証失敗 → エラーメッセージ付きでフォームへ戻す</li>
 * </ol>
 *
 * <p>使用JSP：</p>
 * <ul>
 *   <li>{@code /WEB-INF/jsp/login.jsp}</li>
 *   <li>{@code /WEB-INF/jsp/loginComplete.jsp}</li>
 * </ul>
 *
 * <p>関連クラス：</p>
 * <ul>
 *   <li>{@link service.UserLoginService}</li>
 *   <li>{@link model.User}</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * GETリクエストを処理します。<br>
	 * ログインフォーム（{@code login.jsp}）を表示します。
	 *
	 * @param request  クライアントからのHTTPリクエスト
	 * @param response サーバーからのHTTPレスポンス
	 * @throws ServletException サーブレットエラー発生時
	 * @throws IOException      入出力エラー発生時
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * POSTリクエストを処理します。<br>
	 * 入力されたユーザー情報を基に認証を行い、
	 * 成功時にはユーザー情報をセッションに保存します。
	 *
	 * @param request  クライアントからのHTTPリクエスト
	 * @param response サーバーからのHTTPレスポンス
	 * @throws ServletException サーブレットエラー発生時
	 * @throws IOException      入出力エラー発生時
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 入力値取得
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// 入力情報をUserオブジェクトに格納
		User loginUser = new User(email, password);
		UserLoginService userLoginService = new UserLoginService();

		// DBからユーザー情報を取得（認証）
		User userFromDB = userLoginService.execute(loginUser);

		if (userFromDB != null) {

			// 今日がユーザーの誕生日かどうかを判定
			LocalDate today = LocalDate.now();
			LocalDate birthday = userFromDB.getBirthday();

			// nullチェックを行い、誕生日と一致するか確認
			boolean isBirthday = false;
			if (birthday != null) {
				isBirthday = (today.getMonth() == birthday.getMonth()
						&& today.getDayOfMonth() == birthday.getDayOfMonth());
				// 認証成功 → セッションにユーザー情報を保存
				request.getSession().setAttribute("loginUser", userFromDB);
				// 認証成功 → セッションに誕生日フラグを保存
				request.getSession().setAttribute("isBirthday", isBirthday);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/loginComplete.jsp");
				dispatcher.forward(request, response);
			} else {
				// 認証失敗 → エラーメッセージを表示して戻す
				request.setAttribute("errorMsg", "メールアドレスまたはパスワードが違います");
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}
		}
	}
}
