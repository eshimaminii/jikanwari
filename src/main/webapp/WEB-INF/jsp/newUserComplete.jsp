<%-- ==========================================================
     新規登録完了画面（newUserComplete.jsp）
     ・NewUserServlet（POST：action=submit）からフォワードされて表示
     ・ユーザー登録処理が成功したことを明示的に通知
     ・ログイン画面またはトップページへの導線を提供
     ・ログイン前用ヘッダー（header_plain.jsp）を利用
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録完了｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>
	<%-- ログイン前専用ヘッダー（タイトルのみ）を読み込み --%>
	<%@ include file="/common/header_plain.jsp"%>

	<div class="container">
		<h1>登録完了</h1>

		<!-- 登録後の導線ナビゲーション -->
		<nav>
			<!-- 登録直後にログインできるよう導線を配置 -->
			<a href="<%=request.getContextPath()%>/LoginServlet"
				class="image-button-p" role="button">ログイン</a>

			<!-- トップページ（WelcomeServlet）に戻るリンク -->
			<a href="<%=request.getContextPath()%>/WelcomeServlet"
				class="image-button" role="button">トップ</a>
		</nav>
	</div>

	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
