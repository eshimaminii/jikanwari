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
	<%@ include file="/common/header_plain.jsp"%>
<div class="container">
		<h1>登録完了</h1>
		
		<nav>
			<a href="<%=request.getContextPath()%>/LoginServlet"
				class="image-button-p" role="button">ログイン</a>
			<a href="<%=request.getContextPath()%>/WelcomeServlet"
				class="image-button" role="button">トップ</a>
		</nav>
</div>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>