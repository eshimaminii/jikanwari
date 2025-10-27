package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Event;
import model.User;
import service.ScheduleDisplayService;

/**
 * {@code MainServlet} クラスは、
 * ログイン後のメイン画面（当日の予定一覧）を表示するサーブレットです。<br>
 * ログインユーザーのセッションを確認し、当日の日付に該当する予定を取得・表示します。
 *
 * <p>主な処理の流れ：</p>
 * <ol>
 *   <li>セッションからログインユーザー情報を取得</li>
 *   <li>未ログインの場合はログイン画面へリダイレクト</li>
 *   <li>当日の日付を取得し、該当ユーザーの予定を取得</li>
 *   <li>JSP（main.jsp）に予定一覧を渡して表示</li>
 * </ol>
 *
 * <p>使用JSP：</p>
 * <ul>
 *   <li>{@code /WEB-INF/jsp/main.jsp}</li>
 * </ul>
 *
 * <p>関連クラス：</p>
 * <ul>
 *   <li>{@link service.ScheduleDisplayService}</li>
 *   <li>{@link model.Event}</li>
 *   <li>{@link model.User}</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GETリクエストを処理します。<br>
     * ログイン状態を確認し、当日の予定を取得してメイン画面を表示します。
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレット処理中にエラーが発生した場合
     * @throws IOException 入出力エラーが発生した場合
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションからログイン中のユーザーを取得
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // ログインしていない場合はログイン画面へリダイレクト
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        // 今日の日付を取得
        LocalDate today = LocalDate.now();

        // 当日の予定を取得
        ScheduleDisplayService service = new ScheduleDisplayService();
        List<Event> todayEvents = service.getTodayEvents(loginUser.getUserId(), today);

     // セッションから誕生日フラグを取得し、JSPに渡す
        Boolean isBirthday = (Boolean) request.getSession().getAttribute("isBirthday");
        request.setAttribute("isBirthday", isBirthday);
        
        // JSPにデータを渡す
        request.setAttribute("todayEvents", todayEvents);

        // メイン画面へフォワード
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
        dispatcher.forward(request, response);
    }
}
