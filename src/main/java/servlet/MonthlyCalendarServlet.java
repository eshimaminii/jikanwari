package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Event;
import model.User;
import service.ScheduleDisplayService;

/**
 * {@code MonthlyCalendarServlet} クラスは、
 * 月間カレンダー画面の表示処理を管理するサーブレットです。<br>
 * ログインユーザーの指定月の予定を取得し、月間ビューに表示します。
 *
 * <p>主な処理の流れ：</p>
 * <ol>
 *   <li><b>GET</b>：指定された年月または当月の予定一覧を取得</li>
 *   <li>予定データを {@link model.Event} のリストとして取得</li>
 *   <li>JSP（{@code monthlyCalendar.jsp}）に転送してカレンダーを表示</li>
 * </ol>
 *
 * <p>使用JSP：</p>
 * <ul>
 *   <li>{@code /WEB-INF/jsp/monthlyCalendar.jsp}</li>
 * </ul>
 *
 * <p>関連クラス：</p>
 * <ul>
 *   <li>{@link service.ScheduleDisplayService}</li>
 *   <li>{@link model.Event}</li>
 *   <li>{@link model.User}</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
@WebServlet("/MonthlyCalendarServlet")
public class MonthlyCalendarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * GETリクエストを処理します。<br>
     * ログイン中のユーザーを確認し、指定された年月の予定を取得して
     * 月間カレンダー画面を表示します。
     *
     * @param request  クライアントからのHTTPリクエスト
     * @param response サーバーからのHTTPレスポンス
     * @throws ServletException サーブレットエラー発生時
     * @throws IOException      入出力エラー発生時
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- セッションからログインユーザー情報を取得 ---
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // ログインしていない場合はログイン画面へリダイレクト
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        // --- 表示する年月を取得（パラメータ指定がなければ当月） ---
        String monthParam = request.getParameter("month");
        String yearParam = request.getParameter("year");

        LocalDate targetMonth;
        if (monthParam != null && yearParam != null) {
            targetMonth = LocalDate.of(
                Integer.parseInt(yearParam),
                Integer.parseInt(monthParam),
                1
            );
        } else {
            targetMonth = LocalDate.now().withDayOfMonth(1);
        }

        // --- 表示範囲（1日〜末日）を算出 ---
        LocalDate start = targetMonth.withDayOfMonth(1);
        LocalDate end = targetMonth.withDayOfMonth(targetMonth.lengthOfMonth());

        // --- 指定月のイベントを取得 ---
        ScheduleDisplayService service = new ScheduleDisplayService();
        List<Event> monthEvents = service.getEventsBetween(loginUser.getUserId(), start, end);

        // --- LocalDate → 表示用タイトルに変換（例：2025年 10月） ---
        String monthTitle = targetMonth.format(DateTimeFormatter.ofPattern("yyyy年 M月"));

        // --- JSPへ渡すデータをセット ---
        request.setAttribute("targetMonth", targetMonth);
        request.setAttribute("monthTitle", monthTitle);
        request.setAttribute("monthEvents", monthEvents);
        request.setAttribute("today", LocalDate.now());

        // --- 月間カレンダー画面へフォワード ---
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/monthlyCalendar.jsp");
        dispatcher.forward(request, response);
    }
}
