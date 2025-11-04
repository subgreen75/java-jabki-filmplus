package ru.jabki.filmplus.model;

import ru.jabki.filmplus.exception.FilmException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Genre> genres;

    public Film(Long id, String name, String description, String releaseDate, int duration, Set<Genre> genres) {
        validateFilm(releaseDate);
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.duration = duration;
        this.genres = genres;
    }

    private static void validateFilm(String releaseDate) {
        if (!Utils.isValidDate(releaseDate, "yyyy-MM-dd")) {
            throw new FilmException("Ошибка форматирования даты или некорректный месяц или день. Дата должна быть в формате yyyy-MM-dd");
        }
        if (LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).isAfter(LocalDate.now())) {
            throw new FilmException("Дата рождения не может быть в будущем");
        }
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public int getDuration() {
        return this.duration;
    }

    public Set<Genre> getGenres() {
        return this.genres;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
}