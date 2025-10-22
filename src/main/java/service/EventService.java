package service;

import dao.EventsDAO;
import model.Event;

public class EventService {
    public boolean addEvent(Event event, String userId) {
        EventsDAO dao = new EventsDAO();
        event.setUser_id(userId);
        return dao.insert(event);
    }
}
