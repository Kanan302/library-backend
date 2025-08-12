package com.example.library.service.author;

import com.example.library.dto.author.request.AuthorRequestDto;
import com.example.library.dto.author.response.AuthorResponseDto;

import java.util.List;

public interface AuthorService {
    AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto);
    AuthorResponseDto getAuthorById(Long id);
    List<AuthorResponseDto> getAllAuthors();
    void deleteAuthorById(Long id);
}
