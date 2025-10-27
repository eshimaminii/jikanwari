package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Weekday;

/**
 * {@code WeekdayDAO} クラスは、weekdays テーブルに対する
 * データアクセス操作を行うDAOクラスです。<br>
 * 曜日データの一覧取得および個別取得を担当します。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>全曜日データの取得（{@link #findAll()}）</li>
 *   <li>曜日IDによる検索（{@link #findById(int)}）</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * WeekdayDAO dao = new WeekdayDAO();
 * List<Weekday> weekdays = dao.findAll();
 * weekdays.forEach(w -> System.out.println(w.getWeekday()));
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class WeekdayDAO {

    /**
     * weekdays テーブルの全レコードを取得します。<br>
     * 各レコードを {@link Weekday} オブジェクトにマッピングして返します。
     *
     * @return 全ての曜日データのリスト（存在しない場合は空のリスト）
     */
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

    /**
     * 指定した曜日IDに対応する曜日データを取得します。
     *
     * @param weekdayId 検索対象の曜日ID
     * @return 該当する {@link Weekday} オブジェクト（存在しない場合は {@code null}）
     */
    public Weekday findById(int weekdayId) {
        Weekday weekday = null;
        try (Connection conn = DBManager.getConnection()) {
            String sql = "SELECT weekday_id, weekday FROM weekdays WHERE weekday_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, weekdayId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        weekday = new Weekday();
                        weekday.setWeekday_id(rs.getInt("weekday_id"));
                        weekday.setWeekday(rs.getString("weekday"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weekday;
    }
}
