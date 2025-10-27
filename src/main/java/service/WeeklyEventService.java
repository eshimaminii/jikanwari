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

/**
 * {@code WeeklyEventService} クラスは、
 * イベントの「曜日設定」や「繰り返しフラグ管理」に関する
 * ビジネスロジックを担当するサービスクラスです。<br>
 * DAO層（{@link dao.EventsDAO}, {@link dao.WeekdayDAO}）を利用して、
 * 繰り返し予定の曜日登録・更新・削除を一括管理します。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>イベント情報の取得（{@link #findEventById(int)}）</li>
 *   <li>全曜日リストの取得（{@link #getAllWeekdays()}）</li>
 *   <li>イベントに紐づく曜日IDリストの取得（{@link #findWeekdaysByEventId(int)}）</li>
 *   <li>曜日選択データのリスト化（確認画面用）（{@link #getWeekdaysByIds(String[])}）</li>
 *   <li>曜日設定の更新（{@link #updateWeekdays(int, String[])}）</li>
 *   <li>繰り返しフラグを含めた曜日設定更新（{@link #updateWeekdaysAndRepeatFlag(int, String[], boolean)}）</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * WeeklyEventService service = new WeeklyEventService();
 * boolean result = service.updateWeekdaysAndRepeatFlag(5, new String[] {"2", "4"}, true);
 * if (result) {
 *     System.out.println("更新成功！");
 * }
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class WeeklyEventService {

    /**
     * 指定したイベントIDのイベント情報を取得します。<br>
     * DAO層の {@link dao.EventsDAO#findById(int)} を利用します。
     *
     * @param eventId 取得対象のイベントID
     * @return 該当する {@link Event} オブジェクト（存在しない場合は {@code null}）
     */
    public Event findEventById(int eventId) {
        EventsDAO dao = new EventsDAO();
        return dao.findById(eventId);
    }

    /**
     * 全ての曜日情報を取得します。<br>
     * DAO層の {@link dao.WeekdayDAO#findAll()} を呼び出します。
     *
     * @return {@link Weekday} のリスト（曜日順）
     */
    public List<Weekday> getAllWeekdays() {
        WeekdayDAO dao = new WeekdayDAO();
        return dao.findAll();
    }

    /**
     * 指定イベントに紐づく曜日IDの一覧を取得します。<br>
     * DAO層の {@link dao.EventsDAO#findWeekdaysByEventId(int)} を呼び出します。
     *
     * @param eventId イベントID
     * @return 曜日IDのリスト（存在しない場合は空リスト）
     */
    public List<Integer> findWeekdaysByEventId(int eventId) {
        EventsDAO dao = new EventsDAO();
        return dao.findWeekdaysByEventId(eventId);
    }

    /**
     * チェックされた曜日ID配列から曜日リストを取得します。<br>
     * 登録前の確認画面などで、ユーザーに選択内容を再表示する用途に使います。
     *
     * @param weekdayIds 曜日ID配列（例：{"2", "4", "6"}）
     * @return 選択された曜日の {@link Weekday} リスト
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
     * イベントに紐づく曜日設定を更新します。<br>
     * 既存データを削除後、指定された曜日IDを再登録します。
     *
     * @param eventId    対象イベントのID
     * @param weekdayIds 新しく設定する曜日ID配列
     * @return 更新成功時は {@code true}、失敗時は {@code false}
     */
    public boolean updateWeekdays(int eventId, String[] weekdayIds) {
        EventsDAO dao = new EventsDAO();

        try {
            dao.deleteWeekdays(eventId); // 既存削除

            // 新しい曜日が選択されている場合のみ再登録
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

    /**
     * イベントの曜日設定および繰り返しフラグを同時に更新します。<br>
     * DAO層で曜日情報を削除・再登録した後、<br>
     * 繰り返しフラグ（repeat_flag）を直接更新します。
     *
     * @param eventId     更新対象のイベントID
     * @param weekdayIds  選択された曜日ID配列（nullの場合は削除のみ）
     * @param hasWeekdays 曜日設定が存在する場合 {@code true}、存在しない場合 {@code false}
     * @return 更新成功時は {@code true}、失敗時は {@code false}
     */
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

        // repeat_flag 更新
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
