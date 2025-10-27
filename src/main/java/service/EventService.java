package service;

import java.util.List;

import dao.EventsDAO;
import model.Event;

/**
 * {@code EventService} クラスは、イベントの新規登録処理を担当するサービスクラスです。<br>
 * DAO層（{@link dao.EventsDAO}）を利用して、イベント本体および
 * 繰り返し設定に関する曜日情報を登録します。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>イベントの新規追加（単発・繰り返し対応）</li>
 *   <li>ユーザーIDの紐付け処理</li>
 *   <li>繰り返し曜日の登録（event_weekdaysテーブル）</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * EventService service = new EventService();
 * Event event = new Event();
 * event.setTitle("買い物");
 * event.setDate(LocalDate.of(2025, 10, 28));
 * event.setRepeat_flag(true);
 * 
 * List<Integer> weekdays = List.of(2, 4); // 月曜・水曜
 * boolean result = service.addEvent(event, "user01@example.com", weekdays);
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class EventService {

    /**
     * 新しいイベントを登録します。<br>
     * イベント情報を {@link dao.EventsDAO#insert(Event)} により登録し、
     * 繰り返し設定がONの場合は、曜日情報を event_weekdays テーブルにも登録します。
     *
     * @param event      登録するイベント情報
     * @param userId     登録ユーザーのID
     * @param weekdayIds 繰り返し設定で指定された曜日IDリスト（null または空の場合は無視）
     * @return 登録が成功した場合は {@code true}、失敗した場合は {@code false}
     */
    public boolean addEvent(Event event, String userId, List<Integer> weekdayIds) {
        EventsDAO dao = new EventsDAO();
        event.setUser_id(userId);

        // イベント本体を登録し、自動採番されたIDを取得
        Integer newEventId = dao.insert(event);

        System.out.println("🌸 newEventId = " + newEventId);
        System.out.println("🌸 repeat_flag = " + event.isRepeat_flag());
        System.out.println("🌸 weekdayIds = " + weekdayIds);

        if (newEventId == null) return false;

        // 繰り返し設定あり＆曜日指定が存在する場合、関連テーブルに登録
        if (event.isRepeat_flag() && weekdayIds != null && !weekdayIds.isEmpty()) {
            dao.insertWeekdays(newEventId, weekdayIds);
        }

        return true;
    }
}
