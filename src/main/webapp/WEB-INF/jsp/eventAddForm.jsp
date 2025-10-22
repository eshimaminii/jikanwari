<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定入力｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<form action="EventAddServlet" method="post">
	 <input type="hidden" name="action" value="confirm">
		<h1>予定の入力</h1>

		<p>
			タイトル：<input type="text" name="title" required />
		</p>

		<p>
			日付：<input type="date" name="date" required />
		</p>

		<p>
			開始時：<input type="number" name="startHour" min="0" max="23" required /> 時　
			<input type="number" name="startMinute" min="0" max="59" required /> 分
		</p>

		<p>
			継続時間（分）：<input type="number" name="durationMinutes" value="60" min="1" />
		</p>

		<p>
			メモ：<textarea name="description" rows="4" cols="30"></textarea>
		</p>

		<p>
			<fieldset>
				<legend>繰り返し：</legend>
				<label><input type="radio" name="repeat_flag" value="0" checked />なし</label>
				<label><input type="radio" name="repeat_flag" value="1" />あり</label>

				<!-- 曜日選択は繰り返し「あり」のときのみ表示する仕様にする場合はJSで制御 -->
				<div class="weekday-select" style="margin-top: 5px;">
					<c:forEach var="weekday" items="${weekdayList}">
						<label>
							<input type="checkbox" name="weekday_ids" value="${weekday.weekday_id}" />
							${weekday.weekday}
						</label>
					</c:forEach>
				</div>
			</fieldset>
		</p>

		<p>
			カラー：
			<select name="color_id">
				<c:forEach var="color" items="${colorList}">
					<option value="${color.color_id}">${color.color}</option>
				</c:forEach>
			</select>
		</p>

		<p>
			<button type="submit">登録する</button>
		</p>
	</form>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>
