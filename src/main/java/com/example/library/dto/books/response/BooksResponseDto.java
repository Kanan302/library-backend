package com.example.library.dto.books.response;

import com.example.library.dto.author.response.AuthorResponseToBookDto;
import com.example.library.dto.category.response.CategoryResponseToBookDto;
import com.example.library.dto.user.UserResponseToBooksDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BooksResponseDto {
    private Long id;

    private String title;

    private String imageUrl;

    private LocalDate readDate;

    private String review;

    private AuthorResponseToBookDto author;

    private CategoryResponseToBookDto category;

    private List<UserResponseToBooksDto> users;
}
