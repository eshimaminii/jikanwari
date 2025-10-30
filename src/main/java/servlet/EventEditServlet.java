package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ColorDAO;
import dao.WeekdayDAO;
import model.Color;
import model.Event;
import model.Weekday;
import service.EventEditService;

/**
 * {@code EventEditServlet} クラスは、
 * イベントの編集フォーム表示・確認・更新処理を制御するサーブレットです。<br>
 * DAO層（{@link dao.EventsDAO}, {@link dao.ColorDAO}, {@link dao.WeekdayDAO}）および
 * サービス層（{@link service.EventEditService}）と連携して、
 * 入力内容の検証・再表示・更新登録を実行します。
 *
 * <p>主な処理の流れ：</p>
 * <ol>
 *   <li><b>GET</b>：既存のイベント情報を取得し、編集フォームを表示</li>
 *   <li><b>POST(confirm)</b>：入力内容を確認画面に渡す</li>
 *   <li><b>POST(submit)</b>：編集内容をデータベースに更新</li>
 * </ol>
 *
 * <p>使用JSP：</p>
 * <ul>
 *   <li>{@code /WEB-INF/jsp/eventEditForm.jsp}</li>
 *   <li>{@code /WEB-INF/jsp/eventEditConfirm.jsp}</li>
 *   <li>{@code /WEB-INF/jsp/eventEditComplete.jsp}</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/EventEditServlet")
public class EventEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GETリクエスト処理。<br>
     * 指定されたイベントIDをもとに既存データを取得し、編集フォームを表示します。
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
        String eventIdStr = request.getParameter("event_id");

        // イベントIDが指定されていない場合はメイン画面へ戻る
        if (eventIdStr == null || eventIdStr.isEmpty()) {
            response.sendRedirect("mainMenu.jsp");
            return;
        }

        int eventId = Integer.parseInt(eventIdStr);

        // イベント情報と関連データを取得
        EventEditService service = new EventEditService();
        Event event = service.findEventById(eventId);
        List<Integer> weekdayIds = service.findWeekdaysByEventId(eventId);
        event.setWeekdayIds(weekdayIds);

        // カラー・曜日データも取得
        ColorDAO colorDao = new ColorDAO();
        WeekdayDAO weekdayDao = new WeekdayDAO();
        List<Color> colorList = colorDao.findAll();
        List<Weekday> weekdayList = weekdayDao.findAll();

        // JSPへ渡す
        request.setAttribute("event", event);
        request.setAttribute("colorList", colorList);
        request.setAttribute("weekdayList", weekdayList);

        request.getRequestDispatcher("/WEB-INF/jsp/eventEditForm.jsp").forward(request, response);
    }

    /**
     * POSTリクエスト処理。<br>
     * 入力された値をもとに、確認画面または更新処理を実行します。
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

        // 確認 or 更新のみを受け付ける
        if ("confirm".equals(action) || "submit".equals(action)) {

            int eventId = Integer.parseInt(request.getParameter("event_id"));
            EventEditService service = new EventEditService();
            Event existingEvent = service.findEventById(eventId);

            // イベント存在チェック
            if (existingEvent == null) {
                request.setAttribute("error", "該当するイベントが見つかりませんでした。");
                request.getRequestDispatcher("/WEB-INF/jsp/eventEditForm.jsp").forward(request, response);
                return;
            }

            // イベントオブジェクト生成（入力内容を反映）
            Event event = new Event();
            event.setEvent_id(eventId);

            // タイトル
            String title = request.getParameter("title");
            event.setTitle((title != null && !title.isEmpty()) ? title : existingEvent.getTitle());

            // 説明
            String description = request.getParameter("description");
            event.setDescription((description != null && !description.isEmpty()) ? description : existingEvent.getDescription());

            // 日付
            String dateStr = request.getParameter("date");
            event.setDate((dateStr != null && !dateStr.isEmpty()) ? LocalDate.parse(dateStr) : existingEvent.getDate());

            // 開始時間
            try {
                event.setStartHour(Integer.parseInt(request.getParameter("startHour")));
                event.setStartMinute(Integer.parseInt(request.getParameter("startMinute")));
            } catch (Exception e) {
                event.setStartHour(existingEvent.getStartHour());
                event.setStartMinute(existingEvent.getStartMinute());
            }

            // 継続時間
            try {
                event.setDurationMinutes(Integer.parseInt(request.getParameter("durationMinutes")));
            } catch (Exception e) {
                event.setDurationMinutes(existingEvent.getDurationMinutes());
            }

            // カラー
            String colorId = request.getParameter("color_id");
            event.setColor_id((colorId != null && !colorId.isEmpty()) ? colorId : existingEvent.getColor_id());

            // 繰り返しフラグ
            String repeatStr = request.getParameter("repeat_flag");
            event.setRepeat_flag("1".equals(repeatStr));

            // 曜日
            String[] weekdayIdParams = request.getParameterValues("weekday_ids");
            if (weekdayIdParams != null) {
                List<Integer> weekdayIds = new ArrayList<>();
                for (String idStr : weekdayIdParams) {
                    weekdayIds.add(Integer.parseInt(idStr));
                }
                event.setWeekdayIds(weekdayIds);
            } else {
                event.setWeekdayIds(existingEvent.getWeekdayIds());
            }

            /* ---------- 確認画面表示 ---------- */
            if ("confirm".equals(action)) {
                WeekdayDAO weekdayDao = new WeekdayDAO();
                List<Weekday> weekdayList = weekdayDao.findAll();
                
             // 色名をセット
                ColorDAO colorDao = new ColorDAO();
                String colorName = colorDao.findColorNameById(event.getColor_id());
                event.setColor_name(colorName);

                request.setAttribute("event", event);
                request.setAttribute("weekdayList", weekdayList);
                request.getRequestDispatcher("/WEB-INF/jsp/eventEditConfirm.jsp").forward(request, response);
            }

            /* ---------- 更新処理実行 ---------- */
            else if ("submit".equals(action)) {
                boolean result = service.updateEvent(event);

                if (result) {
                    request.getRequestDispatcher("/WEB-INF/jsp/eventEditComplete.jsp").forward(request, response);
                } else {
                    ColorDAO colorDao = new ColorDAO();
                    WeekdayDAO weekdayDao = new WeekdayDAO();
                    List<Color> colorList = colorDao.findAll();
                    List<Weekday> weekdayList = weekdayDao.findAll();

                    request.setAttribute("colorList", colorList);
                    request.setAttribute("weekdayList", weekdayList);
                    request.setAttribute("error", "更新に失敗しました。");
                    request.getRequestDispatcher("/WEB-INF/jsp/eventEditForm.jsp").forward(request, response);
                }
            }
        }
    }
}
