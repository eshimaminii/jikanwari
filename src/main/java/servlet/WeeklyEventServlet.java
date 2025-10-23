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

@WebServlet("/WeeklyEventServlet")
public class WeeklyEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // ログインチェック
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        try {
            // 曜日ごとにグループ化された繰り返し予定を取得
            WeeklyDisplayService displayService = new WeeklyDisplayService();
            Map<String, List<Event>> groupedEvents =
                    displayService.getGroupedRepeatedEvents(loginUser.getUserId());


            // 🌈 各キーを出力して中身確認！
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
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/weeklyEvent.jsp");
        rd.forward(request, response);
    }
}
