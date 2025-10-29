<%-- ==========================================================
     予定入力画面（eventAddForm.jsp）
     ・EventAddServlet（GET）から呼び出される新規登録フォーム
     ・ユーザーが予定タイトル・日付・時間・カラーなどを入力する画面
     ・入力後、「確認」ボタンで EventAddServlet（POST, action="confirm"）へ送信
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定入力｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">

<style>
/* ==========================================================
   曜日選択チェックボックスのレイアウト
   ========================================================== */
.weekday-select {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(90px, 1fr));
	/* 自動で整列するグリッド */
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

	<%-- 共通ヘッダーの読み込み --%>
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<!-- 予定登録フォーム -->
		<form action="EventAddServlet" method="post">
			<input type="hidden" name="action" value="confirm">

			<h1>予定の入力</h1>

			<div class="form-card">

				<div class="form-row">
					<label>タイトル：</label> <input type="text" name="title" required />
				</div>

				<div class="form-row">
					<label>日付：</label> <input type="date" name="date" required />
				</div>

				<div class="form-row">
					<label>開始時刻：</label> <input type="number" name="startHour" min="0"
						max="23" required style="width: 80px;"> 時 <input
						type="number" name="startMinute" min="0" max="59" required
						style="width: 80px;"> 分
				</div>

				<div class="form-row">
					<label>継続時間：</label> <input type="number" name="durationMinutes"
						value="60" min="1" style="width: 120px;"> 分
				</div>

				<div class="form-row">
					<label>メモ：</label>
					<textarea name="description"></textarea>
				</div>

				<div class="form-row">
					<label>繰り返し：</label>
					<div>
						<label><input type="radio" name="repeat_flag" value="0"
							checked />なし</label> <label><input type="radio"
							name="repeat_flag" value="1" />あり</label>
					</div>
				</div>

				<div class="form-row">
					<label>曜日：</label>
					<div class="weekday-select">
						<c:forEach var="weekday" items="${weekdayList}">
							<label> <input type="checkbox" name="weekday_ids"
								value="${weekday.weekday_id}" /> ${weekday.weekday}
							</label>
						</c:forEach>
					</div>
				</div>

				<div class="form-row">
					<label>カラー：</label> <select name="color_id">
						<c:forEach var="color" items="${colorList}">
							<option value="${color.color_id}">${color.color}</option>
						</c:forEach>
					</select>
				</div>

				<div class="form-actions">
					<button type="submit" class="image-button" role="button">確認</button>
				</div>

			</div>
		</form>

	</div>

	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp"%>

</body>
</html>
