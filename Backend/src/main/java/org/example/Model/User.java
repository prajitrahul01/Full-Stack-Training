package org.example.Model;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.*;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;

import jakarta.persistence.Table;

import lombok.*;

import java.time.LocalDateTime;

import java.util.Date;


@Entity // to map Java objects to database tables

@Data // Boilerplate code for setters and getters (from lombok)

@AllArgsConstructor // Boilerplate code for All argument constructor (from lombok)

@NoArgsConstructor // Boilerplate code for No argument constructor (from lombok)

@Getter

@Setter

@Table(name = "users")

public class User {

    public enum UserRole {

        ADMIN,

        SITEMANAGER,

        EMPLOYEE

    }

    public enum Gender {

        MALE,

        FEMALE,

        OTHER

    }

    @Id

    @Column(name = "user_id")

    @GeneratedValue(strategy = GenerationType.IDENTITY) // to configure the way of increment

    private int userId;

    @Column(name = "user_name", nullable = false)

    private String userName;

    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING) // to map the enum values to the database using their string representations
    private UserRole userRole;

    @Column(name = "user_gender", nullable = false)
    @Enumerated(EnumType.STRING) // to map the enum values to the database using their string representations
    private Gender gender;

    @Column(name = "last_login_time_stamp")
    private Date lastLoginTimeStamp;

}