package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import service.UserRegistrationService;

@WebServlet("/NewUserServlet")
public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/newUserForm.jsp");
				dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		System.out.println("action = " + action);
		
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String birthday = request.getParameter("birthday");
            
            //  登録処理            
            if ("submit".equals(action)) {

            	boolean result = false;
				try {
					UserRegistrationService service = new UserRegistrationService();
					result = service.registerUser(email, password, name, birthday);

				} catch (Exception e) {
					e.printStackTrace();
				}

                if (result) {
                    // 登録成功 → 完了画面へ
                    request.getRequestDispatcher("/WEB-INF/jsp/newUserComplete.jsp").forward(request, response);
                } else {
                    // 登録失敗 → エラーメッセージを表示して入力画面へ
                    request.setAttribute("error", "ユーザー登録に失敗しました。再度お試しください。");
                    request.getRequestDispatcher("/WEB-INF/jsp/newUserForm.jsp").forward(request, response);
                }
                return;
        }

        //  確認画面表示 
        request.setAttribute("email", email);
        request.setAttribute("password", password);
        request.setAttribute("name", name);
        request.setAttribute("birthday", birthday);

        request.getRequestDispatcher("/WEB-INF/jsp/newUserConfirm.jsp").forward(request, response);
    }

}
