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
import java.util.List;

import model.Event;

public class EventsDAO {

	public List<Event> findByDate(String userId, LocalDate date) {
		List<Event> events = new ArrayList<>();
		String sql = """
				SELECT 
				    event_id, title, date, description, repeat_flag, 
				    color_id, delete_flag, TIME_FORMAT(time, '%H') AS start_hour, 
				    TIME_FORMAT(time, '%i') AS start_minute
				FROM events
				WHERE user_id = ? 
				  AND date = ?
				  AND delete_flag = 0
				ORDER BY time;
				""";

		try (Connection conn = DBManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, userId);
			pstmt.setDate(2, java.sql.Date.valueOf(date));

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

				// 仮で1時間固定（後でカラム追加して変更可）
				e.setDurationMinutes(60);

				events.add(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return events;
	}
	
	public boolean insert(Event event) {
        String sql = """
            INSERT INTO events
            (title, date, time, description, repeat_flag, color_id, delete_flag, user_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // time カラムは startHour と startMinute から生成
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
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	
	public boolean update(Event event) {
		try (Connection conn = DBManager.getConnection()) {
			String sql = "UPDATE events SET title=?, date=?, time=?, description=?, repeat_flag=?, color_id=?, duration_minutes=? WHERE event_id=?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			    LocalTime time = LocalTime.of(event.getStartHour(), event.getStartMinute());
			    stmt.setString(1, event.getTitle());
			    stmt.setDate(2, Date.valueOf(event.getDate()));
			    stmt.setTime(3, Time.valueOf(time));
			    stmt.setString(4, event.getDescription());
			    stmt.setBoolean(5, event.isRepeat_flag());
			    stmt.setString(6, event.getColor_id());
			    stmt.setInt(7, event.getDurationMinutes());
			    stmt.setInt(8, event.getEvent_id());
			    stmt.executeUpdate();

				int rows = stmt.executeUpdate();
				return rows > 0;
			}
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
}
