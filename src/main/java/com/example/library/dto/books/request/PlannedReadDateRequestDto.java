package com.example.library.dto.books.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlannedReadDateRequestDto {
    private String plannedReadDate; // YYYY-MM-DD
}
