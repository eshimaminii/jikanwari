package service;

import dao.EventsDAO;
import model.Event;

/**
 * {@code EventEditService} クラスは、イベント編集や削除などの
 * ビジネスロジックを担当するサービスクラスです。<br>
 * DAO層（{@link dao.EventsDAO}）を利用して、イベントの更新・削除を安全に処理します。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>イベントの更新（単発・繰り返し両対応）</li>
 *   <li>イベントIDによる取得</li>
 *   <li>イベントの削除（論理削除）</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * EventEditService service = new EventEditService();
 * Event event = service.findEventById(5);
 * event.setTitle("会議（変更後）");
 * boolean result = service.updateEvent(event);
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class EventEditService {

    /**
     * イベント情報を更新します。<br>
     * 繰り返しフラグが有効な場合は、関連する曜日データも更新します。
     *
     * @param event 更新対象の {@link Event} オブジェクト
     * @return 更新が成功した場合 {@code true}、失敗した場合 {@code false}
     */
    public boolean updateEvent(Event event) {
        EventsDAO dao = new EventsDAO();
        boolean updated = dao.update(event);

        // 繰り返しイベントの場合、関連する曜日情報を再登録
        if (updated && event.isRepeat_flag()) {
            dao.deleteWeekdays(event.getEvent_id());          // 既存の曜日データを削除
            dao.insertWeekdays(event.getEvent_id(), event.getWeekdayIds()); // 新しい曜日を登録
        }
        return updated;
    }

    /**
     * イベントIDを指定して、該当イベント情報を取得します。
     *
     * @param eventId 検索対象のイベントID
     * @return 該当する {@link Event} オブジェクト（存在しない場合は {@code null}）
     */
    public Event findEventById(int eventId) {
        EventsDAO dao = new EventsDAO();
        return dao.findById(eventId);
    }

    /**
     * 指定したイベントを削除します（論理削除）。<br>
     * DAO層の {@link dao.EventsDAO#deleteEvent(int)} を呼び出します。
     *
     * @param eventId 削除対象のイベントID
     * @return 削除成功時は {@code true}、失敗時は {@code false}
     */
    public boolean deleteEvent(int eventId) {
        EventsDAO dao = new EventsDAO();
        return dao.deleteEvent(eventId);
    }
}
