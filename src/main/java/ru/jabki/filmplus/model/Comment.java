package ru.jabki.filmplus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Comment {
    private long id;
    private Long filmId;
    private Long userId;
    private String comment;
}