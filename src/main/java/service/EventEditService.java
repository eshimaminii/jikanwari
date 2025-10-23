package service;

import dao.EventsDAO;
import model.Event;

public class EventEditService {
	public boolean updateEvent(Event event) {
		EventsDAO dao = new EventsDAO();
		boolean updated = dao.update(event);

		if (updated && event.isRepeat_flag()) {
			dao.deleteWeekdays(event.getEvent_id()); // 既存の曜日を削除
			dao.insertWeekdays(event.getEvent_id(), event.getWeekdayIds()); // 新しい曜日を登録
		}
		return updated;
	}
	
	 public Event findEventById(int eventId) {
	        EventsDAO dao = new EventsDAO();
	        return dao.findById(eventId);
	    }
	 
	 public boolean deleteEvent(int eventId) {
	        EventsDAO dao = new EventsDAO();
	        return dao.deleteEvent(eventId);
	    }
}