<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定編集｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">

</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<form action="EventEditServlet" method="post">
		<input type="hidden" name="action" value="confirm" /> <input
			type="hidden" name="event_id" value="${event.event_id}" /> タイトル：<input
			type="text" name="title" value="${event.title}" /><br> 日付：<input
			type="date" name="date" value="${event.date}" /><br> 開始時：<input
			type="number" name="startHour" value="${event.startHour}" min="0"
			max="23" /><br> 開始分：<input type="number" name="startMinute"
			value="${event.startMinute}" min="0" max="59" /><br> 継続時間（分）：<input
			type="number" name="durationMinutes" value="${event.durationMinutes}" /><br>
		メモ：
		<textarea name="description">${event.description}</textarea>
		<br> 繰り返し：
		<fieldset>
			<legend>繰り返し：</legend>
			<label><input type="radio" name="repeat_flag" value="0"
				${!event.repeat_flag ? 'checked' : ''} />なし</label> <label><input
				type="radio" name="repeat_flag" value="1"
				${event.repeat_flag ? 'checked' : ''} />あり</label>

			<c:if test="${event.repeat_flag}">
				<div>
					<c:forEach var="weekday" items="${weekdayList}">
						<label> <input type="checkbox" name="weekday_ids"
							value="${weekday.weekday_id}"
							<c:if test="${event.weekdayIds != null && event.weekdayIds.contains(weekday.weekday_id)}">checked</c:if> />
							${weekday.weekday}
						</label>
					</c:forEach>
				</div>
			</c:if>
		</fieldset>
		<br> カラー： <label for="color_id">カラー：</label> <select
			name="color_id" id="color_id">
			<c:forEach var="color" items="${colorList}">
				<option value="${color.color_id}"
					${color.color_id == event.color_id ? 'selected' : ''}>
					${color.color}</option>
			</c:forEach>
		</select><br>

		<button type="submit">確認画面へ</button>
	</form>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>