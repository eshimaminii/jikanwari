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
	<%@ include file="/common/header_plain.jsp"%>

	<div class="container">
		<h1>新規登録</h1>

		<form method="post" action="NewUserServlet">
		<input type="hidden" name="action" value="confirm">

			<div class="form-label">
				<label for="email">メールアドレス</label> <input type="email" id="email"
					name="email" required>
			</div>

			<div class="form-label">
				<label for="password">パスワード ※6文字以上</label> <input type="password"
					id="password" name="password" required minlength="6">
				<!-- 最低6文字 -->
			</div>

			<div class="form-label">
				<label for="name">ニックネーム</label> <input type="text" id="name"
					name="name" required maxlength="20">
			</div>

			<div class="form-label">
				<label for="birthday">誕生日</label> <input type="date" id="birthday"
					name="birthday" required>
			</div>

			<div class="button-area">
				<button type="submit" class="image-button" role="button">確認</button>
				<button type="button" class="image-button" role="button"
					onclick="window.history.back();">戻る</button>


			</div>

		</form>
	</div>


	<%@ include file="/common/footer.jsp"%>
</body>
</html>