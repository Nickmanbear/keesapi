package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.security.Principal;

public class Product implements Principal {

    @NotEmpty
    @Length(min = 3, max = 100)
    @JsonView(View.Public.class)
    private String productName;

    @JsonView(View.Public.class)
    private int productPrice;

    @JsonView(View.Public.class)
    private int id;

    @JsonView(View.Public.class)
    private String imageUrl;

    public Product() {

    }

    public Product(int id, String productName, int price, String imageUrl) {
        this.id = id;
        this.productName = productName;
        this.productPrice = price;
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    @JsonIgnore
    public String getName()
    {
        return productName;
    }

}
