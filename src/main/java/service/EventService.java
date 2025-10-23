package service;

import java.util.List;

import dao.EventsDAO;
import model.Event;

public class EventService {
	public boolean addEvent(Event event, String userId, List<Integer> weekdayIds) {
	    EventsDAO dao = new EventsDAO();
	    event.setUser_id(userId);

	    boolean inserted = dao.insert(event);

	    if (inserted) {
	        // 登録したイベントの event_id を取得して曜日を登録
	        int newEventId = dao.getLastInsertedId();
	        dao.insertWeekdays(newEventId, weekdayIds);
	    }

	    return inserted;
	}

}
