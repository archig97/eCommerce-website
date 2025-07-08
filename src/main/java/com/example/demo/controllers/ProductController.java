package com.example.demo.controllers;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import com.example.demo.dto.ProductDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.request.AddProductRequest;
import com.example.demo.request.ProductUpdateRequest;
import com.example.demo.response.APIResponse;
import com.example.demo.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new APIResponse("Success",convertedProducts));
    }

    @GetMapping("product/{productId}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable  Long productId) {
        try{
            Product product = productService.getProductById(productId);
            ProductDTO productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new APIResponse("Found",productDto));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));

        }

    }

    @GetMapping("product/{name}/product")
    public ResponseEntity<APIResponse> getProductByName(@PathVariable  String name) {
       try {
                List<Product> products = productService.getProductsByName(name);

                if (products.isEmpty()) {
                    return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No product found", null));
                }

                List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);

                return ResponseEntity.ok(new APIResponse("Found products", convertedProducts));
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(),null));
            }

    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product) {
        try{
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new APIResponse("Added product successfully",theProduct));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<APIResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) {
        try{
            Product theProduct = productService.updateProduct(request,productId);
            return ResponseEntity.ok(new APIResponse("Updated product successfully",theProduct));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long productId) {
        try{
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new APIResponse("Deleted product successfully",productId));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<APIResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
            try {
                List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
                //List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);
                if (products.isEmpty()) {
                    return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No product found", null));
                }

                List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);

                return ResponseEntity.ok(new APIResponse("Found products", convertedProducts));
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(),null));
            }


    }

    @GetMapping("/products/count/by-brand/by-name")
    public ResponseEntity<APIResponse> countProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
            try {
                Long count = productService.countProductsByBrandAndName(brandName, productName);
                if (count==0) {
                    return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No product found", null));
                }

                return ResponseEntity.ok(new APIResponse("Found products", count));
            } catch (Exception e) {
                return ResponseEntity.ok(new APIResponse(e.getMessage(),null));
            }


    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<APIResponse> getProductByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brandName) {
            try {
                List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);

                if (products.isEmpty()) {
                    return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No product found", null));
                }

                List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);

                return ResponseEntity.ok(new APIResponse("Found products", convertedProducts));
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(),null));
            }


    }

    @GetMapping("/products/by-brand")
    public ResponseEntity<APIResponse> getProductByBrand(@RequestParam String brandName) {
            try {
                List<Product> products = productService.getProductsByBrand(brandName);

                if (products.isEmpty()) {
                    return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No product found", null));
                }

                List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);

                return ResponseEntity.ok(new APIResponse("Found products", convertedProducts));
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(),null));
            }


    }

     @GetMapping("/products/{categoryName}/all/products")
    public ResponseEntity<APIResponse> findProductsByCategory(@PathVariable String categoryName) {
            try {
                List<Product> products = productService.getProductByCategory(categoryName);

                if (products.isEmpty()) {
                    return ResponseEntity.status(NOT_FOUND).body(new APIResponse("No product found", null));
                }

                List<ProductDTO> convertedProducts = productService.getConvertedProducts(products);

                return ResponseEntity.ok(new APIResponse("Found products", convertedProducts));
            } catch (Exception e) {
                return ResponseEntity.ok(new APIResponse(e.getMessage(),null));
            }


    }










}
