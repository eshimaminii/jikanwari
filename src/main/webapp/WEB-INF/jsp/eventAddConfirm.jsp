<%-- ==========================================================
     予定登録確認画面（eventAddConfirm.jsp）
     ・EventAddServlet（action="confirm"）からフォワードされるページ
     ・入力内容を一覧表示し、ユーザーに登録内容を確認させる
     ・「修正」ボタンで前画面に戻り、「登録」でDB登録処理へ進む
   ========================================================== --%>

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

	<%-- 共通ヘッダーの読み込み --%>
	<%@ include file="/common/header.jsp" %>

	<div class="container">
  <h1>入力内容の確認</h1>

  <%-- セッションスコープから Event オブジェクトを取得 --%>
  <c:set var="event" value="${sessionScope.event}" />

  <div class="confirm-card">

    <table class="confirm-table">
      <tr>
        <th>タイトル</th>
        <td>${event.title}</td>
      </tr>
      <tr>
        <th>日付</th>
        <td>${event.date}</td>
      </tr>
      <tr>
        <th>開始時刻</th>
        <td>${event.startHour}時 ${event.startMinute}分</td>
      </tr>
      <tr>
        <th>継続時間</th>
        <td>${event.durationMinutes} 分</td>
      </tr>
      <tr>
        <th>メモ</th>
        <td>${event.description}</td>
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
          <div class="color-preview">
            <div class="color-swatch" style="background-color:${event.color_id};"></div>
            <span>${event.color_name}</span>
          </div>
        </td>
      </tr>
    </table>

    <form method="post" action="<%=request.getContextPath()%>/EventAddServlet">
      <input type="hidden" name="action" value="submit">
      <input type="hidden" name="title" value="${event.title}">
      <input type="hidden" name="date" value="${event.date}">
      <input type="hidden" name="startHour" value="${event.startHour}">
      <input type="hidden" name="startMinute" value="${event.startMinute}">
      <input type="hidden" name="durationMinutes" value="${event.durationMinutes}">
      <input type="hidden" name="description" value="${event.description}">
      <input type="hidden" name="repeat_flag" value="${event.repeat_flag ? 1 : 0}">
      <input type="hidden" name="color_id" value="${event.color_id}">

      <div class="confirm-actions">
        <button type="button" class="image-button" onclick="history.back()">修正</button>
        <button type="submit" class="image-button">登録</button>
      </div>
    </form>
  </div>
</div>


	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp" %>

</body>
</html>
