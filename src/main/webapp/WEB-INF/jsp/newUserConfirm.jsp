<%-- ==========================================================
     新規登録確認画面（newUserConfirm.jsp）
     ・NewUserServlet（POST：action未指定時）からフォワードされて表示
     ・ユーザーが入力した新規登録情報を確認する画面
     ・登録確定（submit）または修正（戻る）を選択可能
     ・hidden要素で入力値を再送信可能に保持
     ・ログイン前用ヘッダー（header_plain.jsp）を利用
   ========================================================== --%>

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
	<%-- ログイン前用ヘッダー --%>
	<%@ include file="/common/header_plain.jsp"%>

	<div class="container">
		<h1>入力内容の確認</h1>

		<!-- 入力内容を表示 -->
		<form method="post" action="<%=request.getContextPath()%>/NewUserServlet">
			<p>メールアドレス：<%=request.getAttribute("email")%></p>
			<p>パスワード：******</p> <%-- パスワードは伏せて表示 --%>
			<p>ニックネーム：<%=request.getAttribute("name")%></p>
			<p>誕生日：<%=request.getAttribute("birthday")%></p>

			<!-- hiddenでデータを再送信用に保持 -->
			<input type="hidden" name="email" value="<%=request.getAttribute("email")%>">
			<input type="hidden" name="password" value="<%=request.getAttribute("password")%>">
			<input type="hidden" name="name" value="<%=request.getAttribute("name")%>">
			<input type="hidden" name="birthday" value="<%=request.getAttribute("birthday")%>">

			<!-- ボタン操作領域 -->
			<div class="button-area">
				<!-- 戻るボタン（修正用：入力フォームへ戻る） -->
				<button type="button" class="image-button" onclick="history.back()">修正</button>

				<!-- 登録確定ボタン -->
				<button type="submit" name="action" value="submit" class="image-button-p">登録</button>
			</div>
		</form>
	</div>

	<%-- 共通フッター --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
