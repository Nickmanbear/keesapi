package nl.hsleiden.persistence;

import nl.hsleiden.Database;
import nl.hsleiden.model.User;

import javax.inject.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Peter van Vliet
 */
@Singleton
public class UserDAO
{
    private final List<User> users;
    private Database database = Database.getInstance();
    
    public UserDAO()
    {
//        User user1 = new User();
//        user1.setFullName("First user");
//        user1.setPostcode("1234AB");
//        user1.setStreetnumber("12");
//        user1.setEmailAddress("first@user.com");
//        user1.setPassword("first");
//        user1.setRoles(new String[] { "GUEST", "ADMIN" });
//
//        User user2 = new User();
//        user2.setFullName("Second user");
//        user2.setPostcode("9876ZY");
//        user2.setStreetnumber("98");
//        user2.setEmailAddress("second@user.com");
//        user2.setPassword("second");
//        user2.setRoles(new String[] { "GUEST" });
        
        users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
    }
    
    public List<User> getAll()
    {
        List<User> users = new ArrayList<>();

        String selectString = "SELECT * FROM Wumsh_User";

        PreparedStatement preparedStatement;
        ResultSet result;
        try {
            preparedStatement = database.getConnection().prepareStatement(selectString);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                User user = new User(
                        result.getInt("user_id"),
                        result.getString("user_name"),
                        result.getBoolean("user_permission"),
                        result.getString("user_password")
                );
                users.add(user);
            }

            database.closeResult(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return users;
    }
    
    public User get(int id)
    {
        String selectString = "SELECT * FROM Wumsh_User WHERE user_id = ?;";

        ResultSet result = database.getResult(id, selectString);

        User user = null;
        try {
            while (result.next()) {
                user = new User(
                        result.getInt("user_id"),
                        result.getString("user_name"),
                        result.getBoolean("user_permission"),
                        result.getString("user_password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        database.closeResult(result);

        return user;
    }
    
    public User getByUsername(String username)
    {
        Optional<User> result = getAll().stream()
            .filter(user -> user.getUsername().equals(username))
            .findAny();

        return result.orElse(null);
    }
    
    public void add(User user)
    {
        String insertString = "INSERT INTO Wumsh_User " +
                "(user_id, user_name, user_permission, user_password) VALUES " +
                "(?, ?, ?, ?);";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertString);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setBoolean(3, user.getRoles()[0].equals("ADMIN"));
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(int id, User user)
    {
        String updateString = "UPDATE Wumsh_User " +
                "SET user_id = ?, user_name = ?, user_permission = ?, user_password = ? " +
                "WHERE user_id = ?;";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(updateString);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setBoolean(3, user.getRoles()[0].equals("ADMIN"));
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id)
    {
        String deleteString = "DELETE FROM Wumsh_User WHERE user_id = ?;";
        database.delete(id, deleteString);
    }
}
