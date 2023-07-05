package org.esgi.cookmaster.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.esgi.cookmaster.database.ConnectDatabase;
import org.esgi.cookmaster.database.ReadFile;

public class SubscriptionController {

    ConnectDatabase connectDb = new ConnectDatabase();
    Properties config = new Properties();

    /**
     * Constructor
     * Connect to database
     * Log info from config
     */
    public SubscriptionController(){
        try (FileInputStream fis = new FileInputStream("src/config.cfg")) {
            config.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading the configuration file: " + e.getMessage());
            return ;
        }
        connectDb.importConfig("src/config.cfg");
        connectDb.connect();
    }

    public String getSub(String id){
    String result = null;
        List<List<Object>> storedSubs = connectDb.getDataFromTable(config.getProperty("tbl.sub.name"));
        for (List<Object> storedSub : storedSubs){
            if(storedSub.get(0) == id){
                result = storedSub.get(1).toString();
                return result;
            }
        }
        return result;
    }

}
