package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Color;

public class ColorDAO {
	public List<Color> findAll() {
		List<Color> list = new ArrayList<>();
		try (Connection conn = DBManager.getConnection()) {
			String sql = "SELECT color_id, color FROM colors";
			try (PreparedStatement stmt = conn.prepareStatement(sql);
				 ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Color c = new Color();
					c.setColor_id(rs.getString("color_id"));
					c.setColor(rs.getString("color"));
					list.add(c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
