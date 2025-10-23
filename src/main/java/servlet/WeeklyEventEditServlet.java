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

@WebServlet("/WeeklyEventEditServlet")
public class WeeklyEventEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        WeeklyEventService service = new WeeklyEventService();

        // ===== 編集フォーム =====
        if ("edit".equals(action)) {
            int eventId = Integer.parseInt(request.getParameter("event_id"));
            Event event = service.findEventById(eventId);
            List<Weekday> weekdayList = service.getAllWeekdays();
            List<Integer> selectedWeekdays = service.findWeekdaysByEventId(eventId);

            // JSPに渡す
            request.setAttribute("event", event);
            request.setAttribute("weekdayList", weekdayList);
            request.setAttribute("selectedWeekdays", selectedWeekdays);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/weeklyEventForm.jsp");
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

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/weeklyEventConfirm.jsp");
            rd.forward(request, response);
            return;
        }

        // ===== 完了処理 =====
        if ("submit".equals(action)) {
            int eventId = (int) session.getAttribute("event_id");
            String[] selectedIds = (String[]) session.getAttribute("selectedWeekdayIds");

            boolean result = service.updateWeekdays(eventId, selectedIds);

            session.removeAttribute("event_id");
            session.removeAttribute("selectedWeekdayIds");

            if (result) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/weeklyEventComplete.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("errorMsg", "更新に失敗しました。");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/weeklyEventForm.jsp");
                rd.forward(request, response);
            }
        }
    }
}
