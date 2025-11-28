package ru.jabki.filmplus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.service.FilmService;
import ru.jabki.filmplus.service.UserService;

import java.time.LocalDate;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Test
    void createUser_valid() {
        final User user = getUser();
        final UserService userService = new UserService();
        userService.create(user);
    }

    private User getUser() {
        return new User(1, "Name User 1","mail@mail.ru", "String login 1", LocalDate.now());
    }
}
