package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * {@code LogoutServlet} クラスは、
 * ログイン中のユーザーセッションを破棄してログアウト処理を行うサーブレットです。<br>
 * セッションを無効化し、ログアウト完了画面へ遷移します。
 *
 * <p>主な処理の流れ：</p>
 * <ol>
 *   <li><b>GET</b>：現在のセッションを破棄し、ログアウト完了画面を表示</li>
 *   <li><b>POST</b>：同様にログアウト処理を実行（GETへ委譲）</li>
 * </ol>
 *
 * <p>使用JSP：</p>
 * <ul>
 *   <li>{@code /WEB-INF/jsp/logout.jsp}</li>
 * </ul>
 *
 * <p>関連クラス：</p>
 * <ul>
 *   <li>{@link servlet.LoginServlet}</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GETリクエストを処理します。<br>
     * セッションを無効化し、ログアウト完了画面へフォワードします。
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレットエラー発生時
     * @throws IOException      入出力エラー発生時
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションを無効化（ログイン情報の削除）
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // ログアウト完了画面へフォワード
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/logout.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * POSTリクエストを処理します。<br>
     * GET処理に委譲して同様のログアウト処理を実行します。
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレットエラー発生時
     * @throws IOException      入出力エラー発生時
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
