package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.CommentException;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.Comment;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private static final Set<Comment> comments = new HashSet<>();
    private final UserService userService;
    private final FilmService filmService;

    public CommentService(UserService userService, FilmService filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }

    public void addComments(Comment comment) {
        validate(comment);
        comments.add(comment);
    }

    public Set<Comment> getComments(Long filmId) {
        Film film = filmService.getById(filmId);
        return comments.stream().filter(comment -> comment.getFilmId().equals(filmId)).collect(Collectors.toSet());
    }

    private void validate(Comment comment) {
        if (comment == null) {
            throw new CommentException("Отзыв не может быть пустым");
        }
        if (comment.getUserId() == null) {
            throw new CommentException("ID пользователя не может быть пустым");
        }
        if (comment.getFilmId() == null) {
            throw new CommentException("ID фильма не может быть пустым");
        }
        if (!StringUtils.hasText(comment.getComment())) {
            throw new CommentException("ID фильма не может быть пустым");
        }
        if (comments.stream().anyMatch(c -> c.getUserId().equals(comment.getUserId()) && c.getFilmId().equals(comment.getFilmId()))) {
            throw new CommentException("Для пользователя id " + comment.getUserId() + " фильма id " + comment.getFilmId() + " уже есть отзыв");
        }
        if (userService.getById(comment.getUserId()) == null) {
            throw new UserException("Не найден пользователь с Id " + comment.getUserId());
        }
        if (filmService.getById(comment.getFilmId()) == null) {
            throw new FilmException("Не найден фильм с Id " + comment.getFilmId());
        }
    }
}