package ru.jabki.filmplus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.repository.FilmRepository;
import ru.jabki.filmplus.repository.UserRepository;
import ru.jabki.filmplus.service.FilmService;
import ru.jabki.filmplus.service.UserService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {
    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    @Test
    void createFilm_valid() {
        final Film film = getFilm();

        Mockito.when(filmRepository.insert(film)).thenReturn(film);
        Film result = filmService.create(film);

        assertThat(result).isEqualTo(film);
        verify(filmRepository).insert(film);
    }

    @Test
    void createFilm_fail_badduration() {
        final Film film = getFilm();
        film.setDuration(0);

        final FilmException exception = assertThrows(
                FilmException.class,
                () -> filmService.create(film)
        );

        assertEquals(exception.getMessage(), "Продолжительность фильма не может быть пустой или равно 0");
        verify(filmRepository, never()).insert(any());
    }

    private Film getFilm() {
        return  Film.builder()
                .id(1L)
                .name("Some name")
                .description("some description")
                .releaseDate(LocalDate.now())
                .duration(10)
                .genres(Set.of(Genre.ANIMATION, Genre.COMEDY))
                .build();
    }
}