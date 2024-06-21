package org.example.Repository;

import org.example.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Custom query to retrieve a Product by its name
    @Query(value = "SELECT p FROM Product p WHERE p.name = :productName")
    Product getProductByProductName(String productName);

    // Custom query to retrieve a Product by its price
    @Query(value="SELECT p FROM Product p WHERE p.price=:Price")
    Product getProductByPrice(int Price);

    // Custom query to retrieve a Product by its description
    @Query(value="SELECT p FROM Product p WHERE p.imageUrl=:imageUrl")
    Product getProductByImageUrl(String imageUrl);
}
