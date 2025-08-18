package com.example.library.controller.books;

import com.example.library.config.ApiResponse;
import com.example.library.dto.books.request.BooksRequestDto;
import com.example.library.dto.books.request.PlannedReadDateRequestDto;
import com.example.library.dto.books.response.BooksResponseDto;
import com.example.library.service.books.BooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
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

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<BooksResponseDto>> createBook(
            @RequestPart("book") String bookJson,
            @RequestPart("image") MultipartFile imageFile
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        BooksRequestDto booksRequestDto = objectMapper.readValue(bookJson, BooksRequestDto.class);

        BooksResponseDto createdBookDto = booksService.createBook(booksRequestDto, imageFile);
        return ResponseEntity.ok(new ApiResponse<>(200, "kitab yaradildi", createdBookDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<String>> deleteBookById(@PathVariable Long id) {
        booksService.deleteBookById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "kitab silindi", null));
    }

    @PostMapping("/{bookId}/user/{userId}/set-read-date")
    public ResponseEntity<ApiResponse<BooksResponseDto>> setReadDate(@PathVariable Long bookId, @PathVariable Long userId, @RequestBody PlannedReadDateRequestDto request) {

        LocalDate plannedReadDate = LocalDate.parse(request.getPlannedReadDate());
        BooksResponseDto response = booksService.setReadDate(bookId, userId, plannedReadDate);

        return ResponseEntity.ok(new ApiResponse<>(200, "Tarix qeyd olundu", response));
    }

    @PutMapping(value = "/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<BooksResponseDto>> updateBookById(
            @PathVariable Long id,
            @RequestPart("book") String bookJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        BooksRequestDto booksRequestDto = objectMapper.readValue(bookJson, BooksRequestDto.class);

        BooksResponseDto updatedBook = booksService.updateBookById(id, booksRequestDto, imageFile);
        return ResponseEntity.ok(new ApiResponse<>(200, "Kitab yeniləndi", updatedBook));
    }
}
