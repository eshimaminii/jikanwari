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

/**
 * Servlet implementation class EventEditServlet
 */
@WebServlet("/EventEditServlet")
public class EventEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventEditServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");

	    String eventIdStr = request.getParameter("event_id");
	    if (eventIdStr == null || eventIdStr.isEmpty()) {
	        response.sendRedirect("mainMenu.jsp");
	        return;
	    }

	    int eventId = Integer.parseInt(eventIdStr);

	    // イベント情報取得
	    EventsDAO dao = new EventsDAO();
	    Event event = dao.findById(eventId); // イベント本体
	    List<Integer> weekdayIds = dao.findWeekdaysByEventId(eventId); // 曜日情報
	    event.setWeekdayIds(weekdayIds);

	    // カラー・曜日選択肢取得
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
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		if ("confirm".equals(action)) {
			Event event = new Event();
			event.setEvent_id(Integer.parseInt(request.getParameter("event_id")));
			event.setTitle(request.getParameter("title"));
			event.setDate(LocalDate.parse(request.getParameter("date")));
			event.setStartHour(Integer.parseInt(request.getParameter("startHour")));
			event.setStartMinute(Integer.parseInt(request.getParameter("startMinute")));
			event.setDurationMinutes(Integer.parseInt(request.getParameter("durationMinutes")));
			event.setDescription(request.getParameter("description"));
			event.setRepeat_flag(Boolean.parseBoolean(request.getParameter("repeat_flag")));
			event.setColor_id(request.getParameter("color_id"));
			WeekdayDAO weekdayDao = new WeekdayDAO();
			List<Weekday> weekdayList = weekdayDao.findAll();
			request.setAttribute("weekdayList", weekdayList);
			
			String[] weekdayIdParams = request.getParameterValues("weekday_ids");
			if (weekdayIdParams != null) {
			    List<Integer> weekdayIds = new ArrayList<>();
			    for (String idStr : weekdayIdParams) {
			        weekdayIds.add(Integer.parseInt(idStr));
			    }
			    event.setWeekdayIds(weekdayIds);
			}

			request.setAttribute("event", event);
			request.getRequestDispatcher("/WEB-INF/jsp/eventEditConfirm.jsp").forward(request, response);
		} else if ("submit".equals(action)) {
			Event event = new Event();
			event.setEvent_id(Integer.parseInt(request.getParameter("event_id")));
			event.setTitle(request.getParameter("title"));
			event.setDate(LocalDate.parse(request.getParameter("date")));
			event.setStartHour(Integer.parseInt(request.getParameter("startHour")));
			event.setStartMinute(Integer.parseInt(request.getParameter("startMinute")));
			event.setDurationMinutes(Integer.parseInt(request.getParameter("durationMinutes")));
			event.setDescription(request.getParameter("description"));
			event.setRepeat_flag(Boolean.parseBoolean(request.getParameter("repeat_flag")));
			event.setColor_id(request.getParameter("color_id"));

			String[] weekdayIdParams = request.getParameterValues("weekday_ids");
			if (weekdayIdParams != null) {
			    List<Integer> weekdayIds = new ArrayList<>();
			    for (String idStr : weekdayIdParams) {
			        weekdayIds.add(Integer.parseInt(idStr));
			    }
			    event.setWeekdayIds(weekdayIds);
			}

			
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
				request.setAttribute("error", "更新に失敗しました");
				request.getRequestDispatcher("/WEB-INF/jsp/eventEditForm.jsp").forward(request, response);
			}
		}
	}

}
