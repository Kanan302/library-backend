package com.example.library.service.author.impl;

import com.example.library.dto.author.request.AuthorRequestDto;
import com.example.library.dto.author.response.AuthorResponseDto;
import com.example.library.entity.author.Author;
import com.example.library.entity.category.Category;
import com.example.library.exception.NotFoundException;
import com.example.library.mapper.author.AuthorMapper;
import com.example.library.repository.author.AuthorRepository;
import com.example.library.repository.category.CategoryRepository;
import com.example.library.service.author.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final CategoryRepository categoryRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper, CategoryRepository categoryRepository) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto) {
        Author author = authorMapper.toEntity(authorRequestDto);

        if (authorRequestDto.getCategoryIds() != null && !authorRequestDto.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(authorRequestDto.getCategoryIds());
            author.setCategories(categories);
        }

        Author saved = authorRepository.save(author);
        return authorMapper.toDto(saved);
    }

    @Override
    public AuthorResponseDto getAuthorById(Long id) {
        Author authors = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Yazici tapılmadı"));
        return authorMapper.toDto(authors);
    }

    @Override
    public List<AuthorResponseDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto).toList();
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
