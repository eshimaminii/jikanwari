package service;

import java.util.List;

import dao.EventsDAO;
import model.Event;

public class EventService {
    public boolean addEvent(Event event, String userId, List<Integer> weekdayIds) {
    	
        EventsDAO dao = new EventsDAO();
        event.setUser_id(userId);

        Integer newEventId = dao.insert(event);   // ← 生成IDを受け取る
        
        System.out.println("🌸 newEventId = " + newEventId);
        System.out.println("🌸 repeat_flag = " + event.isRepeat_flag());
        System.out.println("🌸 weekdayIds = " + weekdayIds);
        
        if (newEventId == null) return false;

        // 繰り返しONかつ曜日が選ばれていれば関連テーブルへ登録
        if (event.isRepeat_flag() && weekdayIds != null && !weekdayIds.isEmpty()) {
            dao.insertWeekdays(newEventId, weekdayIds);
        }
        return true;
    }
}
