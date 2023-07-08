package org.esgi.cookmaster.database;

import java.io.FileInputStream;
import java.io.IOException;
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
    private String userTable;
    private String eventTable;
    private String subscriptionTable;
    private String roomTable;

    public String getUserTable() {
        return userTable;
    }

    public String getEventTable() {
        return eventTable;
    }

    public String getSubscriptionTable() {
        return subscriptionTable;
    }

    public String getRoomTable() {
        return roomTable;
    }

    protected void defineDb(String host, String port, String name, String userName, String password) {
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

    protected void defineTable(String userTable, String eventTable, String subscriptionTable) {
        this.eventTable = eventTable;
        this.userTable = userTable;
        this.subscriptionTable = subscriptionTable;
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

                // Perform database
                conn = connection;
                connStatement = connection.createStatement();
            } catch (SQLException e) {
                System.err.println("Failed to connect to the database.");
                e.getMessage();
            } catch (ClassNotFoundException e) {
                e.getMessage();
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("Database type is not supported");
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
//                            System.out.println(resultSet.getObject("price"));
                            Object columnValue = resultSet.getObject(i);
                            System.out.println(columnValue.getClass());

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

    public Subscription getSubscription(String id) {
        try {
            if (conn != null && !conn.isClosed()) {
                Subscription data = null;
                String query = "SELECT * FROM " + subscriptionTable + "where id = '" + id + "' ";
                try (Statement stmt = conn.createStatement();
                     ResultSet resultSet = stmt.executeQuery(query)) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            data = new Subscription(resultSet.getString("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("price"),
                                    resultSet.getString("currency"),
                                    resultSet.getString("frequency"),
                                    resultSet.getString("stripe_api_key"),
                                    resultSet.getString("stripe_product_key")
                            );
                        }
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

    public User getUser(String id) {
        try {
            if (conn != null && !conn.isClosed()) {
                User data = null;
                String query = "SELECT * FROM " + userTable + " where id = '" + id + "' ";
                try (Statement stmt = conn.createStatement();
                     ResultSet resultSet = stmt.executeQuery(query)) {

                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            data = new User(resultSet.getString("id"),
                                    resultSet.getString("email"),
                                    resultSet.getString("phone"),
                                    resultSet.getString("last_name"),
                                    resultSet.getString("first_name"),
                                    resultSet.getString("address"),
                                    resultSet.getString("role"),
                                    resultSet.getString("subscription_id")
                            );
                        }
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
    public Event getEvent(String id) {
        try {
            if (conn != null && !conn.isClosed()) {
                Event data = null;
                String query = "SELECT * FROM " + eventTable + " where id = '" + id + "' ";
                try (Statement stmt = conn.createStatement();
                     ResultSet resultSet = stmt.executeQuery(query)) {

                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            data = new Event(resultSet.getString("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("max_capacity"),
                                    resultSet.getString("description"),
                                    resultSet.getString("type"),
                                    resultSet.getString("start_time"),
                                    resultSet.getString("end_time"),
                                    resultSet.getString("room_id")
                            );
                        }
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

    public Room getRoom(String id) {
        try {
            if (conn != null && !conn.isClosed()) {
                Room data = null;
                String query = "SELECT * FROM " + roomTable + " where id = '" + id + "' ";
                try (Statement stmt = conn.createStatement();
                     ResultSet resultSet = stmt.executeQuery(query)) {

                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            data = new Room(resultSet.getString("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("address"),
                                    resultSet.getString("max_capacity"),
                                    resultSet.getString("price"),
                                    resultSet.getString("availability")
                            );
                        }
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
        this.subscriptionTable = config.getProperty("tbl.sub.name");
        this.eventTable = config.getProperty("tbl.event.name");
        this.userTable = config.getProperty("tbl.usr.name");
        this.roomTable = config.getProperty("tbl.room.name");


        this.defineDb(this.dbHost, this.dbPort, this.dbName, this.dbUsername, this.dbPassword);


    }
}
