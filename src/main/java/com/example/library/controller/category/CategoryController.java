package com.example.library.controller.category;

import com.example.library.config.ApiResponse;
import com.example.library.dto.category.request.CategoryRequestDto;
import com.example.library.dto.category.response.CategoryResponseDto;
import com.example.library.service.category.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> getCategory() {
        List<CategoryResponseDto> categoryResponseDtos = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse<>(200, "kateqoriyalar getirildi", categoryResponseDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> getCategoryById(@PathVariable Long id) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "kateqoriya getirildi", categoryResponseDto));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(200, "kateqoriya yaradildi", categoryResponseDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<String>> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "kateqoriya silindi", null));
    }
}
