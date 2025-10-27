package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * {@code WelcomeServlet} クラスは、
 * アプリケーションのトップページ（ウェルカム画面）を表示するサーブレットです。<br>
 * 初回アクセス時やログアウト後に表示されるエントリーポイントとして機能します。
 *
 * <p>主な処理の流れ：</p>
 * <ol>
 *   <li><b>GET</b>：ウェルカム画面（index.jsp）を表示</li>
 *   <li><b>POST</b>：GET処理へ委譲（同様にウェルカム画面を表示）</li>
 * </ol>
 *
 * <p>使用JSP：</p>
 * <ul>
 *   <li>{@code /WEB-INF/jsp/index.jsp}</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/WelcomeServlet")
public class WelcomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * デフォルトコンストラクタ。特別な初期化処理は行いません。
     */
    public WelcomeServlet() {
        super();
    }

    /**
     * GETリクエストを処理します。<br>
     * アプリのトップページ（index.jsp）へフォワードします。
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレット処理中にエラーが発生した場合
     * @throws IOException 入出力エラーが発生した場合
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * POSTリクエストを処理します。<br>
     * このメソッドは GET 処理に委譲し、同様にトップページを表示します。
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレット処理中にエラーが発生した場合
     * @throws IOException 入出力エラーが発生した場合
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
