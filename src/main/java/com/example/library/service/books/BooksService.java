package com.example.library.service.books;



import com.example.library.dto.books.request.BooksRequestDto;
import com.example.library.dto.books.response.BooksResponseDto;

import java.util.List;

public interface BooksService {
    BooksResponseDto createBook(BooksRequestDto booksRequestDto);
    BooksResponseDto getBookById(Long id);
    List<BooksResponseDto> getAllBooks();
    void deleteBookById(Long id);
}
