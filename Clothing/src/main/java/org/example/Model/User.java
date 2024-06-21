package org.example.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="e_user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "u_id")
    private int id;

    @Column(name = "u_username")
    private String username;

    @Column(name = "u_password")
    private String password;

    @Column(name = "u_email")
    private String email;
}
