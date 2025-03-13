package com.example.demo.service.product;

import com.example.demo.model.Product;

import java.util.List;

public interface IProductService {

    Product addProduct(Product product);//for adding product to DB
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    void updateProduct(Product product, Long productId);
    List<Product> getProductByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String productName);
    List<Product> getProductsByBrandAndName(String brand, String productName);
    Long countProductsByBrandAndName(String brand, String productName);


}
