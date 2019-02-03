package nl.hsleiden.persistence;

import nl.hsleiden.Database;
import nl.hsleiden.model.Product;

import javax.inject.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ProductDAO {

    private Database database = Database.getInstance();

    public ProductDAO() {
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
