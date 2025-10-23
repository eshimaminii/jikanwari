<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>曜日変更｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
<style>
.weekday-select {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(90px, 1fr));
	gap: 8px 10px;
	justify-items: start;
	margin-top: 10px;
	margin-bottom: 20px;
}
.weekday-select label {
	display: flex;
	align-items: center;
	gap: 4px;
	white-space: nowrap;
}
</style>
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<div class="container">
		<h1>曜日の変更</h1>

		<p>予定名：${event.title}</p>

		<form action="WeeklyEventEditServlet" method="post">
			<input type="hidden" name="action" value="confirm">
			<input type="hidden" name="event_id" value="${event.event_id}">

			<div class="weekday-select">
				<c:forEach var="weekday" items="${weekdayList}">
					<label>
						<input type="checkbox" name="weekday_ids"
							value="${weekday.weekday_id}"
							<c:if test="${not empty selectedWeekdays and weekday.weekday_id in selectedWeekdays}">checked</c:if>>
						${weekday.weekday}
					</label>
				</c:forEach>
			</div>

			<p style="text-align:center;">
				<button type="submit" class="image-button">確認</button>
			</p>
		</form>
	</div>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
