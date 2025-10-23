<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定削除確認｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>

<%@ include file="/common/header.jsp" %>

<div class="container">
    <h1>削除の確認</h1>

    <p>以下の予定を削除します。よろしいですか？</p>

    <table border="1" style="margin:auto; border-collapse:collapse; width:80%;">
        <tr><th>タイトル</th><td>${event.title}</td></tr>
        <tr><th>日付</th><td>${event.date}</td></tr>
        <tr><th>開始時刻</th><td>${event.startHour}時 ${event.startMinute}分</td></tr>
        <tr><th>メモ</th><td>${event.description}</td></tr>
    </table>

    <form action="EventDeleteServlet" method="post" style="text-align:center; margin-top:30px;">
        <input type="hidden" name="action" value="submit">
        <input type="hidden" name="event_id" value="${event.event_id}">
        <button type="submit" class="image-button-p">削除する</button>
        <button type="button" class="image-button" onclick="history.back()">キャンセル</button>
    </form>
</div>

<%@ include file="/common/footer.jsp" %>
</body>
</html>
