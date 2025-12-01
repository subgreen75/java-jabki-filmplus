package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;
import ru.jabki.filmplus.repository.FilmRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;

    @Transactional(rollbackFor = Exception.class)
    public Film create(Film film) {
        validate(film);
        return filmRepository.insert(film);
    }

    @Transactional(readOnly = true)
    public Film getById(long id) {
        final Film film = filmRepository.getById(id);
        if (film == null) {
            throw new UserException("Фильм по id " + id + " не найден");
        }
        return film;
    }

    @Transactional(readOnly = true)
    public List<Film> search(String name, int year ) {
        if (StringUtils.isEmpty(name) && year == 0) {
            throw new FilmException("Хотя бы один параметр нужно ввести");
        }
        return filmRepository.search(name, year);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) {
        filmRepository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Film update(Film film) {
        validate(film);
        Film existsFilm = getById(film.getId());
        existsFilm.setName(film.getName());
        existsFilm.setDescription(film.getDescription());
        existsFilm.setReleaseDate(film.getReleaseDate());
        existsFilm.setDuration(film.getDuration());
        existsFilm.setGenres(film.getGenres());
        return filmRepository.update(film);
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
}