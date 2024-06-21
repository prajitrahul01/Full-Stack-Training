package org.example.Service;


import org.example.Model.Product;
import org.example.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigData;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Method to add a new product
    public Product add(Product product){
        return productRepository.save(product);
    }

    // Method to update an existing product by ID
    public Product updateProd(Product product, int id) {//throws ProductException, IdException {
//        try {
        product.setId(id);
        return productRepository.saveAndFlush(product);
//        }
//        catch (IdException e){
//            e.getErrorMessage();
//        }
//        catch (ProductException e){
//            e.getErrorMessage();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
    }

    // Method to delete a product by ID
    public void deleteProd(int id){
        productRepository.deleteById(id);
    }

    // Method to retrieve all products
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    // Method to retrieve a product by ID
    public Optional<Product> getProduct(int id){
        return productRepository.findById(id);
    }

    @Autowired
    CategoryService categoryService;

    // Method to link a product with a category
    public Product linkProductCategory(Product product, int id){
        // Retrieve the existing category from the db for the id and link it with new product
        product.setCategory(categoryService.getCategory(id).get());

        // Get the old products value
        Product product1 = productRepository.findById(product.getId()).get();

        // Update new product with old product attributes only if the new product attributes are null or 0
        if(product.getName() == null)
            product.setName(product1.getName());
        if(product.getImageUrl() == null)
            product.setImageUrl(product1.getImageUrl());
        if(product.getPrice() == 0)
            product.setPrice(product1.getPrice());

        // update the old product with updated new product
        return productRepository.saveAndFlush(product);
    }

    public Product getProductbyname(String name) {
        return productRepository.getProductByProductName(name);
    }
    public Product getProductbyprice(int Price) {
        return productRepository.getProductByPrice(Price);
    }
    public Product getProductByImageUrl(String des) {
        return productRepository.getProductByImageUrl(des);
    }

}
