<%-- ==========================================================
     曜日変更完了画面（weeklyEventComplete.jsp）
     ・WeeklyEventEditServlet（POST：submit）後にフォワードされる完了画面
     ・曜日設定変更の結果をユーザーに伝える
     ・「一覧に戻る」ボタンで WeeklyEventServlet に戻る導線を提供
     ・ログイン後ヘッダー（header.jsp）と共通フッターを使用
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>曜日変更完了｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>
	<%-- ログイン後用ヘッダーの読み込み --%>
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<h1>変更が完了しました</h1>
		<p>曜日設定を更新しました。</p>

		<!-- 一覧へ戻るボタン -->
		<a href="<%=request.getContextPath()%>/WeeklyEventServlet"
		   class="image-button-m"
		   role="button">一覧に戻る</a>
	</div>

	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
