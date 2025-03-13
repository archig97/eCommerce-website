package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String brand;

    @ManyToOne(cascade = CascadeType.ALL)//relation between product and category will be deleted as well
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
}
