package model;

import java.time.LocalDate;
import java.util.List;

/**
 * {@code Event} クラスは、events テーブルの1レコードを表すモデルクラスです。<br>
 * 各イベント（予定）の基本情報、開始時刻、継続時間、繰り返し設定などを保持します。
 *
 * <p>主な用途：</p>
 * <ul>
 *   <li>{@link dao.EventsDAO} によるDB入出力データの保持</li>
 *   <li>画面上での日付別・週別スケジュール表示</li>
 *   <li>イベント編集・削除処理時のデータ転送</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * Event e = new Event();
 * e.setTitle("会議");
 * e.setDate(LocalDate.of(2025, 10, 27));
 * e.setStartHour(14);
 * e.setStartMinute(30);
 * e.setDurationMinutes(60);
 * System.out.println(e.getTitle() + " 開始時刻: " + e.getStartHour() + ":" + e.getStartMinute());
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class Event {

    /** イベントID（主キー） */
    private int event_id;

    /** イベントのタイトル（例：「打ち合わせ」「買い物」など） */
    private String title;

    /** イベントの日付 */
    private LocalDate date;

    /** イベントのメモ・説明文 */
    private String description;

    /** 繰り返しフラグ（true: 繰り返しあり） */
    private boolean repeat_flag;

    /** カラーID（表示色の指定） */
    private String color_id;
    
    /** カラーの名前 */
    private String color_name;

    /** 論理削除フラグ（true: 削除済み） */
    private boolean delete_flag;

    /** 開始時刻（時） */
    private int startHour;

    /** 開始時刻（分） */
    private int startMinute;

    /** 継続時間（分） */
    private int durationMinutes;

    /** 登録ユーザーID（メールアドレス） */
    private String user_id;

    /** 曜日IDリスト（繰り返しイベントで使用） */
    private List<Integer> weekdayIds;

    /** デフォルトコンストラクタ */
    public Event() {}

    /**
     * イベント情報をまとめて初期化するコンストラクタ。
     *
     * @param event_id     イベントID
     * @param title        タイトル
     * @param date         日付
     * @param description  説明文
     * @param repeat_flag  繰り返しフラグ
     * @param color_id     カラーID
     * @param delete_flag  論理削除フラグ
     */
    public Event(int event_id, String title, LocalDate date, String description, boolean repeat_flag, String color_id,
                 boolean delete_flag) {
        this.event_id = event_id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.repeat_flag = repeat_flag;
        this.color_id = color_id;
        this.delete_flag = delete_flag;
    }

    public int getEvent_id() { return event_id; }
    public void setEvent_id(int event_id) { this.event_id = event_id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isRepeat_flag() { return repeat_flag; }
    public void setRepeat_flag(boolean repeat_flag) { this.repeat_flag = repeat_flag; }

    public boolean isDelete_flag() { return delete_flag; }
    public void setDelete_flag(boolean delete_flag) { this.delete_flag = delete_flag; }

    public int getStartHour() { return startHour; }
    public void setStartHour(int startHour) { this.startHour = startHour; }

    public int getStartMinute() { return startMinute; }
    public void setStartMinute(int startMinute) { this.startMinute = startMinute; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getColor_id() { return color_id; }
    public void setColor_id(String color_id) { this.color_id = color_id; }

    public List<Integer> getWeekdayIds() { return weekdayIds; }
    public void setWeekdayIds(List<Integer> weekdayIds) { this.weekdayIds = weekdayIds; }

    public String getUser_id() { return user_id; }
    public void setUser_id(String user_id) { this.user_id = user_id; }

    public String getColor_name() { return color_name; }
    public void setColor_name(String color_name) { this.color_name = color_name; }

}
