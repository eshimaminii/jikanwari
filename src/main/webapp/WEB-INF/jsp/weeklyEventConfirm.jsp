<%-- ==========================================================
     曜日変更確認画面（weeklyEventConfirm.jsp）
     ・WeeklyEventEditServlet（POST：confirm）からフォワードされるページ
     ・ユーザーが選択した曜日を一覧で確認し、更新前に再確認させる
     ・「戻る」ボタンでフォームへ戻り、「登録」で更新処理へ送信
     ・ログイン後ヘッダー（header.jsp）と共通フッターを使用
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>曜日変更確認｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>
	<%-- ログイン後用ヘッダーを読み込み --%>
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<h1>曜日変更の確認</h1>
		<p>下記の曜日で登録を更新します。</p>

		<!-- 選択された曜日リストを表示 -->
		<ul style="list-style:none; padding:0;">
			<c:forEach var="w" items="${weekdayList}">
				<li>${w.weekday}</li>
			</c:forEach>
		</ul>

		<!-- 更新確定フォーム -->
		<form action="WeeklyEventEditServlet" method="post">
			<input type="hidden" name="action" value="submit">

			<!-- ボタンエリア -->
			<p style="margin-top:20px;">
				<button type="button" class="image-button" onclick="history.back()">戻る</button>
				<button type="submit" class="image-button">登録</button>
			</p>
		</form>
	</div>

	<%-- 共通フッターを読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
