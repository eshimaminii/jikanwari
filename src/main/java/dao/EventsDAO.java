package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Event;

/**
 * {@code EventsDAO} クラスは、events テーブルおよび event_weekdays テーブルに対する
 * データアクセス操作（CRUD処理）を行うDAOクラスです。<br>
 * 単発イベント・繰り返しイベントの取得、登録、更新、削除などを担当します。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>指定日のイベント一覧取得（{@link #findByDate(String, LocalDate)}）</li>
 *   <li>イベントの新規登録（{@link #insert(Event)}）</li>
 *   <li>イベントの更新（{@link #update(Event)}）</li>
 *   <li>イベントIDからの検索（{@link #findById(int)}）</li>
 *   <li>イベント削除（論理削除）（{@link #deleteEvent(int)}）</li>
 *   <li>繰り返しイベント一覧取得（{@link #findRepeatedEvents(String)}）</li>
 *   <li>曜日関連テーブルの操作（{@link #insertWeekdays(int, List)}, {@link #deleteWeekdays(int)})</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * EventsDAO dao = new EventsDAO();
 * List<Event> todayEvents = dao.findByDate("user01", LocalDate.now());
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class EventsDAO {

    /**
     * 指定したユーザーと日付に対応するイベントを取得します。<br>
     * 単発イベントおよび曜日繰り返しイベントの両方を対象にします。
     *
     * @param userId ユーザーID
     * @param date   検索対象日
     * @return 該当する {@link Event} のリスト（存在しない場合は空リスト）
     */
    public List<Event> findByDate(String userId, LocalDate date) {
        List<Event> events = new ArrayList<>();
        String sql = """
            SELECT 
                e.event_id, e.title, e.date, e.description, e.repeat_flag,
                e.color_id, e.delete_flag, e.duration_minutes,
                TIME_FORMAT(e.time, '%H') AS start_hour, 
                TIME_FORMAT(e.time, '%i') AS start_minute
            FROM events e
            LEFT JOIN event_weekdays ew ON e.event_id = ew.event_id
            LEFT JOIN weekdays w ON ew.weekday_id = w.weekday_id
            WHERE e.user_id = ?
              AND e.delete_flag = 0
              AND (
                  e.date = ?                           -- 単発予定
                  OR (e.repeat_flag = 1 AND w.weekday_id = ?)  -- 曜日繰り返し予定
              )
            ORDER BY e.time;
        """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setDate(2, java.sql.Date.valueOf(date));

            // Javaの曜日補正（月曜=1 → 日曜=1）
            int dayOfWeek = date.getDayOfWeek().getValue() + 1;
            if (dayOfWeek == 8) dayOfWeek = 1;
            pstmt.setInt(3, dayOfWeek);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event e = new Event();
                e.setEvent_id(rs.getInt("event_id"));
                e.setTitle(rs.getString("title"));
                e.setDate(rs.getDate("date").toLocalDate());
                e.setDescription(rs.getString("description"));
                e.setRepeat_flag(rs.getInt("repeat_flag") == 1);
                e.setColor_id(rs.getString("color_id"));
                e.setDelete_flag(rs.getInt("delete_flag") == 1);
                e.setStartHour(rs.getInt("start_hour"));
                e.setStartMinute(rs.getInt("start_minute"));
                e.setDurationMinutes(rs.getInt("duration_minutes"));
                events.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    /**
     * 新しいイベントをデータベースに登録します。<br>
     * 自動採番された {@code event_id} を返します。
     *
     * @param event 登録するイベント情報
     * @return 登録されたイベントのID（失敗時は {@code null}）
     */
    public Integer insert(Event event) {
        String sql = """
            INSERT INTO events
            (title, date, time, description, repeat_flag, color_id, duration_minutes, delete_flag, user_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            LocalTime eventTime = LocalTime.of(event.getStartHour(), event.getStartMinute());

            pstmt.setString(1, event.getTitle());
            pstmt.setDate(2, java.sql.Date.valueOf(event.getDate()));
            pstmt.setTime(3, Time.valueOf(eventTime));
            pstmt.setString(4, event.getDescription());
            pstmt.setInt(5, event.isRepeat_flag() ? 1 : 0);
            pstmt.setString(6, event.getColor_id());
            pstmt.setInt(7, event.getDurationMinutes()); // ← これを追加！！
            pstmt.setInt(8, event.isDelete_flag() ? 1 : 0);
            pstmt.setString(9, event.getUser_id());

            int result = pstmt.executeUpdate();
            if (result != 1) return null;

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ イベント登録中にエラー発生！");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 既存のイベント情報を更新します。
     *
     * @param event 更新対象のイベント
     * @return 更新が成功した場合 {@code true}、それ以外は {@code false}
     */
    public boolean update(Event event) {
        String sql = "UPDATE events SET title=?, date=?, time=?, description=?, repeat_flag=?, color_id=?, duration_minutes=? WHERE event_id=?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            LocalTime time = LocalTime.of(event.getStartHour(), event.getStartMinute());
            stmt.setString(1, event.getTitle());
            stmt.setDate(2, Date.valueOf(event.getDate()));
            stmt.setTime(3, Time.valueOf(time));
            stmt.setString(4, event.getDescription());
            stmt.setBoolean(5, event.isRepeat_flag());
            stmt.setString(6, event.getColor_id());
            stmt.setInt(7, event.getDurationMinutes());
            stmt.setInt(8, event.getEvent_id());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * イベントIDを指定して、該当するイベント情報を取得します。
     *
     * @param eventId イベントID
     * @return 該当する {@link Event} オブジェクト（存在しない場合は {@code null}）
     */
    public Event findById(int eventId) {
        Event event = null;
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT * FROM events WHERE event_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, eventId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        event = new Event();
                        event.setEvent_id(rs.getInt("event_id"));
                        event.setTitle(rs.getString("title"));
                        event.setDate(rs.getDate("date").toLocalDate());
                        event.setStartHour(rs.getTime("time").toLocalTime().getHour());
                        event.setStartMinute(rs.getTime("time").toLocalTime().getMinute());
                        event.setDurationMinutes(rs.getInt("duration_minutes"));
                        event.setDescription(rs.getString("description"));
                        event.setRepeat_flag(rs.getInt("repeat_flag") == 1);
                        event.setColor_id(rs.getString("color_id"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    /**
     * 指定したイベントIDに紐づく曜日ID一覧を取得します。
     *
     * @param eventId イベントID
     * @return 曜日IDのリスト
     */
    public List<Integer> findWeekdaysByEventId(int eventId) {
        List<Integer> weekdayIds = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT weekday_id FROM event_weekdays WHERE event_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, eventId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        weekdayIds.add(rs.getInt("weekday_id"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weekdayIds;
    }

    /**
     * 指定イベントに関連する曜日情報を削除します。
     *
     * @param eventId イベントID
     */
    public void deleteWeekdays(int eventId) {
        try (Connection conn = DBManager.getConnection()) {
            String sql = "DELETE FROM event_weekdays WHERE event_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, eventId);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定イベントに新しい曜日情報を登録します。
     *
     * @param eventId    イベントID
     * @param weekdayIds 登録する曜日IDのリスト
     */
    public void insertWeekdays(int eventId, List<Integer> weekdayIds) {
        if (weekdayIds == null || weekdayIds.isEmpty()) return;

        try (Connection conn = DBManager.getConnection()) {
            String sql = "INSERT INTO event_weekdays (event_id, weekday_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int weekdayId : weekdayIds) {
                    stmt.setInt(1, eventId);
                    stmt.setInt(2, weekdayId);
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定イベントを論理削除します（delete_flagを1に更新）。
     *
     * @param eventId イベントID
     * @return 削除が成功した場合は {@code true}
     */
    public boolean deleteEvent(int eventId) {
        String sql = "UPDATE events SET delete_flag = 1 WHERE event_id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 繰り返しフラグが有効なイベントを全件取得します。
     *
     * @param userId ユーザーID
     * @return 繰り返しイベントのリスト
     */
    public List<Event> findRepeatedEvents(String userId) {
        List<Event> events = new ArrayList<>();
        String sql = """
            SELECT event_id, title, date, time, description, repeat_flag, color_id
            FROM events
            WHERE user_id = ? AND repeat_flag = 1 AND delete_flag = 0
            ORDER BY date, time
        """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event e = new Event();
                e.setEvent_id(rs.getInt("event_id"));
                e.setTitle(rs.getString("title"));
                e.setDate(rs.getDate("date").toLocalDate());
                e.setDescription(rs.getString("description"));
                e.setRepeat_flag(true);
                e.setColor_id(rs.getString("color_id"));
                events.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    /**
     * 直近で自動採番されたIDを取得します。
     *
     * @return 最後に挿入されたイベントID（取得できなかった場合は -1）
     */
    public int getLastInsertedId() {
        int id = -1;
        String sql = "SELECT LAST_INSERT_ID()";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * ユーザーごとに繰り返しイベントを曜日別にグルーピングして取得します。
     *
     * @param userId ユーザーID
     * @return 曜日名をキーとし、その曜日に属する {@link Event} リストを値とするマップ
     */
    public Map<String, List<Event>> getGroupedEvents(String userId) {
        Map<String, List<Event>> groupedEvents = new LinkedHashMap<>();

        String sql = """
            SELECT e.event_id, e.title, w.weekday
            FROM events e
            JOIN event_weekdays ew ON e.event_id = ew.event_id
            JOIN weekdays w ON ew.weekday_id = w.weekday_id
            WHERE e.user_id = ? AND e.repeat_flag = 1
            ORDER BY w.weekday_id
        """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String weekday = rs.getString("weekday");

                    Event event = new Event();
                    event.setEvent_id(rs.getInt("event_id"));
                    event.setTitle(rs.getString("title"));

                    groupedEvents.computeIfAbsent(weekday, k -> new ArrayList<>()).add(event);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groupedEvents;
    }
    
    /**
     * 指定した期間内のイベントを取得します。<br>
     * このメソッドは、ユーザーID・開始日・終了日をもとに、
     * その期間に属するすべてのイベントを日付・時間順に取得します。<br>
     * 取得対象は論理削除されていないイベントのみです。
     *
     * <p>主に月間カレンダー表示などの機能で利用されます。</p>
     *
     * <p>使用例：</p>
     * <pre>{@code
     * EventsDAO dao = new EventsDAO();
     * LocalDate start = LocalDate.of(2025, 10, 1);
     * LocalDate end = LocalDate.of(2025, 10, 31);
     * List<Event> monthEvents = dao.findBetweenDates("user01@example.com", start, end);
     * monthEvents.forEach(e -> System.out.println(e.getTitle()));
     * }</pre>
     *
     * @param userId  ユーザーID（メールアドレスなど）
     * @param start   検索開始日（含む）
     * @param end     検索終了日（含む）
     * @return 指定期間内の {@link Event} リスト（存在しない場合は空リスト）
     */
    public List<Event> findBetweenDates(String userId, LocalDate start, LocalDate end) {
        List<Event> eventList = new ArrayList<>();

        String sql = """
            SELECT event_id, title, date, time, description, color_id, duration_minutes
            FROM events
            WHERE user_id = ?
              AND date BETWEEN ? AND ?
              AND delete_flag = 0
            ORDER BY date, time
        """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setDate(2, java.sql.Date.valueOf(start));
            pstmt.setDate(3, java.sql.Date.valueOf(end));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Event e = new Event();
                    e.setEvent_id(rs.getInt("event_id"));
                    e.setTitle(rs.getString("title"));
                    e.setDate(rs.getDate("date").toLocalDate());

                    // ← ここを修正！
                    LocalTime time = rs.getTime("time").toLocalTime();
                    e.setStartHour(time.getHour());
                    e.setStartMinute(time.getMinute());

                    e.setDurationMinutes(rs.getInt("duration_minutes"));
                    e.setDescription(rs.getString("description"));
                    e.setColor_id(rs.getString("color_id"));
                    eventList.add(e);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventList;
    }


}
