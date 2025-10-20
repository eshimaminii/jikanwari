<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録確認｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css">
</head>
<body>
	<%@ include file="/common/header_plain.jsp"%>

	<div class="container">
		<h1>新規登録内容確認</h1>

        <%
            // セッションから前ページで入力した値を取得
            Map<String, String> userData = (Map<String, String>) session.getAttribute("User");
            if (userData == null) {
                userData = new java.util.HashMap<>();
                userData.put("email", "");
                userData.put("password", "");
                userData.put("name", "");
                userData.put("birthday", "");
            }
        %>

        <div class="confirm-item">
            <strong>メールアドレス:</strong> <%= userData.get("email") %>
        </div>
        <div class="confirm-item">
            <strong>パスワード:</strong> ******* <!-- セキュリティのため伏字 -->
        </div>
        <div class="confirm-item">
            <strong>ニックネーム:</strong> <%= userData.get("name") %>
        </div>
        <div class="confirm-item">
            <strong>誕生日:</strong> <%= userData.get("birthday") %>
        </div>

		<div class="action-btns" style="margin-top: 30px;">
			<!-- 戻るボタン -->
			<button type="button" class="image-button" role="button"
				onclick="window.history.back();">戻る</button>

			<!-- 登録ボタン -->
			<form method="post" action="NewUserServlet" style="display:inline;">
				<input type="hidden" name="action" value="submit">
				<button type="submit" class="image-button" role="button">登録</button>
			</form>
		</div>
	</div>

	<%@ include file="/common/footer.jsp"%>
</body>
</html>

