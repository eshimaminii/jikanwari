package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Event;
import service.EventEditService;

/**
 * {@code EventDeleteServlet} クラスは、
 * イベント削除処理（削除確認・削除確定）を制御するサーブレットです。<br>
 * {@link service.EventEditService} を利用して、指定されたイベントを論理削除（delete_flag更新）します。
 *
 * <p>主な処理の流れ：</p>
 * <ol>
 *   <li><b>POST(confirm)</b>：削除確認画面を表示（削除対象のイベント情報を取得）</li>
 *   <li><b>POST(submit)</b>：削除処理を実行し、完了画面へ遷移</li>
 * </ol>
 *
 * <p>使用JSP：</p>
 * <ul>
 *   <li>{@code /WEB-INF/jsp/eventDeleteConfirm.jsp}</li>
 *   <li>{@code /WEB-INF/jsp/eventDeleteComplete.jsp}</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/EventDeleteServlet")
public class EventDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * POSTリクエストを処理します。<br>
     * actionパラメータにより、削除確認画面の表示または削除実行を切り替えます。
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
        String action = request.getParameter("action"); // 削除確認 or 実行を判定
        String eventIdStr = request.getParameter("event_id");

        // イベントIDが無効な場合はエラー画面へ
        if (eventIdStr == null || eventIdStr.isEmpty()) {
            response.sendRedirect("error.jsp");
            return;
        }

        int eventId = Integer.parseInt(eventIdStr);
        EventEditService service = new EventEditService();

        /* ---------- 削除確認画面の表示 ---------- */
        if (action == null || "confirm".equals(action)) {
            Event event = service.findEventById(eventId);
            request.setAttribute("event", event);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/eventDeleteConfirm.jsp");
            rd.forward(request, response);
        }

        /* ---------- 削除確定処理（delete_flag更新） ---------- */
        else if ("submit".equals(action)) {
            boolean result = service.deleteEvent(eventId);

            if (result) {
                // 削除成功 → 完了画面へ
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/eventDeleteComplete.jsp");
                rd.forward(request, response);
            } else {
                // 削除失敗 → 確認画面へ戻す
                request.setAttribute("errorMsg", "削除に失敗しました。");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/eventDeleteConfirm.jsp");
                rd.forward(request, response);
            }
        }
    }
}
