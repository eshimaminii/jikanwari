<%-- ==========================================================
     曜日変更フォーム画面（weeklyEventForm.jsp）
     ・WeeklyEventEditServlet（POST：edit）からフォワードされるページ
     ・対象イベントの「繰り返し曜日」を編集するフォーム
     ・チェック済みの曜日は JSTL の条件分岐で checked 表示
     ・「確認」ボタン押下で WeeklyEventEditServlet（action=confirm）へ送信
     ・共通ヘッダー／フッターを利用
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>曜日変更｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">

<style>
/* ===== チェックボックスレイアウト（レスポンシブ対応） ===== */
.weekday-select {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(90px, 1fr));
	gap: 8px 10px;
	justify-items: start;
	margin-top: 10px;
	margin-bottom: 20px;
}

/* ===== チェックボックスラベル整形 ===== */
.weekday-select label {
	display: flex;
	align-items: center;
	gap: 4px;
	white-space: nowrap;
}
</style>
</head>

<body>
	<%-- ログイン後ヘッダー読み込み --%>
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<h1>曜日の変更</h1>

		<!-- 編集対象イベント名 -->
		<p>予定名：${event.title}</p>

		<!-- 繰り返し曜日編集フォーム -->
		<form action="WeeklyEventEditServlet" method="post">
			<input type="hidden" name="action" value="confirm">
			<input type="hidden" name="event_id" value="${event.event_id}">

			<!-- 曜日チェックボックス群 -->
			<div class="weekday-select">
				<c:forEach var="weekday" items="${weekdayList}">
					<label>
						<input type="checkbox" name="weekday_ids"
							value="${weekday.weekday_id}"
							<%-- 選択済み曜日は checked 表示 --%>
							<c:forEach var="id" items="${selectedWeekdays}">
								<c:if test="${id == weekday.weekday_id}">checked</c:if>
							</c:forEach>>
						${weekday.weekday}
					</label>
				</c:forEach>
			</div>

			<!-- 確認ボタン -->
			<p style="text-align: center;">
				<button type="submit" class="image-button">確認</button>
			</p>
		</form>
	</div>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>
