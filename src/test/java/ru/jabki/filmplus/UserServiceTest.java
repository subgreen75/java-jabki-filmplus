package ru.jabki.filmplus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.repository.UserRepository;
import ru.jabki.filmplus.service.FilmService;
import ru.jabki.filmplus.service.UserService;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_valid() {
        final User user = getUser();

        when(userRepository.insert(user)).thenReturn(user);
        User result = userService.create(user);

        assertThat(result).isEqualTo(user);
        verify(userRepository).insert(user);
    }

    @Test
    void createUser_fail_bademail() {
        final User user = getUser();
        user.setEmail("bademail");

        final UserException exception = assertThrows(
                UserException.class,
                () -> userService.create(user)
        );

        assertEquals(exception.getMessage(), "Некорректное значение email");
        verify(userRepository, never()).insert(any());
    }

    @Test
    void getUser_valid() {
        final User user = getUser();
        when(userRepository.getById(user.getId())).thenReturn(user);
        User result = userService.getById(user.getId());
        assertThat(result).isEqualTo(user);
        verify(userRepository).getById(user.getId());
    }

    @Test
    void deleteUser_valid() {
        long id = 1L;
        doNothing().when(userRepository).delete(id);

        userService.delete(id);

        verify(userRepository).delete(id);
    }

    private User getUser() {
        return  User.builder()
                .id(1L)
                .name("Some name")
                .email("mail@mail.ru")
                .login("some login")
                .birthday(LocalDate.now())
                .build();
    }
}