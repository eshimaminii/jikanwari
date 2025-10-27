package service;

import java.time.LocalDate;
import java.util.List;

import dao.EventsDAO;
import model.Event;

/**
 * {@code ScheduleDisplayService} クラスは、
 * 指定された日付におけるユーザーのスケジュール（イベント）を取得する
 * ビジネスロジックを担当するサービスクラスです。<br>
 * DAO層（{@link dao.EventsDAO}）を利用して、日付別のイベント一覧を取得します。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>指定ユーザー・指定日付のイベント一覧取得</li>
 *   <li>単発イベント・繰り返しイベント両対応</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * ScheduleDisplayService service = new ScheduleDisplayService();
 * List<Event> events = service.getTodayEvents("user01@example.com", LocalDate.now());
 * events.forEach(e -> System.out.println(e.getTitle()));
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class ScheduleDisplayService {

    /**
     * 指定されたユーザーIDと日付に基づいて、当日のイベント一覧を取得します。<br>
     * このメソッドは {@link dao.EventsDAO#findByDate(String, LocalDate)} を呼び出します。
     *
     * @param userId ユーザーID
     * @param date   対象日付
     * @return 指定日付の {@link Event} リスト（存在しない場合は空リスト）
     */
    public List<Event> getTodayEvents(String userId, LocalDate date) {
        EventsDAO dao = new EventsDAO();
        return dao.findByDate(userId, date);
    }
}
