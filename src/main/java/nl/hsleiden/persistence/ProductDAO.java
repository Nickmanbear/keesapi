package nl.hsleiden.persistence;

import nl.hsleiden.Database;
import nl.hsleiden.model.Product;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProductDAO {

    private final List<Product> products;
    private Database database = Database.getInstance();

    public ProductDAO() {
//        Product product1 = new Product();
//        product1.setProductName("Cool lämp");
//        product1.setProductPrice(70);
//        product1.setId(0);
//
//        Product product2 = new Product();
//        product2.setProductName("Fancy Chair");
//        product2.setProductPrice(150);
//        product2.setId(1);

        products = new ArrayList<>();
//        products.add(product1);
//        products.add(product2);
    }

    public List<Product> getAll()
    {
        List<Product> products = new ArrayList<>();

        String selectString = "SELECT * FROM Product";

        PreparedStatement preparedStatement;
        ResultSet result;
        try {
            preparedStatement = database.getConnection().prepareStatement(selectString);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                Product product = new Product(
                        result.getInt("product_id"),
                        result.getString("product_name"),
                        result.getInt("product_price"),
                        result.getString("product_image")
                );
                products.add(product);
            }

            database.closeResult(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return products;
    }

    public Product get(int id) {
        String selectString = "SELECT * FROM Product WHERE product_id = ?;";

        ResultSet result = database.getResult(id, selectString);

        Product product = null;
        try {
            if (result.next()) {
                product = new Product(
                        result.getInt("product_id"),
                        result.getString("product_name"),
                        result.getInt("product_price"),
                        result.getString("product_image")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        database.closeResult(result);

        return product;
    }

    public void add(Product product) {
        String insertString = "INSERT INTO Product " +
                "(product_id, product_name, product_price, product_image) VALUES " +
                "(?, ?, ?, ?);";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertString);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setInt(3, product.getProductPrice());
            preparedStatement.setString(4, product.getImageUrl());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Product product)
    {
        String updateString = "UPDATE Product " +
                "SET product_id = ?, product_name = ?, product_price = ?, product_image = ? " +
                "WHERE product_id = ?;";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(updateString);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setInt(3, product.getProductPrice());
            preparedStatement.setString(4, product.getImageUrl());
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id)
    {
        String deleteString = "DELETE FROM Product WHERE product_id = ?;";
        database.delete(id, deleteString);
    }
}
