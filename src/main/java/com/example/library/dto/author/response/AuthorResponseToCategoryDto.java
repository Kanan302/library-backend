package com.example.library.dto.author.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseToCategoryDto {
    private Long id;

    private String name;

    private String imageBase64;

    private String review;

}
