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

public class EventsDAO {

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
	              e.date = ?                           -- 通常の単発予定
	              OR (e.repeat_flag = 1 AND w.weekday_id = ?)  -- 曜日繰り返し予定
	          )
	        ORDER BY e.time;
	    """;

	    try (Connection conn = DBManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, userId);
	        pstmt.setDate(2, java.sql.Date.valueOf(date));

	        // 今日の曜日（1=日曜, 7=土曜）
	        int dayOfWeek = date.getDayOfWeek().getValue() + 1;
	        if (dayOfWeek == 8) dayOfWeek = 1; // Javaは月曜=1だから補正
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

	
	public Integer insert(Event event) {
	    String sql = """
	        INSERT INTO events
	        (title, date, time, description, repeat_flag, color_id, delete_flag, user_id)
	        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
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
	        pstmt.setInt(7, event.isDelete_flag() ? 1 : 0);
	        pstmt.setString(8, event.getUser_id());

	        int result = pstmt.executeUpdate();
	        if (result != 1) return null;

	        try (ResultSet keys = pstmt.getGeneratedKeys()) {
	            if (keys.next()) {
	                return keys.getInt(1);   // ← ここで event_id を返す！
	            }
	        }
	    } catch (SQLException e) {
	    	System.out.println("❌ 曜日登録中にエラー発生！");
	        e.printStackTrace();
	    }
	    return null;
	}


	
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

	        int rows = stmt.executeUpdate();  
	        return rows > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
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
	
	public boolean deleteEvent(int eventId) {
	    boolean result = false;
	    String sql = "UPDATE events SET delete_flag = 1 WHERE event_id = ?";

	    try (Connection conn = DBManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, eventId);
	        int updated = pstmt.executeUpdate();
	        if (updated > 0) result = true;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	// --- 繰り返しイベント一覧取得 ---
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

	                // まだその曜日のリストがない場合は新規作成
	                groupedEvents.computeIfAbsent(weekday, k -> new ArrayList<>()).add(event);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return groupedEvents;
	}


}
