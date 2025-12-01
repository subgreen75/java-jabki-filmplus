package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
import ru.jabki.filmplus.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/film")
@Tag(name = "Фильмы")
public class FilmController {
    private final FilmService filmService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создать фильм")
    public Film create(@RequestBody Film film) {
        return filmService.create(film);
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

    @GetMapping("/name/year")
    @Operation(summary = "Найти фильм по названию и/или по году выпуска")
    public List<Film> search(@RequestParam(required = false) String name, @RequestParam(required = false) int year) {
        return filmService.search(name, year);
    }
}