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
	<jsp:useBean id="now" class="java.util.Date" />

	<header class="site-header">
		<div class="header-container">私の時間割</div>
		<a href="<%=request.getContextPath()%>/LogoutServlet"
			class="header-button" role="button">
			<img src="<%=request.getContextPath()%>/images/logout.png"
				class="button-icon" alt="ログアウト">
		</a>
	</header>

	<div class="container">

		<a href="<%=request.getContextPath()%>/EventAddServlet"
			class="image-button-l" role="button">予定を入れる</a>
		<a href="<%=request.getContextPath()%>/WeeklyEventServlet"
			class="image-button-l" role="button">曜日指定の予定一覧</a>

		<div class="date-display">
			<fmt:formatDate value="${now}" pattern="M月d日 (E)" />
		</div>

		<div class="schedule-table">
			<div class="schedule-content">
				<!-- 7:00〜23:00まで -->
				<c:forEach begin="7" end="23" var="hour">
					<div class="time-row">
						<div class="time-label">${hour}</div>
						<div class="time-line"></div>
						<div class="half-line"></div>
					</div>
				</c:forEach>

				<!-- イベントブロック -->
				<c:set var="pxPerMinute" value="1" />
				<c:forEach var="event" items="${todayEvents}">
					<c:set var="offsetMinutes"
						value="${(event.startHour - 7) * 60 + event.startMinute}" />

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

	<%@ include file="/common/footer.jsp"%>
</body>
</html>
