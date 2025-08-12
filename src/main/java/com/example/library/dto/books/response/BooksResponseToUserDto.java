package com.example.library.dto.books.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BooksResponseToUserDto {
    private Long id;

    private String title;
}
