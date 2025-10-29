<%-- ==========================================================
     ログイン完了画面（loginComplete.jsp）
     ・LoginServlet（POST）で認証成功後にフォワードされるページ
     ・セッションスコープの loginUser オブジェクトから名前を表示
     ・ログイン成功後、メインメニュー（MainServlet）へ遷移可能
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン完了｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>
	<%-- 共通ヘッダーの読み込み（ログイン後バージョン） --%>
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<h1>ログイン完了</h1>

		<!-- セッションから取得したユーザー名を歓迎メッセージとして表示 -->
		 <p class="welcome-text">ようこそ</p>
		<h1>${loginUser.name}さん</h1>

		<!-- メインメニューへの導線 -->
		<nav>
			<a href="<%=request.getContextPath()%>/MainServlet"
				class="image-button-m" role="button">メインメニュー</a>
		</nav>
	</div>

	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
