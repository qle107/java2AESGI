package org.esgi.cookmaster;

import org.esgi.cookmaster.controller.GenerateGUI;
import org.esgi.cookmaster.controller.UserController;
import org.esgi.cookmaster.database.User;

import java.sql.SQLException;
import java.util.List;

public class main {
    public static void main(String[] args) throws SQLException {
        GenerateGUI genrateGui = new GenerateGUI<>("Database");
        UserController userController = new UserController();
        List<User> storedUser = userController.getLastUsers(0);
        genrateGui.generateNewGUI(storedUser);
    }
}
