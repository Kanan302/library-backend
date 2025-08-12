package com.example.library.service.category;

import com.example.library.dto.category.request.CategoryRequestDto;
import com.example.library.dto.category.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto getCategoryById(Long id);
    List<CategoryResponseDto> getAllCategories();
    void deleteCategoryById(Long id);
}
