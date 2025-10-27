package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Color;

/**
 * {@code ColorDAO} クラスは、colors テーブルへのデータアクセスを担当するDAOクラスです。<br>
 * このクラスでは、データベースから色データを取得する処理を提供します。
 *
 * <p>主な責務：</p>
 * <ul>
 *   <li>全ての色データの取得（{@link #findAll()}）</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * ColorDAO dao = new ColorDAO();
 * List<Color> colors = dao.findAll();
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class ColorDAO {

    /**
     * colors テーブル内の全レコードを取得します。<br>
     * 各レコードを {@link Color} オブジェクトにマッピングし、リストとして返します。
     *
     * @return 全ての色データのリスト（取得できなかった場合は空のリストを返す）
     */
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
