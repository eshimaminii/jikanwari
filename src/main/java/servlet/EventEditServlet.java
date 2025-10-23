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
import dao.EventsDAO;
import dao.WeekdayDAO;
import model.Color;
import model.Event;
import model.Weekday;
import service.EventEditService;

@WebServlet("/EventEditServlet")
public class EventEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 編集フォーム表示
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String eventIdStr = request.getParameter("event_id");
        if (eventIdStr == null || eventIdStr.isEmpty()) {
            response.sendRedirect("mainMenu.jsp");
            return;
        }

        int eventId = Integer.parseInt(eventIdStr);

        EventsDAO dao = new EventsDAO();
        Event event = dao.findById(eventId);
        List<Integer> weekdayIds = dao.findWeekdaysByEventId(eventId);
        event.setWeekdayIds(weekdayIds);

        ColorDAO colorDao = new ColorDAO();
        WeekdayDAO weekdayDao = new WeekdayDAO();
        List<Color> colorList = colorDao.findAll();
        List<Weekday> weekdayList = weekdayDao.findAll();

        request.setAttribute("event", event);
        request.setAttribute("colorList", colorList);
        request.setAttribute("weekdayList", weekdayList);

        request.getRequestDispatcher("/WEB-INF/jsp/eventEditForm.jsp").forward(request, response);
    }

    // 編集確認＆登録
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("confirm".equals(action) || "submit".equals(action)) {

            int eventId = Integer.parseInt(request.getParameter("event_id"));

            // 既存イベントを取得（null対策）
            EventsDAO dao = new EventsDAO();
            Event existingEvent = dao.findById(eventId);

            if (existingEvent == null) {
                request.setAttribute("error", "該当するイベントが見つかりませんでした。");
                request.getRequestDispatcher("/WEB-INF/jsp/eventEditForm.jsp").forward(request, response);
                return;
            }

            // 新しいイベントオブジェクト作成
            Event event = new Event();
            event.setEvent_id(eventId);

            // ✅ タイトル（空白対策）
            String title = request.getParameter("title");
            if (title != null && !title.isEmpty()) {
                event.setTitle(title);
            } else {
                event.setTitle(existingEvent.getTitle());
            }

            // ✅ 説明
            String description = request.getParameter("description");
            if (description != null && !description.isEmpty()) {
                event.setDescription(description);
            } else {
                event.setDescription(existingEvent.getDescription());
            }

            // ✅ 日付
            String dateStr = request.getParameter("date");
            if (dateStr != null && !dateStr.isEmpty()) {
                event.setDate(LocalDate.parse(dateStr));
            } else {
                event.setDate(existingEvent.getDate());
            }

            // ✅ 時間
            try {
                event.setStartHour(Integer.parseInt(request.getParameter("startHour")));
                event.setStartMinute(Integer.parseInt(request.getParameter("startMinute")));
            } catch (Exception e) {
                event.setStartHour(existingEvent.getStartHour());
                event.setStartMinute(existingEvent.getStartMinute());
            }

            // ✅ 継続時間
            try {
                event.setDurationMinutes(Integer.parseInt(request.getParameter("durationMinutes")));
            } catch (Exception e) {
                event.setDurationMinutes(existingEvent.getDurationMinutes());
            }

            // ✅ カラー
            String colorId = request.getParameter("color_id");
            System.out.println("🎨 color_id = " + request.getParameter("color_id"));
            if (colorId != null && !colorId.isEmpty()) {
                event.setColor_id(colorId);
            } else {
                event.setColor_id(existingEvent.getColor_id());
            }

            // ✅ 繰り返しフラグ
            String repeatStr = request.getParameter("repeat_flag");
            event.setRepeat_flag("1".equals(repeatStr));

            // ✅ 曜日
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

            // ===== 画面分岐 =====
            if ("confirm".equals(action)) {
                WeekdayDAO weekdayDao = new WeekdayDAO();
                List<Weekday> weekdayList = weekdayDao.findAll();

                request.setAttribute("event", event);
                request.setAttribute("weekdayList", weekdayList);
                request.getRequestDispatcher("/WEB-INF/jsp/eventEditConfirm.jsp").forward(request, response);

            } else if ("submit".equals(action)) {
                EventEditService service = new EventEditService();
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
