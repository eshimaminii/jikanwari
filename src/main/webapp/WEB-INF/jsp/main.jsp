<%-- ==========================================================
     メインメニュー画面（main.jsp）
     ・MainServlet（GET）によって呼び出されるログイン後のメインページ
     ・当日の日付とユーザーの予定を時刻別に一覧表示
     ・予定は「Event」オブジェクトのリスト（todayEvents）として表示
     ・時間軸を基準に縦方向へレイアウト（7:00〜23:00）
     ・JSTLとEL式でデータをバインドし、CSSでビジュアルを制御
   ========================================================== --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メインメニュー｜私の時間割</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/mainMenu.css">
</head>

<body>
	<%-- 現在時刻を取得するBean（JSTLのfmtで日付表示に使用） --%>
	<jsp:useBean id="now" class="java.util.Date" />

	<%-- ===== ヘッダーエリア ===== --%>
	<header class="site-header">
		<div class="header-container">私の時間割</div>
		<!-- ログアウトボタン（右上） -->
		<a href="<%=request.getContextPath()%>/LogoutServlet"
			class="header-button" role="button">
			<img src="<%=request.getContextPath()%>/images/logout.png"
				class="button-icon" alt="ログアウト">
		</a>
	</header>

	<%-- ===== メインコンテンツエリア ===== --%>
	<div class="container">

		<!-- 予定登録・曜日指定ページへの導線 -->
		<a href="<%=request.getContextPath()%>/EventAddServlet"
			class="image-button-l" role="button">予定を入れる</a>
		<a href="<%=request.getContextPath()%>/WeeklyEventServlet"
			class="image-button-l" role="button">曜日指定の予定一覧</a>

		<!-- 今日の日付表示（例：10月27日 (月)） -->
		<div class="date-display">
			<fmt:formatDate value="${now}" pattern="M月d日 (E)" />
		</div>

		<!-- スケジュール表（7:00〜23:00まで1時間ごと） -->
		<div class="schedule-table">
			<div class="schedule-content">

				<!-- 時間ラベルと線の描画 -->
				<c:forEach begin="7" end="23" var="hour">
					<div class="time-row">
						<div class="time-label">${hour}</div>
						<div class="time-line"></div>
						<div class="half-line"></div>
					</div>
				</c:forEach>

				<!-- 登録済みイベントを時間位置に応じて配置 -->
				<c:set var="pxPerMinute" value="1" /> <!-- 1分＝1pxのスケール -->
				<c:forEach var="event" items="${todayEvents}">
					<c:set var="offsetMinutes"
						value="${(event.startHour - 7) * 60 + event.startMinute}" />

					<!-- イベントブロック（クリックで編集ページへ） -->
					<a href="<%=request.getContextPath()%>/EventEditServlet?event_id=${event.event_id}"
						class="event-block"
						style="top: ${offsetMinutes * pxPerMinute}px;
						       height: ${event.durationMinutes * pxPerMinute}px;
						       background-color: ${event.color_id};">
						<div class="event-title">${event.title}</div>
					</a>
				</c:forEach>
			</div>
		</div>

	</div>

	<%-- 共通フッター読み込み --%>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>
