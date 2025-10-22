<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定確認｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>

<%@ include file="/common/header.jsp" %>

<div class="container">
    <h1>入力内容確認</h1>

    <c:set var="event" value="${sessionScope.event}" />

    <table class="confirm-table" border="1" style="margin:auto; width:80%; border-collapse:collapse;">
        <tr>
            <th style="width:30%;">タイトル</th>
            <td>${event.title}</td>
        </tr>
        <tr>
            <th>日付</th>
            <td>${event.date}</td>
        </tr>
        <tr>
            <th>開始時刻</th>
            <td>
                <c:out value="${event.startHour}" />時 
                <c:out value="${event.startMinute}" />分
            </td>
        </tr>
        <tr>
            <th>継続時間</th>
            <td><c:out value="${event.durationMinutes}" /> 分</td>
        </tr>
        <tr>
            <th>メモ</th>
            <td><c:out value="${event.description}" /></td>
        </tr>
        <tr>
            <th>繰り返し</th>
            <td>
                <c:choose>
                    <c:when test="${event.repeat_flag}">あり</c:when>
                    <c:otherwise>なし</c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <th>カラー</th>
            <td>
                <div style="display:flex;align-items:center;gap:10px;">
                    <div style="width:20px;height:20px;background-color:${event.color_id};border:1px solid #999;"></div>
                    <span>${event.color_id}</span>
                </div>
            </td>
        </tr>
    </table>

    <form action="EventAddServlet" method="post" style="margin-top:20px;">
        <input type="hidden" name="action" value="submit">
        <div style="text-align:center; margin-top:30px;">
            <button type="submit">この内容で登録する</button>
        </div>
    </form>

    <div style="text-align:center; margin-top:15px;">
        <form action="EventAddServlet" method="get">
            <button type="submit">修正する</button>
        </form>
    </div>

</div>

<%@ include file="/common/footer.jsp" %>

</body>
</html>
