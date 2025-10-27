<%-- ==========================================================
     予定編集フォーム画面（eventEditForm.jsp）
     ・EventEditServlet（GET）からフォワードされて表示される編集画面
     ・既存の予定情報（event）をフォームに表示し、編集可能にする
     ・「確認」ボタンで EventEditServlet（POST, action="confirm"）へ送信
     ・同画面内に「削除」ボタンを設置し、削除処理にも遷移可能
   ========================================================== --%>

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
/* --- 曜日選択チェックボックスのレイアウト調整 --- */
.weekday-select {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(90px, 1fr));
	gap: 8px 10px;
	justify-items: start;
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
	<%-- 共通ヘッダー読み込み --%>
	<%@ include file="/common/header.jsp"%>

	<div class="container">

		<!-- 予定編集フォーム -->
		<form action="EventEditServlet" method="post" style="display: inline;">
			<input type="hidden" name="action" value="confirm">
			<input type="hidden" name="event_id" value="${event.event_id}">

			<h1>予定の編集</h1>

			<!-- タイトル入力 -->
			<p>
				タイトル：
				<input type="text" name="title" value="${event.title}" required />
			</p>

			<!-- 日付入力 -->
			<p>
				日付：
				<input type="date" name="date" value="${event.date}" required />
			</p>

			<!-- 開始時刻入力 -->
			<p>
				開始時：
				<input type="number" name="startHour" value="${event.startHour}" min="0" max="23" required /> 時
				<input type="number" name="startMinute" value="${event.startMinute}" min="0" max="59" required /> 分
			</p>

			<!-- 継続時間 -->
			<p>
				継続時間（分）：
				<input type="number" name="durationMinutes" value="${event.durationMinutes}" min="1" />
			</p>

			<!-- メモ入力 -->
			<p>
				メモ：
				<textarea name="description" rows="4" cols="30">${event.description}</textarea>
			</p>

			<!-- 繰り返し設定 -->
			<p>
				<span style="font-weight: bold;">繰り返し：</span>
				<label><input type="radio" name="repeat_flag" value="0" 
					<c:if test="${!event.repeat_flag}">checked</c:if> />なし</label>
				<label><input type="radio" name="repeat_flag" value="1" 
					<c:if test="${event.repeat_flag}">checked</c:if> />あり</label>
			</p>

			<!-- 曜日選択（繰り返しON時のみ利用） -->
			<div class="weekday-select">
				<c:forEach var="weekday" items="${weekdayList}">
					<label>
						<input type="checkbox" name="weekday_ids"
							value="${weekday.weekday_id}"
							<c:if test="${event.weekdayIds != null && event.weekdayIds.contains(weekday.weekday_id)}">checked</c:if> />
						${weekday.weekday}
					</label>
				</c:forEach>
			</div>

			<!-- カラー選択 -->
			<p>
				カラー：
				<select name="color_id">
					<c:forEach var="color" items="${colorList}">
						<option value="${color.color_id}"
							<c:if test="${color.color_id == event.color_id}">selected</c:if>>
							${color.color}
						</option>
					</c:forEach>
				</select>
			</p>

			<!-- ボタンエリア -->
			<div class="button-area" style="text-align: center; margin-top: 30px;">
				<button type="submit" class="image-button">確認</button>
			</div>
		</form>

		<!-- 削除フォーム -->
		<form action="EventDeleteServlet" method="post" style="display: inline;">
			<input type="hidden" name="action" value="confirm">
			<input type="hidden" name="event_id" value="${event.event_id}">
			<button type="submit" class="image-button-p">削除</button>
		</form>
	</div>

	<%-- 共通フッター読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
