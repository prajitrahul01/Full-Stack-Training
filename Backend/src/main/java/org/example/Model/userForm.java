package org.example.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userForm {

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

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING) // to map the enum values to the database using their string representations
    private User.UserRole userRole;

    @Column(name = "user_gender", nullable = false)
    @Enumerated(EnumType.STRING) // to map the enum values to the database using their string representations
    private User.Gender gender;

    @Column(name = "last_login_time_stamp")
    private Date lastLoginTimeStamp;

}

