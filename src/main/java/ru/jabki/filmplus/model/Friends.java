package ru.jabki.filmplus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Friends {
    private long id;
    private Long userId;
    private Long friendUserId;
}