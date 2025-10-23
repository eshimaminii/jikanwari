package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.EventsDAO;
import dao.WeekdayDAO;
import model.Event;
import model.Weekday;

public class WeeklyDisplayService {

    /**
     * 繰り返し設定されているイベント一覧を取得
     * 
     * @param userId ログイン中のユーザーID
     * @return List<Event> 繰り返し予定一覧
     */
    public List<Event> getRepeatedEvents(String userId) {
        EventsDAO dao = new EventsDAO();
        return dao.findRepeatedEvents(userId);
    }
    
    public Map<String, List<Event>> getGroupedRepeatedEvents(String userId) {
        EventsDAO dao = new EventsDAO();
        List<Event> events = dao.findRepeatedEvents(userId); // repeat_flag=1の予定

        WeekdayDAO weekdayDao = new WeekdayDAO();
        Map<Integer, String> weekdayNames = new HashMap<>();
        for (Weekday w : weekdayDao.findAll()) {
            weekdayNames.put(w.getWeekday_id(), w.getWeekday());
        }

        // 曜日ごとのリスト作成
        Map<String, List<Event>> grouped = new LinkedHashMap<>();
        for (Weekday w : weekdayDao.findAll()) {
            grouped.put(w.getWeekday(), new ArrayList<>());
        }

        // イベントを曜日ごとに振り分け
        for (Event e : events) {
            List<Integer> weekdays = dao.findWeekdaysByEventId(e.getEvent_id());
            for (int id : weekdays) {
                String name = weekdayNames.get(id);
                grouped.get(name).add(e);
            }
        }

        // 空の曜日を削除
        grouped.entrySet().removeIf(entry -> entry.getValue().isEmpty());

        return grouped;
    }
}
