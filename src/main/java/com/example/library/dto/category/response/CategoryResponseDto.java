package com.example.library.dto.category.response;

import com.example.library.dto.author.response.AuthorResponseToCategoryDto;
import com.example.library.dto.books.response.BookResponseToCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;

    private String name;

    private List<AuthorResponseToCategoryDto> authors;

    private List<BookResponseToCategoryDto> books;
}
