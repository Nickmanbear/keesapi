package nl.hsleiden;

import java.sql.*;

public class Database {

    private static Database instance;
    private Connection connection;


    /**
     * Returns the instance of MariaDB so it can be accessed anywhere.
     * @return MariaDB
     * @author Sven van Duijn
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    /**
     * Returns the connection
     * @return Connection
     * @author Sven van Duijn
     */
    public Connection getConnection() {
        return connection;
    }



    /**
     * Returns if the instance of MariaDB has a connection with the database.
     * @return boolean
     * @author Sven van Duijn
     */
    private boolean hasConnection() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Create a connection.
     * @param host String
     * @param dbName String
     * @param user String
     * @param password String
     * @author Sven van Duijn
     */
    public void connect(String host, String dbName, String user, String password) {
//        String connectionString = String.format(
//                "jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=UTC",
//                host, dbName, user, password
//        );
        String connectionString = System.getenv("JDBC_DATABASE_URL");
        connect(connectionString);
    }

    /**
     * Create a connection.
     * @param connectionString String
     * @author Sven van Duijn
     */
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
                stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Product (" +
                        "product_id smallint PRIMARY KEY," +
                        "product_name varchar(256) NOT NULL," +
                        "product_price smallint NOT NULL," +
                        "product_image varchar(512))");
                stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Wumsh_User (" +
                        "user_id smallint PRIMARY KEY," +
                        "user_name varchar(256) NOT NULL UNIQUE," +
                        "user_permission boolean NOT NULL," +
                        "user_password varchar(256) NOT NULL)");
//                stmt.executeUpdate(
//                "INSERT INTO Wumsh_User " +
//                        "VALUES (0, 'admin', true, 'admin')");
                stmt.executeUpdate(
                "INSERT INTO Product " +
                        "VALUES (0, 'First', 0, 'https://i.imgur.com/t7ImDtm.jpg')");
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

    /**
     * Close the connection.
     * @author Sven van Duijn
     */
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

    /**
     * Returns a result using a parameter and a select String.
     * @param key String
     * @param selectString String
     * @return ResultSet
     * @author Sven van Duijn
     */
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

    /**
     * Delete an object from the database.
     * @param key String
     * @param deleteString String
     * @author Sven van Duijn.
     */
    public void delete(int key, String deleteString) {
        try {
            PreparedStatement preparedStatement = instance.getConnection().prepareStatement(deleteString);
            preparedStatement.setInt(1, key);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the result.
     * @param result ResultSet
     * @author Sven van Duijn
     */
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
