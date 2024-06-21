package org.example.Repository;

import org.example.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Custom query to retrieve a User by email, ordered by id in descending order, and limiting to 1 result
    @Query("SELECT u FROM User u WHERE u.email = :email ORDER BY u.id DESC limit 1")
    User getUserByEmail(String email);
}
