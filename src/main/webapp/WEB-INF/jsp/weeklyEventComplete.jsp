<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>曜日変更完了｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<h1>変更が完了しました</h1>

		<p>曜日設定を更新しました。</p>

		<a href="<%=request.getContextPath()%>/WeeklyEventServlet" class="image-button-m" role="button">一覧に戻る</a>
	</div>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>
