package org.esgi.cookmaster.controller;

import org.esgi.cookmaster.database.ConnectDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class UserController {
    ConnectDatabase connectDb = new ConnectDatabase();
    Properties config = new Properties();

    public UserController() {

        try (FileInputStream fis = new FileInputStream("src/config.cfg")) {
            config.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading the configuration file: " + e.getMessage());
            return;
        }
        connectDb.importConfig("src/config.cfg");
        connectDb.connect();
    }

    public Object getUser(String id) {
        Object result = null;
        List<List<Object>> storedSubs = connectDb.getDataFromTable(config.getProperty("tbl.usr.name"));
        for (List<Object> storedSub : storedSubs) {
            if (storedSub.get(0) == id) {
                storedSub.add(new SubscriptionController().getSub(id));
                result = storedSub.get(1);
                return result;
            }
        }
        return result;
    }

    public int maxUsr() {
        int result = 0;
        List<List<Object>> storedSubs = connectDb.getDataFromTable(config.getProperty("tbl.usr.name"));
        for (List<Object> storedSub : storedSubs) {
            result++;
        }
        return result;
    }

    public String getSub(String id) {
        return new SubscriptionController().getSub(id);
    }
}
