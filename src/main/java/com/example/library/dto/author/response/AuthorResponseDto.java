package com.example.library.dto.author.response;

import com.example.library.dto.books.response.BookResponseToAuthorDto;
import com.example.library.dto.category.response.CategoryResponseToAuthorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDto {
    private Long id;

    private String name;

    private List<CategoryResponseToAuthorDto> categories;

    private List<BookResponseToAuthorDto> books;
}
