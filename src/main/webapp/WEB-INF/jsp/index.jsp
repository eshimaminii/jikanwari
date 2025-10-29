<%-- ==========================================================
     トップページ（index.jsp）
     ・WelcomeServlet（GET）からフォワードされて表示される初期画面
     ・アプリタイトルと主要導線（新規登録／ログイン）を提供する
     ・まだ認証していないユーザーが最初にアクセスするエントリーポイント
     ・共通ヘッダーは plain 版（ログイン前専用）を使用
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>トップ｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>
	<%-- ログイン前用のシンプルな共通ヘッダー読み込み --%>
	<%@ include file="/common/header_plain.jsp"%>

	<div class="container">
		<div class="puni-bg">
			<div class="puni puni-yellow"></div>
			<div class="puni puni-pink"></div>
			<div class="puni puni-purple"></div>
			<div class="puni puni-green"></div>
			<div class="puni puni-blue"></div>
		</div>

		<h1>私の時間割</h1>

		<!-- アカウント導線ナビゲーション -->
		<nav>
			<a href="<%=request.getContextPath()%>/NewUserServlet"
				class="image-button" role="button">新規登録</a> <a
				href="<%=request.getContextPath()%>/LoginServlet"
				class="image-button-p" role="button">ログイン</a>
		</nav>
	</div>

	<%-- 共通フッター読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
