package service;

import java.sql.SQLException;

import dao.UsersDAO;
import model.User;

public class UserRegistrationService {

    private static UsersDAO usersDAO;

    public UserRegistrationService() {
        usersDAO = new UsersDAO();
    }

    // 新規登録処理
    public boolean registerUser(String userId, String password, String name, String birthdayStr) {
        try {
            // birthdayをLocalDateに変換
            java.time.LocalDate birthday = java.time.LocalDate.parse(birthdayStr);

            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            user.setName(name);
            user.setBirthday(birthday);

            return usersDAO.insertUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
