package org.esgi.cookmaster.test;

import org.esgi.cookmaster.controller.UserController;
import org.esgi.cookmaster.database.User;

import java.sql.SQLException;
import java.util.List;

public class UserControllerTest {
    public static void main(String[] args) throws SQLException {
        UserController testUserController = new UserController();
        List<User> storedUser = testUserController.getLastUsers(0);
        for (User user : storedUser) {
            System.out.println(user);
        }
    }
}
