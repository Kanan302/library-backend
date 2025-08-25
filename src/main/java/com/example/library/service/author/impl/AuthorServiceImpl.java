package com.example.library.service.author.impl;

import com.example.library.dto.author.request.AuthorRequestDto;
import com.example.library.dto.author.response.AuthorResponseDto;
import com.example.library.entity.author.Author;
import com.example.library.entity.category.Category;
import com.example.library.exception.AlreadyExistsException;
import com.example.library.exception.NotFoundException;
import com.example.library.mapper.author.AuthorMapper;
import com.example.library.repository.author.AuthorRepository;
import com.example.library.repository.category.CategoryRepository;
import com.example.library.service.author.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto, MultipartFile imageFile) {
        List<Author> existingAuthors = authorRepository.findAllByName(authorRequestDto.getName());
        if (!existingAuthors.isEmpty()) {
            throw new AlreadyExistsException("Yazici '" + authorRequestDto.getName() + "' artıq mövcuddur");
        }

        String base64Image;
        try {
            base64Image = java.util.Base64.getEncoder().encodeToString(imageFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Fayl Base64-a çevrilmədi", e);
        }

        Author author = authorMapper.toEntity(authorRequestDto);
        author.setImageBase64(base64Image);

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

    @Transactional
    @Override
    public AuthorResponseDto updateAuthorById(Long id, AuthorRequestDto authorRequestDto, MultipartFile imageFile) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Yazici tapılmadı: " + id));

        if (authorRequestDto.getName() != null && !authorRequestDto.getName().isBlank()) {
            author.setName(authorRequestDto.getName());
        }

        if (authorRequestDto.getReview() != null) {
            author.setReview(authorRequestDto.getReview());
        }

        if (authorRequestDto.getCategoryIds() != null && !authorRequestDto.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(authorRequestDto.getCategoryIds());
            author.setCategories(categories);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String base64Image = java.util.Base64.getEncoder().encodeToString(imageFile.getBytes());
                author.setImageBase64(base64Image);
            } catch (IOException e) {
                throw new RuntimeException("Fayl Base64-a çevrilmədi", e);
            }
        }

        Author updatedAuthor = authorRepository.save(author);
        return authorMapper.toDto(updatedAuthor);
    }
}
