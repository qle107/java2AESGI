package org.esgi.cookmaster.test;

import org.esgi.cookmaster.controller.GenerateGUI;
import org.esgi.cookmaster.controller.UserController;
import org.esgi.cookmaster.database.User;

import java.sql.SQLException;
import java.util.List;

public class GUITest {

    public static void main(String[] args) throws SQLException {
    GenerateGUI test1 = new GenerateGUI<>("Test Table");
        UserController testUserController = new UserController();
        List<User> storedUser = testUserController.getLastUsers(0);
        test1.generateNewGUI(storedUser);
    }
}
