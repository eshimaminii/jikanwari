<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メインメニュー｜私の時間割</title>
<style>
body {
	margin: 0;
	padding-top: 80px;
	padding-bottom: 80px;
	font-family: 'Arial', sans-serif;
	display: flex;
	justify-content: center;
	align-items: flex-start;
	min-height: calc(100vh - 160px);
	overflow-x: hidden;
}

.site-header {
	background-color: #000;
	height: 80px;
	width: 100%;
	position: fixed;
	top: 0;
	left: 0;
	display: flex;
	justify-content: flex-start;
	align-items: center;
	padding: 0 15px;
	box-sizing: border-box;
}

.header-container {
	color: white;
	font-size: 28px;
	font-weight: bold;
	text-align: center;
	position: absolute;
	left: 50%;
	transform: translateX(-50%);
}

.header-button {
	margin-left: auto;
	display: flex;
	align-items: center;
	height: 60px;
	font-weight: bold;
	text-decoration: none;
	cursor: pointer;
	padding: 0 10px;
	color: white;
	font-size: 20px;
}

.header-button .button-icon {
	width: 30px;
	height: 30px;
	margin-right: 8px;
}

.container {
	background-color: white;
	width: 100%;
	max-width: 500px;
	margin: 40px 20px;
	padding: 30px 20px;
	border-radius: 10px;
	text-align: center;
}

h1 {
	font-size: 28px;
	margin-bottom: 30px;
}

.image-button {
	display: block;
	width: 100%;
	max-width: 340px;
	height: 60px;
	margin: 0 auto 20px;
	background-image: url('../images/button_s.png');
	background-size: contain;
	background-repeat: no-repeat;
	background-position: center;
	border: none;
	cursor: pointer;
	color: white;
	font-size: 20px;
	font-weight: bold;
	text-align: center;
	line-height: 60px;
	text-decoration: none;
	border-radius: 8px;
	transition: transform 0.1s ease, filter 0.1s ease;
}

.image-button:active {
	transform: scale(0.95);
	filter: brightness(85%);
}
</style>
</head>
<body>

	<!-- 右ボタンのみのヘッダー -->
	<header class="site-header">
		<div class="header-container">私の時間割</div>

		<a href="<%=request.getContextPath()%>/LogoutServlet"
			class="header-button" role="button"> <img
			src="<%=request.getContextPath()%>/images/logout.png"
			class="button-icon" alt="ログアウト">
		</a>
	</header>

	<div class="container">
		<!-- ページ内容ここから -->
		<p>本文</p>
	</div>

</body>
</html>
