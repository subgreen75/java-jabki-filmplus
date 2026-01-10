package ru.jabki.filmplus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class User {
    private long id;
    private String name;
    private String email;
    private String login;
    private LocalDate birthday;
}