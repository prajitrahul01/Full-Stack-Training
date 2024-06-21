package org.example.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "e_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "p_id")
    private int id;

    @Column(name = "p_name")
    private String name;

    @Column(name = "p_price")
    private int price;

    @Column(name = "p_imageurl")
    private String imageUrl;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "c_id")
    private Category category;
}

