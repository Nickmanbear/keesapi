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
