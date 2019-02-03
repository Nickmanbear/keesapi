package nl.hsleiden;

import java.sql.*;

public class Database {

    private static Database instance;
    private Connection connection;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private boolean hasConnection() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void connect(String host, String dbName, String user, String password) {
//        String connectionString = String.format(
//                "jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=UTC",
//                host, dbName, user, password
//        );
        String connectionString = System.getenv("JDBC_DATABASE_URL");
        connect(connectionString);
    }

    void connect(String connectionString) {
        if (hasConnection()) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (hasConnection()) {
            try {
                Statement stmt = connection.createStatement();
//                stmt.executeUpdate(
//                "CREATE TABLE IF NOT EXISTS Product (" +
//                        "product_id smallint PRIMARY KEY," +
//                        "product_name varchar(256) NOT NULL," +
//                        "product_price smallint NOT NULL," +
//                        "product_image varchar(512))");
//                stmt.executeUpdate(
//                "CREATE TABLE IF NOT EXISTS Wumsh_User (" +
//                        "user_id smallint PRIMARY KEY," +
//                        "user_name varchar(256) NOT NULL UNIQUE," +
//                        "user_permission boolean NOT NULL," +
//                        "user_password varchar(256) NOT NULL)");
//                stmt.executeUpdate(
//                "INSERT INTO Wumsh_User " +
//                        "VALUES (0, 'admin', true, 'admin')");
//                stmt.executeUpdate(
//                "INSERT INTO Product " +
//                        "VALUES (0, 'First', 0, 'https://i.imgur.com/t7ImDtm.jpg')");
//                stmt.executeUpdate(
//                "ALTER TABLE Product RENAME COLUMN prodcut_name TO product_name");
//                stmt.executeUpdate(
//                "ALTER TABLE Product ADD COLUMN product_image varchar(512)");
//                stmt.executeUpdate(
//                "UPDATE Wumsh_User " +
//                        "SET user_name = 'Kees', user_permission = true, user_password = 'MWWiDW^WS5' " +
//                        "WHERE user_id = 0;"
//                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Connection");
        }
    }

    public void disconnect() {
        if (!hasConnection()) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResult(int key, String selectString) {
        ResultSet result = null;
        try {
            PreparedStatement preparedStatement = instance.getConnection().prepareStatement(selectString);
            preparedStatement.setInt(1, key);

            result = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void delete(int key, String deleteString) {
        try {
            PreparedStatement preparedStatement = instance.getConnection().prepareStatement(deleteString);
            preparedStatement.setInt(1, key);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void closeResult(ResultSet result) {
        try {
            Statement statement = result.getStatement();
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
