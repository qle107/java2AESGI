package org.esgi.cookmaster.controller;

import org.esgi.cookmaster.database.ConnectDatabase;
import org.esgi.cookmaster.database.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserController {
    protected ConnectDatabase connectDb = new ConnectDatabase();

    public User getUser(String id) {
        connectDb.importConfig("src/config.cfg");
        connectDb.connect();
        return connectDb.getUser(id);
    }

    public List<User> getLastUsers(int limited) throws SQLException {
        List<User> storedUser = new ArrayList<>();
        connectDb.importConfig("src/config.cfg");
        connectDb.connect();
        ResultSet resultSet = connectDb.executeQuery("SELECT COUNT(*) AS total_users FROM users");
        int totalUsers = 0;
        if (resultSet.next()) {
            totalUsers = resultSet.getInt("total_users");

        }
        int totalQuery = 0;

        if (limited < totalUsers && limited > 0) {
            totalQuery = totalUsers - limited;
        }

        for (int countUser = totalUsers; countUser > totalQuery; countUser--) {
            User storedUserToArray = connectDb.getUser(String.valueOf(countUser));
            storedUser.add(storedUserToArray);
        }
        return storedUser;
    }

}
