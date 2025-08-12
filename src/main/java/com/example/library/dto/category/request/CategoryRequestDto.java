package com.example.library.dto.category.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {
    private String name;

    private List<Long> authorIds;

    private List<Long> bookIds;
}
