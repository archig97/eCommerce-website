package com.example.demo.service.product;

import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
//@RequiredArgsConstructor will only pick up variables that are non-static final
@Service
@RequiredArgsConstructor //Lombok annotation required for constructor injection
public class ProductService implements IProductService {

    private final ProductRepository productRepository;//ProductRepository being used as a dependency
    //but if you hover below the above dependency you will see that
    //the productRepo dependency never got assigned.
    //this means that the dependency never got injected.
    //to get it injected, we must make it final so that
    //@RequiredArgsConstructor will pick it up.
    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElse(()-> new ProductNotFoundException("Product Not Found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () -> {throw new ProductNotFoundException("Product Not Found");});
    }

    @Override
    public void updateProduct(Product product, Long productId) {

    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String productName) {
        return productRepository.findByName(productName);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String productName) {
        return productRepository.findByBrandAndName(brand,productName);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String productName) {
        return productRepository.countByBrandAndName(brand,productName);
    }
}
