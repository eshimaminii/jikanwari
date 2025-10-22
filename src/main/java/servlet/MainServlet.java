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

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ログインチェック
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

		// 今日の日付の予定を取得
		LocalDate today = LocalDate.now();
		ScheduleDisplayService service = new ScheduleDisplayService();
		List<Event> todayEvents = service.getTodayEvents(loginUser.getUserId(), today);

		request.setAttribute("todayEvents", todayEvents);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
}
