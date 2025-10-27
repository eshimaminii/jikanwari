<%-- ==========================================================
     ログアウト完了画面（logout.jsp）
     ・LogoutServlet（GET）によってセッションが無効化された後に表示
     ・ログアウト処理の完了をユーザーへ通知
     ・再ログインや新規登録のため、トップページ（WelcomeServlet）への導線を提供
     ・ログイン前専用のヘッダー（header_plain.jsp）を使用
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト完了｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>
	<%-- ログイン前用ヘッダーを読み込み --%>
	<%@ include file="/common/header_plain.jsp"%>

	<div class="container">
		<h1>ログアウトしました</h1>

		<!-- トップページへの導線 -->
		<nav>
			<a href="<%=request.getContextPath()%>/WelcomeServlet"
				class="image-button" role="button">トップ</a>
		</nav>
	</div>

	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
