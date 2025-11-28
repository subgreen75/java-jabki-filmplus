package ru.jabki.filmplus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;
import ru.jabki.filmplus.service.FilmService;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {
    @Test
    void createFilm_valid() {
        final Film film = getFilm(1L, "", "", LocalDate.now(), Genre.COMEDY);
        final FilmService filmService = new FilmService();
        filmService.create(film);
    }

    private Film getFilm(Long id, String name, String description, LocalDate releaseDate, Genre genre) {
        return new Film(Math.toIntExact(id), name, description, releaseDate, genre);
    }
}
