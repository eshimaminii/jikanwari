package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;
import service.UserLoginService;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    RequestDispatcher dispatcher =
	        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
	    dispatcher.forward(request, response);
	}

       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    
	    User loginUser = new User(email, password);
	    UserLoginService userLoginService = new UserLoginService();
	    
	    User userFromDB = userLoginService.execute(loginUser); // DBから情報取得

	    if (userFromDB != null) {
	        // セッションスコープに name と birthday を保存
	        request.getSession().setAttribute("loginUser", userFromDB);
	        
	        RequestDispatcher dispatcher =
	            request.getRequestDispatcher("/WEB-INF/jsp/loginComplete.jsp");
	        dispatcher.forward(request, response);
	    } else {
	        request.setAttribute("errorMsg", "メールアドレスまたはパスワードが違います");
	        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	    }
	}


}
