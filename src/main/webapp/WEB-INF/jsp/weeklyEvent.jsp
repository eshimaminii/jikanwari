<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>曜日指定の予定一覧｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
<style>
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
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<h1>曜日指定の<br>予定一覧</h1>
		<p>1件選択してください</p>

		<c:if test="${empty groupedEvents}">
			<p>繰り返し設定された予定はありません。</p>
		</c:if>

		<c:if test="${not empty groupedEvents}">
			<form action="WeeklyEventEditServlet" method="post">
				<input type="hidden" name="action" value="edit">

				<!-- 曜日ごとにグループ表示 -->
				<c:forEach var="weekday" items="${groupedEvents}">
					<div class="weekday-block">
						<div class="weekday-title">${weekday.key}</div>
						<c:forEach var="event" items="${weekday.value}">
							<div class="event-item">
								<label>
									<input type="radio" name="event_id" value="${event.event_id}" required>
									${event.title}
								</label>
							</div>
						</c:forEach>
					</div>
				</c:forEach>

				<p style="margin-top:20px; text-align:center;">
					<button type="submit" class="image-button">変更する</button>
				</p>
			</form>
		</c:if>
	</div>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>
