package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Category;
import com.example.demo.model.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String brand;


    private Category category;


    private List<ImageDTO> images;
}
