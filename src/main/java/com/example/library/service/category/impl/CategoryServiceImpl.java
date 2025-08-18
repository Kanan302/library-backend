package com.example.library.service.category.impl;

import com.example.library.dto.category.request.CategoryRequestDto;
import com.example.library.dto.category.response.CategoryResponseDto;
import com.example.library.entity.author.Author;
import com.example.library.entity.category.Category;
import com.example.library.exception.AlreadyExistsException;
import com.example.library.exception.NotFoundException;
import com.example.library.mapper.category.CategoryMapper;
import com.example.library.repository.author.AuthorRepository;
import com.example.library.repository.category.CategoryRepository;
import com.example.library.service.category.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AuthorRepository authorRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, AuthorRepository authorRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.authorRepository = authorRepository;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        List<Category> existingCategories = categoryRepository.findAllByName(categoryRequestDto.getName());
        if (!existingCategories.isEmpty()) {
            throw new AlreadyExistsException("Kateqoriya '" + categoryRequestDto.getName() + "' artıq mövcuddur");
        }

        Category category = categoryMapper.toEntity(categoryRequestDto);

        if (categoryRequestDto.getAuthorIds() != null && !categoryRequestDto.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(categoryRequestDto.getAuthorIds());
            category.setAuthors(authors);
        }

        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category categories = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Kateqoriya tapılmadı"));
        return categoryMapper.toDto(categories);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto).toList();
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponseDto updateCategoryById(Long id, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Kateqoriya tapılmadı: " + id));

        if (categoryRequestDto.getName() != null && !categoryRequestDto.getName().isBlank()) {
            category.setName(categoryRequestDto.getName());
        }

        if (categoryRequestDto.getAuthorIds() != null && !categoryRequestDto.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(categoryRequestDto.getAuthorIds());
            category.setAuthors(authors);
        }

        // Əgər gələcəkdə bookIds göndərsən, buraya da əlavə oluna bilər:
        // if (categoryRequestDto.getBookIds() != null && !categoryRequestDto.getBookIds().isEmpty()) {
        //     List<Books> books = booksRepository.findAllById(categoryRequestDto.getBookIds());
        //     category.setBooks(books);
        // }

        Category updated = categoryRepository.save(category);
        return categoryMapper.toDto(updated);
    }
}
