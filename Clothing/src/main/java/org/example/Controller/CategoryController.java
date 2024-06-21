package org.example.Controller;


import org.example.Model.Category;
import org.example.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins ="*",allowedHeaders = "*")
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // try

    @PostMapping("/saveA")
    public ResponseEntity<String> saveCategoryWithProducts(@RequestBody Category category) {
        categoryService.saveCategoryWithProducts(category);
        return new ResponseEntity<>("Category saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Category>> getAllCategories() {
        Iterable<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Endpoint to save a new category
    @PostMapping("/save")
    public Category save(@RequestBody Category category){
        return categoryService.saveCategory(category);
    }

    // Endpoint to update an existing category by ID
    @PostMapping("/{id}")
    public Category update(@RequestBody Category category, @PathVariable("id") int id){
        return categoryService.updateCategory(category, id);
    }

    @PostMapping("/saveAll")
    public Category saveAll(@RequestBody Category category){
        return categoryService.saveAll(category);
    }

    // Endpoint to delete a category by ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id){
        categoryService.deleteCategory(id);
    }

    // Endpoint to get all categories
    @GetMapping("/")
    public List<Category> getAll(){
        return categoryService.getAllCategory();
    }

    // Endpoint to get a category by ID
    @GetMapping("/get/{id}")
    public Optional<Category> get(@PathVariable("id") int id){
        return categoryService.getCategory(id);
    }

}