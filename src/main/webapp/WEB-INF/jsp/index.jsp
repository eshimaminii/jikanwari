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
	<%@ include file="/common/header_plain.jsp"%>
	<div class="container">
		<h1>私の時間割</h1>
		
		<nav>
			<a href="<%=request.getContextPath()%>/NewUserServlet"
				class="image-button" role="button">新規登録</a>
			<a href="<%=request.getContextPath()%>/LoginServlet"
				class="image-button" role="button">ログイン</a>
		</nav>
		
	</div>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
