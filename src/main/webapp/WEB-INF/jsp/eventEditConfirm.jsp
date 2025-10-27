<%-- ==========================================================
     予定編集確認画面（eventEditConfirm.jsp）
     ・EventEditServlet（POST, action="confirm"）からフォワードされるページ
     ・ユーザーが入力・変更した内容を一覧で確認する
     ・「登録」ボタン押下で EventEditServlet（action="submit"）へ再送信し更新処理を実行
     ・「戻る」ボタンでフォームに戻り、修正可能
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定編集確認｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>

	<%-- 共通ヘッダーの読み込み --%>
	<%@ include file="/common/header.jsp" %>

	<div class="container">
		<h1>編集内容の確認</h1>

		<%-- 編集対象イベント情報を取得 --%>
		<c:set var="event" value="${event}" />

		<!-- 編集後の内容を確認用に表示 -->
		<table class="confirm-table" border="1" style="margin:auto; width:80%; border-collapse:collapse;">
			<tr><th>タイトル</th><td>${event.title}</td></tr>
			<tr><th>日付</th><td>${event.date}</td></tr>
			<tr><th>開始時刻</th><td>${event.startHour}時 ${event.startMinute}分</td></tr>
			<tr><th>継続時間</th><td>${event.durationMinutes}分</td></tr>
			<tr><th>メモ</th><td>${event.description}</td></tr>

			<tr>
				<th>繰り返し</th>
				<td>
					<c:choose>
						<c:when test="${event.repeat_flag}">あり</c:when>
						<c:otherwise>なし</c:otherwise>
					</c:choose>
				</td>
			</tr>

			<%-- 繰り返しONの場合のみ曜日一覧を表示 --%>
			<c:if test="${event.repeat_flag}">
				<tr>
					<th>繰り返し曜日</th>
					<td>
						<c:forEach var="weekday" items="${weekdayList}">
							<c:if test="${event.weekdayIds != null && event.weekdayIds.contains(weekday.weekday_id)}">
								${weekday.weekday}&nbsp;
							</c:if>
						</c:forEach>
					</td>
				</tr>
			</c:if>

			<tr>
				<th>カラー</th>
				<td>
					<div style="display:flex;align-items:center;gap:10px;">
						<div style="width:20px;height:20px;background-color:${event.color_id};border:1px solid #999;"></div>
						<span>${event.color_id}</span>
					</div>
				</td>
			</tr>
		</table>

		<!-- 編集確定フォーム -->
		<form method="post" action="<%=request.getContextPath()%>/EventEditServlet">
			<input type="hidden" name="action" value="submit">
			<input type="hidden" name="event_id" value="${event.event_id}">
			<input type="hidden" name="title" value="${event.title}">
			<input type="hidden" name="date" value="${event.date}">
			<input type="hidden" name="startHour" value="${event.startHour}">
			<input type="hidden" name="startMinute" value="${event.startMinute}">
			<input type="hidden" name="durationMinutes" value="${event.durationMinutes}">
			<input type="hidden" name="description" value="${event.description}">
			<input type="hidden" name="repeat_flag" value="${event.repeat_flag ? 1 : 0}">
			<input type="hidden" name="color_id" value="${event.color_id}">
			
			<%-- 繰り返し設定ありの場合、選択された曜日IDをhiddenで送信 --%>
			<c:forEach var="weekdayId" items="${event.weekdayIds}">
				<input type="hidden" name="weekday_ids" value="${weekdayId}">
			</c:forEach>

			<!-- ボタンエリア -->
			<div class="button-area" style="text-align:center; margin-top:30px;">
				<button type="button" class="image-button" onclick="history.back()">戻る</button>
				<button type="submit" class="image-button">登録</button>
			</div>
		</form>
	</div>

	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp" %>

</body>
</html>
