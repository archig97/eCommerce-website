package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor - Not needed as we have a parameterized constructor
//with almost all attributes
@Entity
@Table(name="products")
public class Product {

    public Product(String name, String description, double price, int quantity, String brand, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.category = category;
    }

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
    @JsonIgnore
    private List<Image> images;
}
