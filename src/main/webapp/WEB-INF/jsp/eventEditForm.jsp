<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定編集｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
<style>
.weekday-select {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(90px, 1fr));
	/* 👈これがポイント */
	gap: 8px 10px;
	justify-items: start; /* 左揃え */
	margin-top: 8px;
	margin-bottom: 10px;
}

.weekday-select label {
	display: flex;
	align-items: center;
	gap: 4px;
	white-space: nowrap;
	font-size: 0.95em;
}
</style>

</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<div class="container">

		<!-- 編集確認フォーム -->
		<form action="EventEditServlet" method="post" style="display: inline;">

			<input type="hidden" name="action" value="confirm"> <input
				type="hidden" name="event_id" value="${event.event_id}">

			<h1>予定の編集</h1>

			<p>
				タイトル：<input type="text" name="title" value="${event.title}" required />
			</p>

			<p>
				日付：<input type="date" name="date" value="${event.date}" required />
			</p>

			<p>
				開始時： <input type="number" name="startHour"
					value="${event.startHour}" min="0" max="23" required /> 時 <input
					type="number" name="startMinute" value="${event.startMinute}"
					min="0" max="59" required /> 分
			</p>

			<p>
				継続時間（分）：<input type="number" name="durationMinutes"
					value="${event.durationMinutes}" min="1" />
			</p>

			<p>
				メモ：
				<textarea name="description" rows="4" cols="30">${event.description}</textarea>
			</p>

			<p>
				<span style="font-weight: bold;">繰り返し：</span> <label><input
					type="radio" name="repeat_flag" value="0" checked />なし</label> <label><input
					type="radio" name="repeat_flag" value="1" />あり</label>
			</p>

			<div class="weekday-select">
				<c:forEach var="weekday" items="${weekdayList}">
					<label> <input type="checkbox" name="weekday_ids"
						value="${weekday.weekday_id}" /> ${weekday.weekday}
					</label>
				</c:forEach>
			</div>


			<p>
				カラー： <select name="color_id">
					<c:forEach var="color" items="${colorList}">
						<option value="${color.color_id}"
							<c:if test="${color.color_id == event.color_id}">selected</c:if>>${color.color}</option>
					</c:forEach>
				</select>
			</p>

			<div class="button-area"
				style="text-align: center; margin-top: 30px;">

				<input type="hidden" name="action" value="confirm"> <input
					type="hidden" name="event_id" value="${event.event_id}">
				<button type="submit" class="image-button">確認</button>
		</form>

		<!-- 削除フォーム -->
		<form action="EventDeleteServlet" method="post"
			style="display: inline;">
			<input type="hidden" name="action" value="confirm"> <input
				type="hidden" name="event_id" value="${event.event_id}">
			<button type="submit" class="image-button-p">削除</button>
		</form>
	</div>

	</div>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
