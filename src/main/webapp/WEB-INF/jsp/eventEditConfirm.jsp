<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定編集確認｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">

</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<h1>編集内容の確認</h1>
	<p>タイトル：${event.title}</p>
	<p>日付：${event.date}</p>
	<p>開始時：${event.startHour}時 ${event.startMinute}分</p>
	<p>継続時間：${event.durationMinutes}分</p>
	<p>メモ：${event.description}</p>
	<p>繰り返し：${event.repeat_flag ? 'あり' : 'なし'}</p>
	<c:if test="${event.repeat_flag}">
		<p>
			繰り返し曜日：
			<c:forEach var="weekday" items="${weekdayList}">
				<c:if
					test="${event.weekdayIds != null && event.weekdayIds.contains(weekday.weekday_id)}">
        ${weekday.weekday}
      </c:if>
			</c:forEach>
		</p>
	</c:if>
	<p>カラー：${event.color_id}</p>

	<form action="EventEditServlet" method="post">
		<input type="hidden" name="action" value="submit" /> <input
			type="hidden" name="event_id" value="${event.event_id}" /> <input
			type="hidden" name="title" value="${event.title}" /> <input
			type="hidden" name="date" value="${event.date}" /> <input
			type="hidden" name="startHour" value="${event.startHour}" /> <input
			type="hidden" name="startMinute" value="${event.startMinute}" /> <input
			type="hidden" name="durationMinutes" value="${event.durationMinutes}" />
		<input type="hidden" name="description" value="${event.description}" />
		<input type="hidden" name="repeat_flag" value="${event.repeat_flag}" />
		<input type="hidden" name="color_id" value="${event.color_id}" />
		<button type="submit">登録する</button>
	</form>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>