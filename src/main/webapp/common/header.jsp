<%-- ==========================================================
     共通ヘッダー（header.jsp）
     ・ログイン後の画面に表示されるヘッダー
     ・ホームボタン（左）とログアウトボタン（右）を配置
     ・中央にアプリタイトル「私の時間割」を表示
     ・全ログイン後ページで共通利用される
   ========================================================== --%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<header class="site-header">
  <%-- 🔹 左：メインメニューへ戻るボタン --%>
  <a href="<%=request.getContextPath()%>/MainServlet" class="header-button" role="button">
    <img src="<%=request.getContextPath()%>/images/home.png" class="button-icon" alt="メイン">
  </a>

  <%-- 🔸 中央：アプリタイトル --%>
  <div class="header-container">
    私の時間割
  </div>

  <%-- 🔹 右：ログアウトボタン --%>
  <a href="<%=request.getContextPath()%>/LogoutServlet" class="header-button" role="button">
    <img src="<%=request.getContextPath()%>/images/logout.png" class="button-icon" alt="ログアウト">
  </a>
</header>
