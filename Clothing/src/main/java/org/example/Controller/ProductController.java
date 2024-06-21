package org.example.Controller;


import jakarta.persistence.Id;
import org.example.Model.Product;
import org.example.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins ="*",allowedHeaders = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    // Endpoint to add a new product
    @PostMapping("/add-product")
    public Product save(@RequestBody Product prod) {
        return productService.add(prod);
    }
    // Endpoint to update an existing product by ID
    @PostMapping("/{id}")
    public Product update(@RequestBody Product prod, @PathVariable("id") int id){
        return productService.updateProd(prod,id);
    }
    // Endpoint to delete a product by ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        productService.deleteProd(id);
    }

    // Endpoint to get all products
    @GetMapping("/")
    public List<Product> getAllProducts(){
        return productService.getAllProduct();
    }

    // Endpoint to get a product by ID
    @PostMapping("/")
    public Optional<Product> getProduct(@RequestBody int id){
        return productService.getProduct(id);
    }

    // Endpoint to link a product with a category by ID
    @PostMapping("/product-with-category/{id}")
    public Product linkProductCategory(@RequestBody Product product, @PathVariable("id") int id){
        return productService.linkProductCategory(product, id);
    }


    @GetMapping("/findbyname/{name}")
    public Product findbyname(@PathVariable String name) {
        return productService.getProductbyname(name);
    }
    @GetMapping("/findbydes/{des}")
    public Product findbydes(@PathVariable String des) {
        return productService.getProductByImageUrl(des);
    }
    @GetMapping("/findbyprice/{price}")
    public Product findbyprice(@PathVariable int price) {
        return productService.getProductbyprice(price);
    }

}
