package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ColorDAO;
import dao.WeekdayDAO;
import model.Color;
import model.Event;
import model.User;
import model.Weekday;
import service.EventService;

/**
 * {@code EventAddServlet} クラスは、
 * 新規イベント登録フォームの表示・確認・登録処理を制御するサーブレットです。<br>
 * カラー選択・曜日設定・繰り返し登録など、予定追加時の一連の操作を担当します。
 *
 * <p>主な処理の流れ：</p>
 * <ol>
 *   <li><b>GET</b>：カラー・曜日リストを取得し、登録フォームを表示</li>
 *   <li><b>POST (confirm)</b>：フォーム入力内容をセッションに保存し、確認画面へ遷移</li>
 *   <li><b>POST (submit)</b>：確認内容をDBへ登録し、完了画面へ遷移</li>
 * </ol>
 *
 * <p>使用JSP：</p>
 * <ul>
 *   <li>{@code /WEB-INF/jsp/eventAddForm.jsp}</li>
 *   <li>{@code /WEB-INF/jsp/eventAddConfirm.jsp}</li>
 *   <li>{@code /WEB-INF/jsp/eventAddComplete.jsp}</li>
 * </ul>
 *
 * <p>使用DAO／Service：</p>
 * <ul>
 *   <li>{@link dao.ColorDAO}</li>
 *   <li>{@link dao.WeekdayDAO}</li>
 *   <li>{@link service.EventService}</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/EventAddServlet")
public class EventAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GETリクエスト処理。<br>
     * カラー・曜日リストをデータベースから取得し、登録フォームへ転送します。
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレットエラー発生時
     * @throws IOException      入出力エラー発生時
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // カラー・曜日リストをDBから取得
            ColorDAO colorDao = new ColorDAO();
            WeekdayDAO weekdayDao = new WeekdayDAO();

            List<Color> colorList = colorDao.findAll();
            List<Weekday> weekdayList = weekdayDao.findAll();
            request.setAttribute("durationMinutes", 60);


            // JSPへ渡す
            request.setAttribute("colorList", colorList);
            request.setAttribute("weekdayList", weekdayList);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "初期データの取得に失敗しました。");
        }

        // 入力フォームへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/eventAddForm.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * POSTリクエスト処理。<br>
     * actionパラメータに応じて、<b>確認画面</b>または<b>登録処理</b>を実行します。
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレットエラー発生時
     * @throws IOException      入出力エラー発生時
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        System.out.println("action = " + action);

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // ログインチェック
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        /* ---------- 確認画面処理 ---------- */
        if ("confirm".equals(action)) {
            try {
                // 入力値取得
                String title = request.getParameter("title");
                String dateStr = request.getParameter("date");
                String hourStr = request.getParameter("startHour");
                String minuteStr = request.getParameter("startMinute");
                String durationStr = request.getParameter("durationMinutes");
                System.out.println("durationStr = " + durationStr);
                String description = request.getParameter("description");
                String colorId = request.getParameter("color_id");
                String repeatFlagStr = request.getParameter("repeat_flag");

                // 曜日チェックボックス取得
                String[] weekdayIdsStr = request.getParameterValues("weekday_ids");
                List<Integer> weekdayIds = new ArrayList<>();
                if (weekdayIdsStr != null) {
                    for (String idStr : weekdayIdsStr) {
                        weekdayIds.add(Integer.parseInt(idStr));
                    }
                }
                session.setAttribute("weekdayIds", weekdayIds);

                // イベントオブジェクト生成
                Event event = new Event();
                event.setTitle(title);
                event.setDate(LocalDate.parse(dateStr));
                event.setStartHour(Integer.parseInt(hourStr));
                event.setStartMinute(Integer.parseInt(minuteStr));
                event.setDurationMinutes(Integer.parseInt(durationStr));
                event.setDescription(description);
                event.setColor_id(colorId);
                event.setRepeat_flag("1".equals(repeatFlagStr));
                event.setDelete_flag(false);

                // セッションに保存
                session.setAttribute("event", event);

                // 確認画面へフォワード
                RequestDispatcher dispatcher =
                        request.getRequestDispatcher("/WEB-INF/jsp/eventAddConfirm.jsp");
                dispatcher.forward(request, response);
                return;

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "入力内容に誤りがあります。");
                doGet(request, response); // 再表示
                return;
            }
        }

        /* ---------- 登録処理 ---------- */
        if ("submit".equals(action)) {
            Event savedEvent = (Event) session.getAttribute("event");
            @SuppressWarnings("unchecked")
            List<Integer> weekdayIds = (List<Integer>) session.getAttribute("weekdayIds");

            if (savedEvent == null) {
                request.setAttribute("error", "セッションが切れました。再度入力してください。");
                doGet(request, response);
                return;
            }

            boolean result = false;
            try {
                EventService service = new EventService();
                result = service.addEvent(savedEvent, loginUser.getUserId(), weekdayIds);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (result) {
                // セッション後片付け
                session.removeAttribute("event");
                session.removeAttribute("weekdayIds");

                // 完了画面へ
                request.getRequestDispatcher("/WEB-INF/jsp/eventAddComplete.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "登録に失敗しました。");
                doGet(request, response);
            }
        }
    }
}
