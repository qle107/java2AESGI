package org.esgi.cookmaster.test;

import org.esgi.cookmaster.database.ConnectDatabase;
import org.esgi.cookmaster.database.ReadFile;


public class TestDb {
    static String db_host = "localhost";
    static String db_name = "testJavaDb";
    static String db_port = "5432";
    static String db_username = "postgres";
    static String password = "admin";
    static String user_table = "users";

    public static void main(String[] args) {
        ConnectDatabase testDb = new ConnectDatabase();
//        testDb.defineDb(db_host, db_port, db_name, db_username, password);
//        testDb.connect();
//        List<Object> storedData2 = testDb.getDataFromTable("orders");
//        List<List<Object>> storedData = testDb.getAllDataFromTables();
        testDb.importConfig("src/config.cfg");
        testDb.connect();
        System.out.print(testDb.getAllDataFromTables());
    }
}

