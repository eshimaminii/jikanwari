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
	<%@ include file="/common/header.jsp"%>
<div class="container">
		<h1>ログイン完了</h1>
		<h1>ようこそ</h1>
		<h1>${loginUser.name}さん</h1>
		<nav>
			<a href="<%=request.getContextPath()%>/MainServlet"
				class="image-button-m" role="button">メインメニュー</a>
		</nav>
</div>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>