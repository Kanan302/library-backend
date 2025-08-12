package com.example.library.controller.books;

import com.example.library.config.ApiResponse;
import com.example.library.dto.books.request.BooksRequestDto;
import com.example.library.dto.books.response.BooksResponseDto;
import com.example.library.service.books.BooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;

    public BooksController(BooksService bookService) {
        this.booksService = bookService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BooksResponseDto>>> getBooks() {
        List<BooksResponseDto> bookDtos = booksService.getAllBooks();
        return ResponseEntity.ok(new ApiResponse<>(200, "kitablar getirildi", bookDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BooksResponseDto>> getBookById(@PathVariable Long id) {
        BooksResponseDto bookDto = booksService.getBookById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "kitab getirildi", bookDto));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BooksResponseDto>> createBook(@RequestBody BooksRequestDto booksRequestDto) {
        BooksResponseDto createdBookDto = booksService.createBook(booksRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(200, "kitab yaradildi", createdBookDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<String>> deleteBookById(@PathVariable Long id) {
        booksService.deleteBookById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "kitab silindi", null));
    }

}
