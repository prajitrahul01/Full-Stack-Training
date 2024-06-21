package org.example.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForms {

    @Id
    @GeneratedValue
    @Column(name = "u_id")
    private int id;

    @Column(name = "u_username")
    private String username;

    @Column(name = "u_password1")
    private String password1;

    @Column(name = "u_password2")
    private String password2;

    @Column(name = "u_email")
    private String email;
}
