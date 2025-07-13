package com.example.demo.dto;

import java.math.BigDecimal;

import com.example.demo.model.Product;
import lombok.Data;

@Data
public class CartItemDTO {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDTO product;
}
