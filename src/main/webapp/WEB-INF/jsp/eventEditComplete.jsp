<%-- ==========================================================
     予定編集完了画面（eventEditComplete.jsp）
     ・EventEditServlet（POST, action="submit"）からフォワードされる完了ページ
     ・編集処理が成功したことをユーザーに通知する
     ・メインメニューへ戻る導線を提供
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定編集完了｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>
	<%-- 共通ヘッダーの読み込み --%>
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<h1>編集が完了しました</h1>

		<!-- メインメニューへの戻りボタン -->
		<nav>
			<a href="<%=request.getContextPath()%>/MainServlet"
				class="image-button-l" role="button">メインメニュー</a>
		</nav>
	</div>

	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
