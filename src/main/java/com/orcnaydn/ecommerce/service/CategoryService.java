package com.orcnaydn.ecommerce.service;

import com.orcnaydn.ecommerce.dto.category.CreateUpdateCategoryDto;
import com.orcnaydn.ecommerce.entity.Category;
import com.orcnaydn.ecommerce.exception.AlreadyExistsException;
import com.orcnaydn.ecommerce.exception.NoAuthorityException;
import com.orcnaydn.ecommerce.exception.ResourceNotFoundException;
import com.orcnaydn.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CreateUpdateCategoryDto dto) {
        if (isCategoryNameExists(dto.getName()))
            throw new AlreadyExistsException("This category already exists!");

        Category category = new Category();
        category.setName(dto.getName());

        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, CreateUpdateCategoryDto dto) {
        if (isCategoryNameExists(dto.getName()))
            throw new AlreadyExistsException("This category already exists!");

        Category category = getCategoryById(categoryId);

        category.setName(dto.getName());

        return categoryRepository.save(category);
    }

    public String deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);

        if (!userService.isCurrentAuthenticatedUserAdmin())
            throw new NoAuthorityException("You don't have permission!");

        categoryRepository.delete(category);

        return "Category successfully deleted!";
    }

    private boolean isCategoryNameExists(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

    public Category getCategoryIfExists(Long categoryId) {
        return (categoryId != null) ? getCategoryById(categoryId) : null;
    }
}