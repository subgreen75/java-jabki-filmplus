package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.model.Grade;
import ru.jabki.filmplus.service.FilmService;
import ru.jabki.filmplus.service.GradeService;
import ru.jabki.filmplus.service.UserService;

import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/grades")
@Tag(name = "Оценки")
public class GradeController {
    private final FilmService filmService;
    private final UserService userService;
    private final GradeService gradeService;

    public GradeController(FilmService filmService, UserService userService, GradeService gradeService) {
        this.filmService = filmService;
        this.userService = userService;
        this.gradeService = gradeService;
    }

    @PostMapping("/{filmId}/{userId}/{grade}")
    @Operation(summary = "Добавить оценку пользователя про фильм")
    public void addGrade(@PathVariable("filmId") Long filmId, @PathVariable("userId") Long userId, @PathVariable("grade") int grade) {
        gradeService.addGrade(filmService.getById(filmId), userService.getById(userId), grade);
    }

    @GetMapping("/{filmId}")
    @Operation(summary = "Найти оценки за фильм")
    public Set<Grade> getGrades(@PathVariable("filmId") Long filmId) {
        return gradeService.getGrades(filmId);
    }
}