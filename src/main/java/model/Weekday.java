package model;

/**
 * {@code Weekday} クラスは、weekdays テーブルの1レコードを表すモデルクラスです。<br>
 * 各曜日のIDと名称を保持します。
 *
 * <p>主な用途：</p>
 * <ul>
 *   <li>{@link dao.WeekdayDAO} による曜日データの取得</li>
 *   <li>繰り返し予定登録時の曜日選択処理</li>
 *   <li>画面表示（例：チェックボックスやドロップダウン）用データの保持</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * Weekday w = new Weekday();
 * w.setWeekday_id(2);
 * w.setWeekday("月曜日");
 * System.out.println(w.getWeekday()); // → 月曜日
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class Weekday {

    /** 曜日ID（1=日曜, 2=月曜, ... 7=土曜） */
    private int weekday_id;

    /** 曜日名（例："日曜日", "月曜日" など） */
    private String weekday;

    /** 曜日IDを取得します。 */
    public int getWeekday_id() { return weekday_id; }

    /** 曜日IDを設定します。 */
    public void setWeekday_id(int weekday_id) { this.weekday_id = weekday_id; }

    /** 曜日名を取得します。 */
    public String getWeekday() { return weekday; }

    /** 曜日名を設定します。 */
    public void setWeekday(String weekday) { this.weekday = weekday; }
}
