package com.orcnaydn.ecommerce.controller;

import com.orcnaydn.ecommerce.dto.category.CreateUpdateCategoryDto;
import com.orcnaydn.ecommerce.entity.Category;
import com.orcnaydn.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("categories/{id}")
    public Category getCategoryById(@PathVariable("id") Long categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("categories")
    public List<Category> getCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("categories")
    public Category createCategory(@Valid @RequestBody CreateUpdateCategoryDto dto){
        return categoryService.createCategory(dto);
    }

    @PutMapping("categories/{id}")
    public Category updateCategory(@PathVariable("id") Long categoryId, @Valid @RequestBody CreateUpdateCategoryDto dto){
        return categoryService.updateCategory(categoryId,dto);
    }

    @DeleteMapping("categories/{id}")
    public String deleteCategory(@PathVariable("id") Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}