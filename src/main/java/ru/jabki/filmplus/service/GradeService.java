package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import ru.jabki.filmplus.exception.CommentException;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.FriendsException;
import ru.jabki.filmplus.exception.GradeException;
import ru.jabki.filmplus.model.Comment;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Grade;
import ru.jabki.filmplus.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GradeService {
    private static final Set<Grade> grades = new HashSet<>();
    private final UserService userService;
    private final FilmService filmService;

    public GradeService(UserService userService, FilmService filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }

    public void addGrade(Film film, User user, Integer grade) {
        Grade gradeObj = new Grade(film.getId(), user.getId(), grade);
        validate(gradeObj);
        grades.add(gradeObj);
    }

    public Set<Grade> getGrades(Long filmId) {
        Film film = filmService.getById(filmId);
        return grades.stream().filter(grade -> grade.getFilmId().equals(filmId)).collect(Collectors.toSet());
    }

    private void validate(Grade grade)  {
       if (grade.getGrade() < 1 || grade.getGrade() > 10) {
            throw new GradeException("Оценка должна быть в пределах от 1 до 10");
       }
        if (grades.stream().anyMatch(c -> c.getUserId().equals(grade.getUserId()) && c.getFilmId().equals(grade.getFilmId()))) {
            throw new GradeException("Для пользователя id " + grade.getUserId() + " фильма id " + grade.getFilmId() + " уже есть оценка");
        }
    }
}