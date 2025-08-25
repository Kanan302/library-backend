package com.example.library.mapper.books;

import com.example.library.dto.author.response.AuthorResponseToBookDto;
import com.example.library.dto.books.request.BooksRequestDto;
import com.example.library.dto.books.response.BooksResponseDto;
import com.example.library.dto.category.response.CategoryResponseToBookDto;
import com.example.library.dto.user.UserResponseToBooksDto;
import com.example.library.entity.books.Books;
import com.example.library.entity.author.Author;
import com.example.library.entity.category.Category;
import com.example.library.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BooksMapper {

    @Mapping(source = "author", target = "author")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "readers", target = "users")
    @Mapping(source = "imageBase64", target = "imageBase64")
    BooksResponseDto toDto(Books book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "readers", ignore = true)
    @Mapping(target = "imageBase64", ignore = true)
    @Mapping(target = "review", source = "dto.review")
    @Mapping(target = "readDate", source = "dto.readDate")
    Books toEntity(BooksRequestDto dto, Author author, Category category);

    AuthorResponseToBookDto mapAuthors(Author author);
    CategoryResponseToBookDto mapCategories(Category category);

    UserResponseToBooksDto mapUsers(User user);
    List<UserResponseToBooksDto> mapUsersList(List<User> users);
}
