package service;

import java.time.LocalDate;
import java.util.List;

import dao.EventsDAO;
import model.Event;

public class ScheduleDisplayService {

	public List<Event> getTodayEvents(String userId, LocalDate date) {
		EventsDAO dao = new EventsDAO();
		return dao.findByDate(userId, date);
	}
}
