<%-- ==========================================================
     予定削除確認画面（eventDeleteConfirm.jsp）
     ・EventDeleteServlet（POST, action="confirm"）からフォワードされるページ
     ・削除対象の予定内容を表示し、ユーザーに最終確認を求める
     ・「削除する」選択でDB更新処理（delete_flag=1）へ進む
     ・「キャンセル」で前画面に戻る
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定削除確認｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>

	<%-- 共通ヘッダーの読み込み --%>
	<%@ include file="/common/header.jsp" %>

	<div class="container">
  <h1>削除の確認</h1>

  <div class="confirm-card">
    <p class="delete-warning">⚠️ 以下の予定を削除します。<br>本当によろしいですか？</p>

    <table class="confirm-table">
      <tr><th>タイトル</th><td>${event.title}</td></tr>
      <tr><th>日付</th><td>${event.date}</td></tr>
      <tr><th>開始時刻</th><td>${event.startHour}時 ${event.startMinute}分</td></tr>
      <tr><th>メモ</th><td>${event.description}</td></tr>
    </table>

    <form action="EventDeleteServlet" method="post">
      <input type="hidden" name="action" value="submit">
      <input type="hidden" name="event_id" value="${event.event_id}">

      <div class="delete-actions">
        <button type="submit" class="image-button-p">削除する</button>
        <button type="button" class="image-button" onclick="history.back()">キャンセル</button>
      </div>
    </form>
  </div>
</div>

	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp" %>
</body>
</html>
