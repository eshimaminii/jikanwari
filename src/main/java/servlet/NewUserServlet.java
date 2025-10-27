package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import service.UserRegistrationService;

/**
 * {@code NewUserServlet} クラスは、
 * 新規ユーザー登録処理を管理するサーブレットです。<br>
 * 入力フォームの表示、確認画面の遷移、登録処理の実行を担当します。
 *
 * <p>主な処理の流れ：</p>
 * <ol>
 *   <li><b>GET</b>：新規登録フォーム（newUserForm.jsp）を表示</li>
 *   <li><b>POST</b>：入力内容を取得し、確認または登録処理を実行</li>
 *   <li>登録成功時 → 完了画面へ遷移</li>
 *   <li>登録失敗時 → エラーメッセージ付きで入力フォームへ戻す</li>
 * </ol>
 *
 * <p>使用JSP：</p>
 * <ul>
 *   <li>{@code /WEB-INF/jsp/newUserForm.jsp}</li>
 *   <li>{@code /WEB-INF/jsp/newUserConfirm.jsp}</li>
 *   <li>{@code /WEB-INF/jsp/newUserComplete.jsp}</li>
 * </ul>
 *
 * <p>関連クラス：</p>
 * <ul>
 *   <li>{@link service.UserRegistrationService}</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/NewUserServlet")
public class NewUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GETリクエストを処理します。<br>
     * 新規ユーザー登録フォームを表示します。
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレットエラー発生時
     * @throws IOException      入出力エラー発生時
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/newUserForm.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * POSTリクエストを処理します。<br>
     * 入力内容の確認または登録処理を実行します。
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
        String action = request.getParameter("action");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String birthday = request.getParameter("birthday");

        System.out.println("action = " + action);

        // --- 登録処理 ---
        if ("submit".equals(action)) {
            boolean result = false;

            try {
                UserRegistrationService service = new UserRegistrationService();
                result = service.registerUser(email, password, name, birthday);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (result) {
                // 登録成功 → 完了画面へ
                request.getRequestDispatcher("/WEB-INF/jsp/newUserComplete.jsp").forward(request, response);
            } else {
                // 登録失敗 → エラーメッセージ付きでフォームへ戻す
                request.setAttribute("error", "ユーザー登録に失敗しました。再度お試しください。");
                request.getRequestDispatcher("/WEB-INF/jsp/newUserForm.jsp").forward(request, response);
            }
            return;
        }

        // --- 確認画面表示 ---
        request.setAttribute("email", email);
        request.setAttribute("password", password);
        request.setAttribute("name", name);
        request.setAttribute("birthday", birthday);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/newUserConfirm.jsp");
        dispatcher.forward(request, response);
    }
}
