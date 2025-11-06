package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.service.FilmService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/film")
@Tag(name = "Фильмы")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    @Operation(summary = "Создать фильм")
    public Film create(@RequestBody Film film) {
        return filmService.create(film);
        //return film;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить фильм по id")
    public Film getById(@PathVariable("id") Long id) {
        return filmService.getById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить фильм по id")
    public void delete(@PathVariable("id") Long id) {
        filmService.delete(id);
    }

    @PatchMapping
    @Operation(summary = "Обновление фильма")
    public Film update(@RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping("/byname")
    @Operation(summary = "Найти фильм по названию")
    public Set<Film> getByName(@RequestParam String name) {
        return filmService.getByName(name);
    }

    @GetMapping("/byyear")
    @Operation(summary = "Найти фильмы по году выпуска")
    public Set<Film> getByReleaseYear(@RequestParam int year) {
        return FilmService.getByYear(year);
    }
}