package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.CommentException;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.Comment;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.repository.CommentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final UserService userService;
    private final FilmService filmService;

    private final CommentRepository commentRepository;

    public void addComments(Comment comment) {
        validate(comment);
        commentRepository.insert(comment);
    }

    public List<Comment> getComments(Long filmId) {
        Film film = filmService.getById(filmId);
        return commentRepository.getComments(filmId);
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
        if (!commentRepository.existsCommentOnUser(comment.getFilmId(), comment.getUserId())) {
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