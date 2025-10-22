package service;

import dao.UsersDAO;
import model.User;

public class UserLoginService {
	 public User execute(User loginUser) {
	        UsersDAO dao = new UsersDAO();
	        return dao.login(loginUser);
	    }


}
