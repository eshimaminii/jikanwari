<%-- ==========================================================
     予定削除完了画面（eventDeleteComplete.jsp）
     ・EventDeleteServlet（POST, action="submit"）の削除処理完了後に表示されるページ
     ・削除成功をユーザーに伝え、メインメニューへ戻る導線を提供
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予定削除完了｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>
	<%-- 共通ヘッダーの読み込み --%>
	<%@ include file="/common/header.jsp"%>

	<div class="container">
		<h1>削除しました</h1>

		<!-- メニューへの戻りリンク -->
		<nav>
			<a href="<%=request.getContextPath()%>/MainServlet"
				class="image-button-l" role="button">メインメニュー</a>
		</nav>
	</div>

	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
