package com.example.library.service.books;

import com.example.library.dto.books.request.BooksRequestDto;
import com.example.library.dto.books.response.BooksResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface BooksService {
    BooksResponseDto createBook(BooksRequestDto booksRequestDto, MultipartFile imageFile);

    BooksResponseDto getBookById(Long id);

    List<BooksResponseDto> getAllBooks();

    void deleteBookById(Long id);

    BooksResponseDto setReadDate(Long bookId, Long userId, LocalDate plannedReadDate);

    BooksResponseDto updateBookById(Long bookId, BooksRequestDto booksRequestDto, MultipartFile imageFile);
}
