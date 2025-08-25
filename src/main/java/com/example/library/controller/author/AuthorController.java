package com.example.library.controller.author;

import com.example.library.config.ApiResponse;
import com.example.library.dto.author.request.AuthorRequestDto;
import com.example.library.dto.author.response.AuthorResponseDto;
import com.example.library.service.author.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AuthorResponseDto>> createAuthor(
            @RequestPart("author") String authorJson,
            @RequestPart("image") MultipartFile imageFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        AuthorRequestDto authorRequestDto = objectMapper.readValue(authorJson, AuthorRequestDto.class);

        AuthorResponseDto authorResponseDto = authorService.createAuthor(authorRequestDto, imageFile);
        return ResponseEntity.ok(new ApiResponse<>(200, "Yazıçı yaradıldı", authorResponseDto));
    }


    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<String>> deleteAuthorById(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "yazici silindi", null));
    }

    @PutMapping(value = "/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AuthorResponseDto>> updateAuthorById(
            @PathVariable Long id,
            @RequestPart("author") String authorJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    )
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        AuthorRequestDto authorRequestDto = objectMapper.readValue(authorJson, AuthorRequestDto.class);

        AuthorResponseDto updatedCategory = authorService.updateAuthorById(id, authorRequestDto, imageFile);
        return ResponseEntity.ok(new ApiResponse<>(200, "kateqoriya yeniləndi", updatedCategory));
    }
}
