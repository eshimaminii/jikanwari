<%-- ==========================================================
     新規登録フォーム（newUserForm.jsp）
     ・NewUserServlet（GET）から表示されるユーザー登録フォーム
     ・入力内容はPOST送信で確認画面（newUserConfirm.jsp）へ
     ・HTML5の入力制約属性を活用したバリデーション付き
     ・ログイン前ヘッダー（header_plain.jsp）と共通フッターを使用
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>
	<%-- ログイン前用ヘッダー --%>
	<%@ include file="/common/header_plain.jsp"%>

	<div class="container">
		<h1>新規登録</h1>

		<!-- 入力フォーム -->
		<form method="post" action="NewUserServlet">
			<!-- サーブレット側で「確認画面へ」分岐するためのパラメータ -->
			<input type="hidden" name="action" value="confirm">

			<!-- メールアドレス入力 -->
			<div class="form-label">
				<label for="email">メールアドレス</label>
				<input type="email" id="email" name="email" required>
			</div>

			<!-- パスワード入力 -->
			<div class="form-label">
				<label for="password">パスワード ※6文字以上</label>
				<input type="password" id="password" name="password" required minlength="6">
				<%-- HTMLのバリデーションを活用（最低6文字） --%>
			</div>

			<!-- ニックネーム入力 -->
			<div class="form-label">
				<label for="name">ニックネーム</label>
				<input type="text" id="name" name="name" required maxlength="20">
			</div>

			<!-- 誕生日入力 -->
			<div class="form-label">
				<label for="birthday">誕生日</label>
				<input type="date" id="birthday" name="birthday" required>
			</div>

			<!-- ボタン操作エリア -->
			<div class="button-area">
				<!-- 確認画面へ -->
				<button type="submit" class="image-button" role="button">確認</button>

				<!-- 戻る（トップページへ） -->
				<button type="button" class="image-button" role="button"
					onclick="window.history.back();">戻る</button>
			</div>
		</form>
	</div>

	<%-- 共通フッターの読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
