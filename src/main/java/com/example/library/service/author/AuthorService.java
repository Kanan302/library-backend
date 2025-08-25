package com.example.library.service.author;

import com.example.library.dto.author.request.AuthorRequestDto;
import com.example.library.dto.author.response.AuthorResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuthorService {
    AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto, MultipartFile imageFile);

    AuthorResponseDto getAuthorById(Long id);

    List<AuthorResponseDto> getAllAuthors();

    void deleteAuthorById(Long id);

    AuthorResponseDto updateAuthorById(Long id, AuthorRequestDto authorRequestDto,  MultipartFile imageFile);
}
