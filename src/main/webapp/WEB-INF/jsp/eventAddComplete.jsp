<%-- ==============================================
     予定入力完了画面（eventAddComplete.jsp）
     ・EventAddServlet の登録処理完了後にフォワードされるページ
     ・ユーザーに登録成功を知らせ、メインメニューへ戻る導線を提供する
   ============================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定入力完了｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>
	<%-- 共通ヘッダーの読み込み --%>
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<h1>登録完了</h1>

		<!-- ナビゲーションエリア -->
		<nav>
			<a href="<%=request.getContextPath()%>/MainServlet"
				class="image-button-l" role="button">メインメニュー</a>
		</nav>
	</div>

	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
