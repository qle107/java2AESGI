package org.esgi.cookmaster.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class ConnectDatabase {
    private String dbHost;
    private String dbPort;
    private String dbName;
    private String dbUsername;
    private String dbPassword;
    private String dbType;
    private String classType = null;
    private Statement connStatement;
    private Connection conn;

    public void defineDb(String host, String port, String name, String userName, String password) {
        switch (port) {
            case "3306":
                this.dbType = "mysql";
                break;
            case "5432":
                this.dbType = "postgresql";
                break;
            default:
                this.dbType = "unknown";
                break;
        }
        this.dbPassword = password;
        this.dbHost = host;
        this.dbUsername = userName;
        this.dbPort = port;
        this.dbName = name;
    }
    public void modifyDbType(String name) {
        this.dbType = name;
    }

    public void connect() {
        // Database connection details
        String jdbcUrl = "";
        switch (this.dbType) {
            case "mysql":
                this.classType = "com.mysql.cj.jdbc.Driver";
                jdbcUrl = "jdbc:mysql://" + this.dbHost + ":" + this.dbPort + "/" + this.dbName;
                break;
            case "postgresql":
                this.classType = "org.postgresql.Driver";
                jdbcUrl = "jdbc:postgresql://" + this.dbHost + ":" + this.dbPort + "/" + this.dbName;
                break;
            default:
                jdbcUrl = "!supported";
                break;
        }
        // Establishing the connection
        if (!jdbcUrl.equals("!supported")) {
            try {
                Class.forName(this.classType);
                Connection connection = DriverManager.getConnection(jdbcUrl, this.dbUsername, this.dbPassword);
                System.out.println("Connected to the database successfully!");

                // Perform database
                conn = connection;
                connStatement = connection.createStatement();
                System.out.println("connected");
            } catch (SQLException e) {
                System.out.println("Failed to connect to the database.");
                e.getMessage();
            } catch (ClassNotFoundException e) {
                e.getMessage();
//                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Database type is not supported");
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        if (this.connStatement != null && !this.connStatement.isClosed()) {
            try {
                // Execute the query and obtain the result set

                return connStatement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<String> getTables() {
        List<String> tableNames = new ArrayList<>();
        try {
            // Get the metadata of the database
            DatabaseMetaData metaData = this.conn.getMetaData();

            // Get all table names
            ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});

            // Process the result set
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                tableNames.add(tableName);

            }
            // Close the result set
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableNames;
    }
    public List<List<List<Object>>> getAllDataFromTables() {
        try {
            if (this.conn != null && !this.connStatement.isClosed()) {
                List<String> tables = getTables();
                List<List<List<Object>>> data = new ArrayList<>();

                for (String table : tables) {

                    List<List<Object>> tableData = getDataFromTable(table);
                    data.add(tableData);
                }
                return data;
            } else {
                return null;
            }
        } catch (SQLException error) {
            System.err.println("Error querying data: " + error.getMessage());
            // Handle the error here
            return null;
        }
    }

    public List<List<Object>> getDataFromTable(String table) {
        try {
            if (conn != null && !conn.isClosed()) {
                List<List<Object>> data = new ArrayList<>();

                String query = "SELECT * FROM " + table;
                try (Statement stmt = conn.createStatement();
                     ResultSet resultSet = stmt.executeQuery(query)) {
                    data.add(Collections.singletonList(table));

                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    while (resultSet.next()) {
                        List<Object> row = new ArrayList<>();
                        for (int i = 1; i <= columnCount; i++) {
                            Object columnValue = resultSet.getObject(i);
                            row.add(columnValue);
                        }
                        data.add(row);
                    }
                }
                return data;
            } else {
                return null;
            }
        } catch (SQLException error) {
            System.err.println("Error querying data: " + error.getMessage());
            return null;
        }
    }


    public void exportDbStructure() {
        try {
            DatabaseMetaData metaData = this.conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");

                // Retrieve the columns in each table
                ResultSet columns = metaData.getColumns(null, null, tableName, null);

                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");

                    // Print the column information
                    System.out.println("Table: " + tableName + ", Column: " + columnName + ", Type: " + columnType);
                }
                columns.close();
            }
            tables.close();
        } catch (SQLException err) {
            System.err.println(err.getMessage());
        }

    }

    public void importConfig(String pathFile) {
        ReadFile thisFile = new ReadFile();
        thisFile.defineFile(pathFile);
        Properties config = new Properties();
        try (FileInputStream fis = new FileInputStream(pathFile)) {
            config.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading the configuration file: " + e.getMessage());
            return;
        }

        // Retrieve and use the configuration values
        this.dbHost = config.getProperty("db.host");
        this.dbPort = config.getProperty("db.port");
        this.dbName = config.getProperty("db.name");
        this.dbUsername = config.getProperty("db.username");
        this.dbPassword = config.getProperty("db.password");


        this.defineDb(this.dbHost, this.dbPort, this.dbName, this.dbUsername, this.dbPassword);


    }
}
