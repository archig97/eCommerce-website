package com.example.demo.service.category;

import java.util.List;
import java.util.Optional;

import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;//why should it be final? - so that the @RequiredArgsConstructor can create a bean out of it
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName()+" already exists!!"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))//the category returned by getCategoryById will be the oldCategory
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());//take the name from the category passed in the function call
                    return categoryRepository.save(oldCategory);//save the changes
                }).orElseThrow(() -> new ResourceNotFoundException("Category not found!!"));
    }

    @Override
    public void deleteCategoryById(Long id) {
    categoryRepository.findById(id)
            .ifPresentOrElse(categoryRepository :: delete,
                    () -> {throw new ResourceNotFoundException("Category Not Found!!");
            });
    }
}
