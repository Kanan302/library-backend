package com.example.library.dto.author.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequestDto {
    private String name;

    private List<Long> categoryIds;

    private List<Long> bookIds;
}
