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

@WebServlet("/EventDeleteServlet")
public class EventDeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action"); // ★ actionで分岐
        String eventIdStr = request.getParameter("event_id");

        if (eventIdStr == null || eventIdStr.isEmpty()) {
            response.sendRedirect("error.jsp");
            return;
        }

        int eventId = Integer.parseInt(eventIdStr);
        EventEditService service = new EventEditService();

        // ====== 削除確認画面の表示 ======
        if (action == null || "confirm".equals(action)) {
            Event event = service.findEventById(eventId);
            request.setAttribute("event", event);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/eventDeleteConfirm.jsp");
            rd.forward(request, response);
        }

        // ====== 削除確定（delete_flag更新） ======
        else if ("submit".equals(action)) {
            boolean result = service.deleteEvent(eventId);
            if (result) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/eventDeleteComplete.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("errorMsg", "削除に失敗しました。");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/eventDeleteConfirm.jsp");
                rd.forward(request, response);
            }
        }
    }
}
