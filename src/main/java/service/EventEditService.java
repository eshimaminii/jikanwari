package service;

import java.util.List;

import dao.EventsDAO;
import model.Event;

/**
 * {@code EventEditService} クラスは、
 * イベントの編集・削除などのビジネスロジックを担当するサービスクラスです。<br>
 * DAO層（{@link dao.EventsDAO}）を利用して、
 * イベントの更新や論理削除を安全に実行します。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>イベント情報の更新（単発・繰り返し両対応）</li>
 *   <li>イベントIDによる取得</li>
 *   <li>曜日リストの取得</li>
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
 * @version 1.1
 */
public class EventEditService {

    /** DAOインスタンス（データアクセス専用） */
    private final EventsDAO dao = new EventsDAO();

    /**
     * イベント情報を更新します。<br>
     * 繰り返しフラグが有効な場合は、関連する曜日データを再登録します。
     *
     * @param event 更新対象の {@link Event} オブジェクト
     * @return 更新が成功した場合 {@code true}、失敗した場合 {@code false}
     */
    public boolean updateEvent(Event event) {
        boolean updated = dao.update(event);

        // 繰り返しイベントの場合、関連する曜日情報を再登録
        if (updated && event.isRepeat_flag()) {
            dao.deleteWeekdays(event.getEvent_id());                      // 既存の曜日データ削除
            dao.insertWeekdays(event.getEvent_id(), event.getWeekdayIds()); // 新しい曜日データ登録
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
        return dao.findById(eventId);
    }

    /**
     * 指定されたイベントに紐づく曜日IDリストを取得します。<br>
     * 繰り返しイベントの編集画面で、曜日チェックボックスを初期表示する際などに使用します。
     *
     * @param eventId 対象イベントのID
     * @return 曜日IDの {@link List}（存在しない場合は空リスト）
     */
    public List<Integer> findWeekdaysByEventId(int eventId) {
        return dao.findWeekdaysByEventId(eventId);
    }

    /**
     * 指定したイベントを論理削除します。<br>
     * 実際のレコードは残し、{@code delete_flag = 1} に更新します。
     *
     * @param eventId 削除対象のイベントID
     * @return 削除成功時は {@code true}、失敗時は {@code false}
     */
    public boolean deleteEvent(int eventId) {
        return dao.deleteEvent(eventId);
    }
}
