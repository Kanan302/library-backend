package com.example.library.mapper.category;

import com.example.library.dto.author.response.AuthorResponseToCategoryDto;
import com.example.library.dto.books.response.BookResponseToCategoryDto;
import com.example.library.dto.category.request.CategoryRequestDto;
import com.example.library.dto.category.response.CategoryResponseDto;
import com.example.library.entity.author.Author;
import com.example.library.entity.books.Books;
import com.example.library.entity.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "authors", source = "authors")
    CategoryResponseDto toDto(Category category);

    Category toEntity(CategoryRequestDto categoryRequestDto);

    List<AuthorResponseToCategoryDto> mapAuthors(List<Author> authors);

    List<BookResponseToCategoryDto> mapBooks(List<Books> books);
}
