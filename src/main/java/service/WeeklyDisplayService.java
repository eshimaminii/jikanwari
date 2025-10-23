package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.EventsDAO;
import model.Event;

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
        Map<String, List<Event>> grouped = new LinkedHashMap<>();

        EventsDAO dao = new EventsDAO();
        
        // すでに取得している「曜日ごと」リストをもとに
        Map<String, List<Event>> original = dao.getGroupedEvents(userId);

        // 「イベントID → 登場した曜日の数」を数えるマップ
        Map<Integer, Integer> eventCount = new HashMap<>();
        for (List<Event> list : original.values()) {
            for (Event e : list) {
                eventCount.put(e.getEvent_id(), eventCount.getOrDefault(e.getEvent_id(), 0) + 1);
            }
        }

     // 「毎日」グループを新しく作る
        List<Event> everydayEvents = new ArrayList<>();

        // 曜日ごとのループ
        for (Map.Entry<String, List<Event>> entry : original.entrySet()) {
            String weekday = entry.getKey();
            List<Event> filtered = new ArrayList<>();

            for (Event e : entry.getValue()) {
                if (eventCount.get(e.getEvent_id()) == 7) {
                    // 7曜日すべてに存在するイベントなら「毎日」へ（★重複を防止）
                    boolean alreadyExists = everydayEvents.stream()
                            .anyMatch(ev -> ev.getEvent_id() == e.getEvent_id());
                    if (!alreadyExists) {
                        everydayEvents.add(e);
                    }
                } else {
                    filtered.add(e);
                }
            }

            if (!filtered.isEmpty()) {
                grouped.put(weekday, filtered);
            }
        }

        // 最後に「毎日」グループを先頭に追加
        if (!everydayEvents.isEmpty()) {
            grouped.put("毎日", everydayEvents);
        }


        return grouped;
    }

}
