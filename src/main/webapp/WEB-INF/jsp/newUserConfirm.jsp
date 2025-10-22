<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録確認｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>
	<%@ include file="/common/header_plain.jsp"%>

	<div class="container">
		<h1>入力内容の確認</h1>

		<form method="post" action="<%=request.getContextPath()%>/NewUserServlet">
			<p>
				メールアドレス：<%=request.getAttribute("email")%></p>
			<p>
				パスワード：<%=request.getAttribute("password")%></p>
			<p>
				ニックネーム：<%=request.getAttribute("name")%></p>
			<p>
				誕生日：<%=request.getAttribute("birthday")%></p>

			<input type="hidden" name="email"
				value="<%=request.getAttribute("email")%>"> <input
				type="hidden" name="password"
				value="<%=request.getAttribute("password")%>"> <input
				type="hidden" name="name"
				value="<%=request.getAttribute("name")%>"> <input
				type="hidden" name="birthday"
				value="<%=request.getAttribute("birthday")%>">

			<div class="button-area">
				<button type="button" class="image-button" onclick="history.back()">修正</button>
				<button type="submit" name="action" value="submit" class="image-button">登録</button>

			</div>
		</form>
	</div>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>

