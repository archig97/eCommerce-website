package com.example.demo.request;

import java.math.BigDecimal;

import com.example.demo.model.Category;
import lombok.Data;

@Data
public class ProductUpdateRequest {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String brand;
    private Category category;
}
