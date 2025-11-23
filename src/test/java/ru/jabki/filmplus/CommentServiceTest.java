package ru.jabki.filmplus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabki.filmplus.model.Comment;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.service.CommentService;
import ru.jabki.filmplus.service.FilmService;
import ru.jabki.filmplus.service.UserService;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Test
    void createComment_valid() {
        final Film film = new Film(1, "", "", LocalDate.now(), Genre.COMEDY);
        final FilmService filmService = new FilmService();
        filmService.create(film);

        final User user = new User(1, "Name User 1","mail@mail.ru", "String login 1", LocalDate.now());
        final UserService userService = new UserService();
        userService.create(user);

        final CommentService commentService = new CommentService(userService, filmService);
        final Comment comment = new Comment(film.getId(), user.getId(), "Хороший фильм");
        commentService.getComments(film.getId());
    }
}
