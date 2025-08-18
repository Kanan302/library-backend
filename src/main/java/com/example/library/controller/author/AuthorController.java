package com.example.library.controller.author;

import com.example.library.config.ApiResponse;
import com.example.library.dto.author.request.AuthorRequestDto;
import com.example.library.dto.author.response.AuthorResponseDto;
import com.example.library.service.author.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorResponseDto>>> getAllAuthors() {
        List<AuthorResponseDto> authorResponseDtos = authorService.getAllAuthors();
        return ResponseEntity.ok(new ApiResponse<>(200, "yazicilar getirildi", authorResponseDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> getAuthorById(@PathVariable Long id) {
        AuthorResponseDto authorResponseDto = authorService.getAuthorById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "yazici getirildi", authorResponseDto));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> createAuthor(@RequestBody AuthorRequestDto authorRequestDto) {
        AuthorResponseDto authorResponseDto = authorService.createAuthor(authorRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(200, "yazici yaradildi", authorResponseDto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<String>> deleteAuthorById(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "yazici silindi", null));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<ApiResponse<AuthorResponseDto>> updateAuthorById(
            @PathVariable Long id,
            @RequestBody AuthorRequestDto authorRequestDto) {

        AuthorResponseDto updatedCategory = authorService.updateAuthorById(id, authorRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(200, "kateqoriya yeniləndi", updatedCategory));
    }
}
