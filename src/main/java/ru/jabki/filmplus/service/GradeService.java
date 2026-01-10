package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jabki.filmplus.exception.GradeException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Grade;
import ru.jabki.filmplus.repository.GradeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GradeService {
    private final FilmService filmService;
    private final UserService userService;
    private final GradeRepository gradeRepository;

    public void addGrade(Grade grade) {
        validate(grade);
        gradeRepository.insert(grade);
    }

    public List<Grade> getGrades(Long filmId) {
        Film film = filmService.getById(filmId);
        return gradeRepository.getGrades(filmId);
    }

    private void validate(Grade grade)  {
       if (grade.getGrade() < 1 || grade.getGrade() > 10) {
            throw new GradeException("Оценка должна быть в пределах от 1 до 10");
       }
       if (filmService.getById(grade.getFilmId()) == null) {
           throw new GradeException("Не найден фильм по id " + grade.getFilmId());
       }
        if (userService.getById(grade.getUserId()) == null) {
            throw new GradeException("Не найден пользователь по id " + grade.getFilmId());
        }
        if (!gradeRepository.existsGradeOnUser(grade.getFilmId(), grade.getUserId())) {
            throw new GradeException("Для пользователя id " + grade.getUserId() + " фильма id " + grade.getFilmId() + " уже есть оценка");
        }
    }
}