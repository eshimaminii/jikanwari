package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Weekday;

public class WeekdayDAO {
	public List<Weekday> findAll() {
		List<Weekday> list = new ArrayList<>();
		try (Connection conn = DBManager.getConnection()) {
			String sql = "SELECT weekday_id, weekday FROM weekdays";
			try (PreparedStatement stmt = conn.prepareStatement(sql);
				 ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Weekday w = new Weekday();
					w.setWeekday_id(rs.getInt("weekday_id"));
					w.setWeekday(rs.getString("weekday"));
					list.add(w);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}