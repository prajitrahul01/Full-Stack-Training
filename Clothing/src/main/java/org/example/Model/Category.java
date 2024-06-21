package org.example.Model;
//
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "e_category")
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @Column(name = "c_id")
    private int id;

    @Column(name = "c_title")
    private String title;

    @Column(name = "c_routename")
    private String routeName;

    @Column(name="c_image")
    private String image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> items;
}

