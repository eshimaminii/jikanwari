<%-- ==========================================================
     ログイン画面（login.jsp）
     ・LoginServlet（POST）へユーザー認証情報を送信するフォーム画面
     ・未ログインユーザーがアクセスする入口ページのひとつ
     ・サーバー側では UserLoginService → UsersDAO で認証処理を実行
     ・入力エラー時は同画面にエラーメッセージを再表示
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
</head>

<body>
	<%-- ログイン前専用の共通ヘッダー --%>
	<%@ include file="/common/header_plain.jsp"%>

	<div class="container">
		<h1>ログイン</h1>

		<!-- ユーザー認証フォーム -->
		<form method="post" action="<%=request.getContextPath()%>/LoginServlet">

			<!-- メールアドレス入力 -->
			<div class="form-label">
				<label for="email">メールアドレス</label>
				<input type="email" id="email" name="email" required>
			</div>

			<!-- パスワード入力 -->
			<div class="form-label">
				<label for="password">パスワード ※6文字以上</label>
				<input type="password" id="password" name="password" required minlength="6">
				<!-- HTML5によるクライアントサイドバリデーション -->
			</div>

			<!-- ボタンエリア -->
			<div class="button-area">
				<button type="submit" class="image-button-p" role="button">ログイン</button>
				<button type="button" class="image-button" role="button"
					onclick="window.history.back();">戻る</button>
			</div>
		</form>
	</div>

	<%-- 共通フッター読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
