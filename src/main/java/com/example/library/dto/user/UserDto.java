package com.example.library.dto.user;

import com.example.library.dto.books.response.BooksResponseToUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String email;

    private String password;

    private List<BooksResponseToUserDto> readBooks;
}
