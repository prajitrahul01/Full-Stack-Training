package org.example.Repository;

import org.example.Model.Gender;
import org.example.Model.User;
import org.example.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.gender = :gender")
    long countByGender(User.Gender gender);

    @Query("SELECT u FROM User u WHERE u.email = :email ORDER BY u.userId DESC")
    User getUserByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email AND u.userRole = :userRole")
    boolean existsByEmailAndUserRole(String email, User.UserRole userRole);

    @Query("SELECT u FROM User u WHERE u.userRole != 'ADMIN'")
    List<User> findAllUsersExceptAdmin();
}
