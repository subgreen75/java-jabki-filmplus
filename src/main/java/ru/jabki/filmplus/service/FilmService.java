package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;
import ru.jabki.filmplus.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private static final Set<Film> films = new HashSet<>();

    public Film create(Film film) {
        validate(film);
        film.setId((long) films.size() + 1);
        films.add(film);
        return film;
    }

    public Film getById(long id) {
        final Film film = films.stream()
                .filter(u -> u.getId() == id)
                .findFirst().orElse(null);
        if (film == null) {
            throw new FilmException("Фильм по id " + id + " не найден");
        }
        return film;
    }

    public Set<Film> search(String name, int year ) {
        if (StringUtils.isEmpty(name) && year == 0) {
            throw new FilmException("Хотя бы один параметр нужно ввести");
        }
        return films.stream()
                .filter(u -> u.getName().toUpperCase().contains((name == null)?u.getName().toUpperCase() : name.toUpperCase() ) &&
                                  u.getReleaseDate().getYear() == ((year == 0) ? u.getReleaseDate().getYear() : year)
                       )
                .collect(Collectors.toSet());
    }

    public void delete(long id) {
        films.remove(getById(id));
    }

    public Film update(Film film) {
        validate(film);
        Film existsFilm = getById(film.getId());
        existsFilm.setName(film.getName());
        existsFilm.setDescription(film.getDescription());
        existsFilm.setReleaseDate(film.getReleaseDate());
        existsFilm.setDuration(film.getDuration());
        existsFilm.setGenres(film.getGenres());
        return existsFilm;
    }

    private void validate(Film film) {
        if (film == null) {
            throw new FilmException("Фильм не может быть пустым");
        }

        if (!StringUtils.hasText(film.getName())) {
            throw new FilmException("Название фильма не может быть пустым");
        }

        if (!StringUtils.hasText(film.getDescription())) {
            throw new FilmException("Описание фильма не может быть пустым");
        }

        if (film.getReleaseDate() == null) {
            throw new FilmException("Дата выхода фильма не может быть пустой");
        }

        if (film.getDuration() == 0) {
            throw new FilmException("Продолжительность фильма не может быть пустой или равно 0");
        }

        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            throw new FilmException("Список жанров фильма не может быть пустым");
        }

        if (!(List.of(Genre.values()).containsAll(film.getGenres()))) {
            throw new FilmException("В списке есть неопределенный жанр. Доступные жанры: " + Arrays.toString(Genre.values()));
        }
        if (film.getReleaseDate().isAfter(LocalDate.now())) {
            throw new FilmException("Дата релиза не может быть в будущем");
        }
    }

    public void addComments(Film film, User user, String comments) {
        film.addComment(user.getId(),  comments);
    }

    public HashMap<Long, String> getComments(Long filmId) {
        return getById(filmId).getComments();
    }

    public void addGrade(Film film, User user, Integer grade) {
        if (grade == null) {
            throw new FilmException("Оценка не может быть null");
        }
        if (grade < 0 || grade > 10) {
            throw new FilmException("Оценка должна быть в пределах от 0 до 10");
        }
        film.setGrades(user.getId(), grade);
    }

    public HashMap<Long, Integer> getGrades(Long filmId) {
        return getById(filmId).getGrades();
    }
}