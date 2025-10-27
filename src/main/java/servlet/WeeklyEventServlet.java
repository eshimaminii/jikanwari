package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Event;
import model.User;
import service.WeeklyDisplayService;

/**
 * {@code WeeklyEventServlet} クラスは、
 * 繰り返し予定（週単位のスケジュール）を曜日ごとに一覧表示するサーブレットです。<br>
 * ログインユーザーに紐づく繰り返し設定付きイベントを取得し、
 * 曜日別にグループ化してJSPに渡します。
 *
 * <p>主な処理の流れ：</p>
 * <ol>
 *   <li>セッションからログインユーザー情報を取得</li>
 *   <li>未ログインの場合はログイン画面へリダイレクト</li>
 *   <li>{@link service.WeeklyDisplayService} を利用して繰り返しイベントを取得</li>
 *   <li>曜日ごとにグループ化されたイベントをリクエスト属性に格納</li>
 *   <li>結果を {@code weeklyEvent.jsp} へフォワード</li>
 * </ol>
 *
 * <p>使用JSP：</p>
 * <ul>
 *   <li>{@code /WEB-INF/jsp/weeklyEvent.jsp}</li>
 * </ul>
 *
 * <p>関連クラス：</p>
 * <ul>
 *   <li>{@link service.WeeklyDisplayService}</li>
 *   <li>{@link model.Event}</li>
 *   <li>{@link model.User}</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/WeeklyEventServlet")
public class WeeklyEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GETリクエストを処理します。<br>
     * ログイン中のユーザーを確認し、曜日ごとにグループ化された
     * 繰り返しイベント一覧を取得して表示します。
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレット処理中にエラーが発生した場合
     * @throws IOException 入出力エラーが発生した場合
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // セッションからログイン情報を取得
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // --- ログインチェック ---
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        try {
            // 曜日ごとにグループ化された繰り返し予定を取得
            WeeklyDisplayService displayService = new WeeklyDisplayService();
            Map<String, List<Event>> groupedEvents =
                    displayService.getGroupedRepeatedEvents(loginUser.getUserId());

            // 🌈 デバッグ出力（開発時用）
            for (String key : groupedEvents.keySet()) {
                System.out.println("🌈 weekday.key = " + key);
            }

            // JSPへ渡す
            request.setAttribute("groupedEvents", groupedEvents);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "予定一覧の取得に失敗しました。");
        }

        // JSP表示
        RequestDispatcher rd =
                request.getRequestDispatcher("/WEB-INF/jsp/weeklyEvent.jsp");
        rd.forward(request, response);
    }
}
