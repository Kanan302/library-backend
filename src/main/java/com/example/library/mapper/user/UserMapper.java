package com.example.library.mapper.user;

import com.example.library.dto.books.response.BooksResponseToUserDto;
import com.example.library.dto.user.UserDto;
import com.example.library.entity.books.Books;
import com.example.library.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "readBooks", target = "readBooks")
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<BooksResponseToUserDto> mapBooks(List<Books> books);
}
