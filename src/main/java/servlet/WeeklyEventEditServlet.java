package servlet;

import java.io.IOException;
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
import model.Weekday;
import service.WeeklyEventService;

/**
 * {@code WeeklyEventEditServlet} クラスは、
 * 繰り返し予定（週単位）の曜日設定を編集・更新するサーブレットです。<br>
 * イベントに紐づく曜日の編集フォーム表示、確認画面、更新処理を担当します。
 *
 * <p>主な処理の流れ：</p>
 * <ol>
 *   <li><b>edit</b>：指定イベントの曜日設定フォームを表示</li>
 *   <li><b>confirm</b>：選択内容を確認画面に表示</li>
 *   <li><b>submit</b>：選択内容をDBへ反映（曜日・繰り返しフラグの更新）</li>
 * </ol>
 *
 * <p>使用JSP：</p>
 * <ul>
 *   <li>{@code /WEB-INF/jsp/weeklyEventForm.jsp}</li>
 *   <li>{@code /WEB-INF/jsp/weeklyEventConfirm.jsp}</li>
 *   <li>{@code /WEB-INF/jsp/weeklyEventComplete.jsp}</li>
 * </ul>
 *
 * <p>関連クラス：</p>
 * <ul>
 *   <li>{@link service.WeeklyEventService}</li>
 *   <li>{@link model.Event}</li>
 *   <li>{@link model.Weekday}</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/WeeklyEventEditServlet")
public class WeeklyEventEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * POSTリクエストを処理します。<br>
     * {@code action} パラメータの値によって、以下の3つの動作を行います：
     * <ul>
     *   <li>{@code "edit"}：曜日編集フォームの表示</li>
     *   <li>{@code "confirm"}：確認画面への遷移</li>
     *   <li>{@code "submit"}：DBへの更新（曜日・繰り返しフラグの反映）</li>
     * </ul>
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレット処理中にエラーが発生した場合
     * @throws IOException 入出力エラーが発生した場合
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // --- ログインチェック ---
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        WeeklyEventService service = new WeeklyEventService();

        // ===== 編集フォーム表示 =====
        if ("edit".equals(action)) {
            int eventId = Integer.parseInt(request.getParameter("event_id"));
            Event event = service.findEventById(eventId);
            List<Weekday> weekdayList = service.getAllWeekdays();
            List<Integer> selectedWeekdays = service.findWeekdaysByEventId(eventId);

            // JSPに渡す
            request.setAttribute("event", event);
            request.setAttribute("weekdayList", weekdayList);
            request.setAttribute("selectedWeekdays", selectedWeekdays);

            RequestDispatcher rd =
                    request.getRequestDispatcher("/WEB-INF/jsp/weeklyEventForm.jsp");
            rd.forward(request, response);
            return;
        }

        // ===== 確認画面 =====
        if ("confirm".equals(action)) {
            int eventId = Integer.parseInt(request.getParameter("event_id"));
            String[] selectedIds = request.getParameterValues("weekday_ids");

            session.setAttribute("event_id", eventId);
            session.setAttribute("selectedWeekdayIds", selectedIds);

            List<Weekday> weekdayList = service.getWeekdaysByIds(selectedIds);
            request.setAttribute("weekdayList", weekdayList);

            RequestDispatcher rd =
                    request.getRequestDispatcher("/WEB-INF/jsp/weeklyEventConfirm.jsp");
            rd.forward(request, response);
            return;
        }

        // ===== 更新（完了）処理 =====
        if ("submit".equals(action)) {
            int eventId = (int) session.getAttribute("event_id");
            String[] selectedIds = (String[]) session.getAttribute("selectedWeekdayIds");

            // 曜日が選択されているかをチェック
            boolean hasWeekdays = selectedIds != null && selectedIds.length > 0;

            // 繰り返しフラグも同時に更新
            boolean result = service.updateWeekdaysAndRepeatFlag(eventId, selectedIds, hasWeekdays);

            // セッション片付け
            session.removeAttribute("event_id");
            session.removeAttribute("selectedWeekdayIds");

            if (result) {
                RequestDispatcher rd =
                        request.getRequestDispatcher("/WEB-INF/jsp/weeklyEventComplete.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("errorMsg", "更新に失敗しました。");
                RequestDispatcher rd =
                        request.getRequestDispatcher("/WEB-INF/jsp/weeklyEventForm.jsp");
                rd.forward(request, response);
            }
        }
    }
}
