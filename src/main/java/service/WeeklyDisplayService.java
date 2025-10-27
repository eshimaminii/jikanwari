package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.EventsDAO;
import model.Event;

/**
 * {@code WeeklyDisplayService} クラスは、
 * ユーザーの「週単位スケジュール表示」に関するビジネスロジックを担当するサービスクラスです。<br>
 * DAO層（{@link dao.EventsDAO}）を利用して、繰り返し予定を曜日ごとに整理・分類します。
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>繰り返しイベント一覧の取得（{@link #getRepeatedEvents(String)}）</li>
 *   <li>曜日別にグルーピングされた繰り返しイベント一覧の生成（{@link #getGroupedRepeatedEvents(String)}）</li>
 *   <li>7曜日すべてに属するイベントを「毎日」グループとして抽出</li>
 * </ul>
 *
 * <p>使用例：</p>
 * <pre>{@code
 * WeeklyDisplayService service = new WeeklyDisplayService();
 * Map<String, List<Event>> grouped = service.getGroupedRepeatedEvents("user01@example.com");
 *
 * grouped.forEach((day, events) -> {
 *     System.out.println("◆ " + day);
 *     events.forEach(e -> System.out.println(" - " + e.getTitle()));
 * });
 * }</pre>
 *
 * @author 
 * @version 1.0
 */
public class WeeklyDisplayService {

    /**
     * 繰り返し設定されているイベントをすべて取得します。<br>
     * DAO層の {@link dao.EventsDAO#findRepeatedEvents(String)} を呼び出して取得します。
     *
     * @param userId ログイン中のユーザーID
     * @return 繰り返し予定の {@link Event} 一覧（存在しない場合は空リスト）
     */
    public List<Event> getRepeatedEvents(String userId) {
        EventsDAO dao = new EventsDAO();
        return dao.findRepeatedEvents(userId);
    }

    /**
     * 繰り返し予定を「曜日別」に分類して取得します。<br>
     * 各曜日に属するイベントを整理し、全7曜日に登録されているイベントを「毎日」グループとして抽出します。
     *
     * <p>処理の流れ：</p>
     * <ol>
     *   <li>{@link dao.EventsDAO#getGroupedEvents(String)} により、曜日ごとのイベント一覧を取得</li>
     *   <li>イベントIDごとに登場回数をカウント</li>
     *   <li>全曜日に出現したイベントを「毎日」グループとして分類</li>
     *   <li>残りの曜日別イベントをそれぞれのグループに整理</li>
     * </ol>
     *
     * @param userId ログイン中のユーザーID
     * @return 曜日名をキー、対応する {@link Event} リストを値に持つマップ（「毎日」グループを含む）
     */
    public Map<String, List<Event>> getGroupedRepeatedEvents(String userId) {
        Map<String, List<Event>> grouped = new LinkedHashMap<>();
        EventsDAO dao = new EventsDAO();

        // DAOから曜日別イベントマップを取得
        Map<String, List<Event>> original = dao.getGroupedEvents(userId);

        // イベントごとの登場回数をカウント
        Map<Integer, Integer> eventCount = new HashMap<>();
        for (List<Event> list : original.values()) {
            for (Event e : list) {
                eventCount.put(e.getEvent_id(), eventCount.getOrDefault(e.getEvent_id(), 0) + 1);
            }
        }

        // 「毎日」グループ作成用リスト
        List<Event> everydayEvents = new ArrayList<>();

        // 曜日ごとのイベントを走査
        for (Map.Entry<String, List<Event>> entry : original.entrySet()) {
            String weekday = entry.getKey();
            List<Event> filtered = new ArrayList<>();

            for (Event e : entry.getValue()) {
                if (eventCount.get(e.getEvent_id()) == 7) {
                    // 7曜日すべてに存在する場合 → 「毎日」へ（重複登録防止）
                    boolean alreadyExists = everydayEvents.stream()
                            .anyMatch(ev -> ev.getEvent_id() == e.getEvent_id());
                    if (!alreadyExists) {
                        everydayEvents.add(e);
                    }
                } else {
                    filtered.add(e);
                }
            }

            // 曜日ごとのリストに追加
            if (!filtered.isEmpty()) {
                grouped.put(weekday, filtered);
            }
        }

        // 最後に「毎日」グループを先頭に追加
        if (!everydayEvents.isEmpty()) {
            grouped.put("毎日", everydayEvents);
        }

        return grouped;
    }
}
