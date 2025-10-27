package model;

/**
 * {@code Color} クラスは、colors テーブルの1レコードを表すモデルクラスです。<br>
 * イベントなどに紐づくカラー情報（色ID・色名）を保持します。
 *
 * <p>主な用途：</p>
 * <ul>
 *   <li>{@link dao.ColorDAO} によるデータ取得時のデータ保持</li>
 *   <li>画面上での色指定やプルダウン表示用データとしての利用</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * Color c = new Color();
 * c.setColor_id("C01");
 * c.setColor("#FFAA00");
 * System.out.println(c.getColor()); // → #FFAA00
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class Color {
    /** 色のID（例：C01, C02...） */
    private String color_id;

    /** 色のコードまたは名称（例：#FFAA00 や "Red"） */
    private String color;

    /** 色IDを取得します。 */
    public String getColor_id() { return color_id; }

    /** 色IDを設定します。 */
    public void setColor_id(String color_id) { this.color_id = color_id; }

    /** 色の値（コードまたは名称）を取得します。 */
    public String getColor() { return color; }

    /** 色の値（コードまたは名称）を設定します。 */
    public void setColor(String color) { this.color = color; }
}
