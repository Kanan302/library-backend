package com.example.library.dto.books.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BooksRequestDto {
    private String title;

    private String imageUrl;

    private Long authorId;

    private Long categoryId;

    private LocalDate readDate;

    private String review;
}
