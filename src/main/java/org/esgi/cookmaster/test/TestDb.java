package org.esgi.cookmaster.test;

import org.esgi.cookmaster.database.ConnectDatabase;
import org.esgi.cookmaster.database.ReadFile;
import org.esgi.cookmaster.database.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TestDb {

    public static void main(String[] args) throws SQLException {
        ConnectDatabase testDb = new ConnectDatabase();
        testDb.importConfig("src/config.cfg");
        testDb.connect();

    }
}

