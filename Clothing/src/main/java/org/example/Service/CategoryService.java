package org.example.Service;

import org.example.Model.Category;
import org.example.Model.Product;
import org.example.Repository.CategoryRepository;
import org.example.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // try

    @Autowired
    private ProductRepository productRepository;

    public Category saveCategoryWithProducts(Category category) {
        // Save the category first to generate the category_id
        Category savedCategory = categoryRepository.save(category);

        // Associate the saved category with each product and save them
        List<Product> products = category.getItems();
        if (products != null && !products.isEmpty()) {
            for (Product product : products) {
                product.setCategory(savedCategory);
                productRepository.save(product);
            }
        }

        return savedCategory;
    }


    public Iterable<Category> getAllCategories() {
        // Retrieve all categories and associated products
        return categoryRepository.findAll();
    }

    // try

    // Method to save a new category
    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    public Category saveAll(Category category){
        return categoryRepository.save(category);
    }

    // Method to update an existing category by ID
    public Category updateCategory(Category category, int id){
        category.setId(id);
        return categoryRepository.saveAndFlush(category);
    }

    // Method to delete a category by ID
    public void deleteCategory(int id){
        categoryRepository.deleteById(id);
    }

    // Method to retrieve all categories
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    // Method to retrieve a category by ID
    public Optional<Category> getCategory(int id){
        return categoryRepository.findById(id);
    }

    public Category getCategoryByTitle(String title){
        return categoryRepository.getCategoryByTitle(title);
    }
    public Category getCategorybyrouteName(String routeName){
        return categoryRepository.getCategoryByRouteName(routeName);
    }

}
