package com.example.library.dto.books.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseToAuthorDto {
    private Long id;

    private String title;

    private String imageBase64;

    private LocalDate readDate;

    private String review;
}
