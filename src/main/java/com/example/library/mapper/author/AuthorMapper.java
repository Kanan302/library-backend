package com.example.library.mapper.author;

import com.example.library.dto.author.request.AuthorRequestDto;
import com.example.library.dto.author.response.AuthorResponseDto;
import com.example.library.dto.books.response.BookResponseToAuthorDto;
import com.example.library.dto.category.response.CategoryResponseToAuthorDto;
import com.example.library.entity.author.Author;
import com.example.library.entity.books.Books;
import com.example.library.entity.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "categories", source = "categories")
    AuthorResponseDto toDto(Author author);

    Author toEntity(AuthorRequestDto authorDto);

    List<CategoryResponseToAuthorDto> mapCategories(List<Category> categories);

    List<BookResponseToAuthorDto> mapBooks(List<Books> books);
}
