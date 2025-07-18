package com.example.demo.service.product;

import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.Image;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.request.AddProductRequest;
import com.example.demo.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@RequiredArgsConstructor will only pick up variables that are non-static final
@Service
@RequiredArgsConstructor //Lombok annotation required for constructor injection
public class ProductService implements IProductService {

    private final ProductRepository productRepository;//ProductRepository being used as a dependency
    //will get highlighted in pink when used as a dependency
    //otherwise even when you use asa an object, not highlighted
    private final CategoryRepository categoryRepository;
    //but if you hover below the above dependency you will see that
    //the productRepo dependency never got assigned.
    //this means that the dependency never got injected.
    //to get it injected, we must make it final so that
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    //@RequiredArgsConstructor will pick it up.
    @Override
    public Product addProduct(AddProductRequest request) {

        //check if the category is found in database
        //if yes, set it as the new product category
        //if not, save it as a new category and set it as new product category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                    //Getting error in above two lines ??
                    //It is because the method findByName was supposed to return category
                    //but it is now showing return type Object
                    //Change that to category and also make sure you have a constructor with
                    //declaration public Category(String category)
                        }

                );

        request.setCategory(category);
        return productRepository.save(createProduct(request,category));

    }

    //we need a helper method to aid in the process of adding a product
    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getQuantity(),
                request.getBrand(),
                category);
    } //getting an error under the whole return statement because cannot find
    //an appropriate constructor for these fields.

    //Once we add the required fields constructor in Product entity, then we can see
    //that the error got removed.

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () -> {throw new ResourceNotFoundException("Product Not Found");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        //have to update return type from void to Product and the parameter
        //will be ProductUpdateRequest instead of Product

        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
    }

    //helper function for updating product
    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());

        existingProduct.setPrice(request.getPrice());
        existingProduct.setQuantity(request.getQuantity());
        existingProduct.setBrand(request.getBrand());


        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;

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

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDTO convertToDto(Product product){
        ProductDTO productDto = modelMapper.map(product, ProductDTO.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        //we just want to return name and download url of image to the user
        List<ImageDTO> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDTO.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;

    }
}
