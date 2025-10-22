<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メインメニュー｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/mainMenu.css">
</head>

<body>
	<jsp:useBean id="now" class="java.util.Date" />

	<header class="site-header">
		<div class="header-container">私の時間割</div>

		<a href="<%=request.getContextPath()%>/LogoutServlet"
			class="header-button" role="button"> <img
			src="<%=request.getContextPath()%>/images/logout.png"
			class="button-icon" alt="ログアウト">
		</a>
	</header>

	<div class="container">

		<a href="<%=request.getContextPath()%>/EventAddServlet"
			class="image-button-l" role="button">予定を入れる</a> <a
			href="<%=request.getContextPath()%>/WeeklyEventServlet"
			class="image-button-l" role="button">曜日指定の予定一覧</a>

		<div class="date-display">
			<fmt:formatDate value="${now}" pattern="M月d日 (E)" />
		</div>

		<div class="schedule-table">

			<c:forEach begin="7" end="16" var="hour">
				<div class="time-slot hour-start" data-time="${hour}:00">
					<div class="time-label">${hour}</div>
					<div class="time-line"></div>
				</div>

				<c:if test="${hour < 16}">
					<div class="time-slot half-hour" data-time="${hour}:30">
						<div class="time-line half"></div>
					</div>
				</c:if>
			</c:forEach>

			<c:forEach var="event" items="${todayEvents}">
				<c:set var="totalStartMinutes"
					value="${event.startHour * 60 + event.startMinute}" />
				<c:set var="offsetMinutes" value="${totalStartMinutes - 7 * 60}" />

				<div class="event-block"
					style="top: ${offsetMinutes * 2}px; 
						height: ${event.durationMinutes * 2}px; 
						background-color: ${event.colorCode};">

					<div class="event-title">${event.title}</div>
				</div>
			</c:forEach>
		</div>
	</div>


	<%@ include file="/common/footer.jsp"%>
</body>
</html>