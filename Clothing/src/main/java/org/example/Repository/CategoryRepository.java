package org.example.Repository;

import org.example.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Custom query to retrieve a Category by its name
    @Query(value = "SELECT c FROM Category c WHERE c.title = :categoryTitle")
    Category getCategoryByTitle(String categoryTitle);

    // Custom query to retrieve a Category by its description
    @Query(value = "SELECT c FROM Category c WHERE c.routeName = :routeName")
    Category getCategoryByRouteName(String routeName);
}