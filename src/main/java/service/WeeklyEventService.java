package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import dao.DBManager;
import dao.EventsDAO;
import dao.WeekdayDAO;
import model.Event;
import model.Weekday;

public class WeeklyEventService {

    /**
     * 指定IDのイベントを取得
     */
    public Event findEventById(int eventId) {
        EventsDAO dao = new EventsDAO();
        return dao.findById(eventId);
    }

    /**
     * 全ての曜日リストを取得
     */
    public List<Weekday> getAllWeekdays() {
        WeekdayDAO dao = new WeekdayDAO();
        return dao.findAll();
    }

    /**
     * イベントに紐づく曜日IDリストを取得
     */
    public List<Integer> findWeekdaysByEventId(int eventId) {
        EventsDAO dao = new EventsDAO();
        return dao.findWeekdaysByEventId(eventId);
    }

    /**
     * チェックされた曜日ID配列から曜日リストを取得（確認画面用）
     */
    public List<Weekday> getWeekdaysByIds(String[] weekdayIds) {
        List<Weekday> list = new ArrayList<>();
        if (weekdayIds == null) return list;

        WeekdayDAO dao = new WeekdayDAO();
        for (String id : weekdayIds) {
            Weekday w = dao.findById(Integer.parseInt(id));
            if (w != null) list.add(w);
        }
        return list;
    }

    /**
     * 曜日設定を更新（既存削除 → 再登録）
     */
    public boolean updateWeekdays(int eventId, String[] weekdayIds) {
        EventsDAO dao = new EventsDAO();

        try {
            dao.deleteWeekdays(eventId); // いったん削除

            // 再登録（選択ありの場合のみ）
            if (weekdayIds != null && weekdayIds.length > 0) {
                List<Integer> ids = new ArrayList<>();
                for (String id : weekdayIds) {
                    ids.add(Integer.parseInt(id));
                }
                dao.insertWeekdays(eventId, ids);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateWeekdaysAndRepeatFlag(int eventId, String[] weekdayIds, boolean hasWeekdays) {
        EventsDAO dao = new EventsDAO();
        dao.deleteWeekdays(eventId);

        // 曜日がある場合のみ insert
        if (hasWeekdays && weekdayIds != null) {
            List<Integer> weekdayList = new ArrayList<>();
            for (String id : weekdayIds) {
                weekdayList.add(Integer.parseInt(id));
            }
            dao.insertWeekdays(eventId, weekdayList);
        }

        // ★ repeat_flagを更新（曜日があれば1、なければ0）
        try (Connection conn = DBManager.getConnection()) {
            String sql = "UPDATE events SET repeat_flag = ? WHERE event_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, hasWeekdays ? 1 : 0);
                stmt.setInt(2, eventId);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
