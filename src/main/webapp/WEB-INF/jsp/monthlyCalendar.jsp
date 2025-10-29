<%-- ==========================================================
     月間カレンダー画面（monthlyCalendar.jsp）
     ・MonthlyCalendarServlet（GET）から呼び出される月間表示ページ
     ・1か月分の予定を日ごとに表示し、色付きドットでイベントを可視化
     ・日付をクリックするとモーダルでその日の予定一覧を表示
     ・「前の月」「次の月」ボタンで前後の月に移動可能
   ========================================================== --%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>月間カレンダー｜私の時間割</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/calendar.css">

<script>
/* ==========================================================
   モーダル表示制御スクリプト
   ========================================================== */
	function showEvents(date) {
		const modal = document.getElementById('eventModal');
		const content = document.getElementById('modalContent');
		content.innerHTML = document.getElementById('events-' + date).innerHTML;
		modal.style.display = 'flex';
	}

	function closeModal() {
		document.getElementById('eventModal').style.display = 'none';
	}
</script>
</head>

<body>

	<%-- ==========================================================
	     共通ヘッダー読み込み
	     ========================================================== --%>
	<%@ include file="/common/header.jsp"%>

	<%-- ==========================================================
	     メインエリア
	     ========================================================== --%>
	<div class="main-content">
		<h1>📅 月間カレンダー</h1>

		<%-- ===== 月タイトル＆移動ボタン ===== --%>
		<div class="calendar-header">
			<a
				href="<%=request.getContextPath()%>/MonthlyCalendarServlet?year=${targetMonth.minusMonths(1).year}&month=${targetMonth.minusMonths(1).monthValue}"
				class="image-button-y" role="button">前の月</a>

			<div class="month-title">${monthTitle}</div>

			<a
				href="<%=request.getContextPath()%>/MonthlyCalendarServlet?year=${targetMonth.plusMonths(1).year}&month=${targetMonth.plusMonths(1).monthValue}"
				class="image-button-y" role="button">次の月</a>
		</div>

		<%-- ===== 曜日ヘッダー ===== --%>
		<div class="weekday-header">
			<div class="weekday sun">日</div>
			<div class="weekday">月</div>
			<div class="weekday">火</div>
			<div class="weekday">水</div>
			<div class="weekday">木</div>
			<div class="weekday">金</div>
			<div class="weekday sat">土</div>
		</div>

		<%-- ==========================================================
		     カレンダー本体
		     JSTLで日付ループを生成し、各日にイベントドットを表示
		   ========================================================== --%>
		<div class="calendar-container">
			<%-- JSTLでは曜日を直接扱えないため、週開始位置を調整 --%>
			<c:set var="firstDayOfWeek" value="${targetMonth.dayOfWeek.value}" />
			<%-- Javaの日曜=7を日曜スタートに変換 --%>
			<c:if test="${firstDayOfWeek == 7}">
				<c:set var="firstDayOfWeek" value="0" />
			</c:if>

			<%-- 空白セルを曜日ぶん追加 --%>
			<c:forEach var="i" begin="0" end="${firstDayOfWeek - 1}">
				<div class="calendar-cell empty"></div>
			</c:forEach>

			<%-- 実際の日付セルを描画 --%>
			<c:forEach var="day" begin="1" end="${targetMonth.lengthOfMonth()}">
				<c:set var="dateStr" value="${targetMonth.withDayOfMonth(day)}" />
				<div class="calendar-cell" onclick="showEvents('${dateStr}')"
					style="${dateStr eq today ? 'background-color:#fff3cd;' : ''}">
					
					<%-- 日付ラベル --%>
					<div class="date-label">${day}</div>

					<%-- イベントがある日は複数ドットを描画 --%>
					<div class="dots">
						<c:forEach var="event" items="${monthEvents}">
							<c:if test="${event.date eq dateStr}">
								<span class="dot" style="background-color:${event.color_id};"></span>
							</c:if>
						</c:forEach>
					</div>

					<%-- モーダル表示用イベントリスト（非表示） --%>
					<div id="events-${dateStr}" style="display: none;">
						<ul>
							<c:forEach var="event" items="${monthEvents}">
								<c:if test="${event.date eq dateStr}">
									<li>${event.title}( 
										<fmt:formatNumber value="${event.startHour}" pattern="00" />:
										<fmt:formatNumber value="${event.startMinute}" pattern="00" /> )
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<%-- ==========================================================
	     モーダルウィンドウ（クリックで当日予定を表示）
	     ========================================================== --%>
	<div id="eventModal" class="modal">
		<div class="modal-content" id="modalContent"></div>
		<button class="image-button-y modal-close-btn" onclick="closeModal()">閉じる</button>
	</div>

	<%-- ==========================================================
	     共通フッター読み込み
	     ========================================================== --%>
	<%@ include file="/common/footer.jsp"%>

</body>
</html>
