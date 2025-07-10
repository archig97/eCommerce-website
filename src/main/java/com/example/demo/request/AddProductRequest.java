package com.example.demo.request;

import java.math.BigDecimal;
import java.util.List;

import com.example.demo.model.Category;
import com.example.demo.model.Image;
import lombok.Data;

@Data //cannot use Data annotation on Entit
//there we must use the other Lombok Getter Setter annotations// y class as it will not be safe
public class AddProductRequest {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String brand;
    private Category category;
    private List<Image> images;
}
