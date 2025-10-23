<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>曜日変更確認｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<h1>曜日変更の確認</h1>

		<p>下記の曜日で登録を更新します。</p>

		<ul style="list-style:none; padding:0;">
			<c:forEach var="w" items="${weekdayList}">
				<li>${w.weekday}</li>
			</c:forEach>
		</ul>

		<form action="WeeklyEventEditServlet" method="post">
			<input type="hidden" name="action" value="submit">
			<p style="margin-top:20px;">
				<button type="submit" class="image-button">完了</button>
			</p>
		</form>
	</div>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>
