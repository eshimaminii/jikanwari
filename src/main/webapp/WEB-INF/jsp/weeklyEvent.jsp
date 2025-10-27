<%-- ==========================================================
     曜日指定の予定一覧（weeklyEvent.jsp）
     ・WeeklyEventServlet（GET）から表示されるページ
     ・繰り返し（曜日指定）イベントを曜日別に一覧表示
     ・ラジオボタンで1件選択 → WeeklyEventEditServlet に送信
     ・空の場合は「繰り返し設定なし」のメッセージを表示
     ・ログイン後用ヘッダー（header.jsp）と共通フッター使用
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>曜日指定の予定一覧｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">

<style>
/* ===== 表示用スタイル ===== */
.weekday-block {
	margin-bottom: 25px;
	text-align: left;
}

.weekday-title {
	font-weight: bold;
	font-size: 18px;
	margin-top: 15px;
	margin-bottom: 8px;
}

.event-item {
	margin-left: 15px;
}
</style>
</head>

<body>
	<%-- ログイン後用ヘッダー --%>
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<h1>
			曜日指定の<br>予定一覧
		</h1>
		<p>1件選択してください</p>

		<!-- データが空の場合 -->
		<c:if test="${empty groupedEvents}">
			<p>繰り返し設定された予定はありません。</p>
		</c:if>

		<!-- データがある場合 -->
		<c:if test="${not empty groupedEvents}">
			<form action="WeeklyEventEditServlet" method="post">
				<input type="hidden" name="action" value="edit">

				<!-- 曜日ごとにイベントをグループ表示 -->
				<c:forEach var="weekday" items="${groupedEvents}">
					<div class="weekday-block">
						<div class="weekday-title">
							<!-- 全曜日に設定されている場合は「毎日」と表示 -->
							<c:choose>
								<c:when test="${fn:length(groupedEvents) == 7}">
									毎日
								</c:when>
								<c:otherwise>
									${weekday.key}
								</c:otherwise>
							</c:choose>
						</div>

						<!-- 各曜日のイベント一覧をラジオボタンで表示 -->
						<c:forEach var="event" items="${weekday.value}">
							<div class="event-item">
								<label>
									<input type="radio" name="event_id"
										value="${event.event_id}" required>
									${event.title}
								</label>
							</div>
						</c:forEach>
					</div>
				</c:forEach>

				<!-- 編集ボタン -->
				<p style="margin-top: 20px; text-align: center;">
					<button type="submit" class="image-button">変更する</button>
				</p>
			</form>
		</c:if>
	</div>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>
