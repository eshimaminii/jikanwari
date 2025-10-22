<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="site-header">
  <a href="<%=request.getContextPath()%>/MainServlet" class="header-button" role="button">
    <img src="<%=request.getContextPath()%>/images/home.png" class="button-icon" alt="メイン">
  </a>

  <div class="header-container">
    私の時間割
  </div>

  <a href="<%=request.getContextPath()%>/LogoutServlet" class="header-button" role="button">
    <img src="<%=request.getContextPath()%>/images/logout.png" class="button-icon" alt="ログアウト">
  </a>
</header>

