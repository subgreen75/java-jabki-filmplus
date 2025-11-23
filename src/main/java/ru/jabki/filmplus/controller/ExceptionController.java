package ru.jabki.filmplus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jabki.filmplus.exception.CommentException;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.FriendsException;
import ru.jabki.filmplus.exception.GradeException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.ApiError;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiError> handleUserError(final UserException userException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                userException.getMessage()
                        )
                );
    }

    @ExceptionHandler(FilmException.class)
    public ResponseEntity<ApiError> handleFilmError(final FilmException filmException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                filmException.getMessage()
                        )
                );
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ApiError> handleFilmError(final CommentException commentException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                commentException.getMessage()
                        )
                );
    }

    @ExceptionHandler(FriendsException.class)
    public ResponseEntity<ApiError> handleFilmError(final FriendsException friendsException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                friendsException.getMessage()
                        )
                );
    }

    @ExceptionHandler(GradeException.class)
    public ResponseEntity<ApiError> handleFilmError(final GradeException gradeException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                gradeException.getMessage()
                        )
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String errorMessage = "Ошибка парсинга JSON. Пожалуйста проверьте синтаксис JSON  или типы данных." + ex.getMessage();
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                errorMessage
                        )
                );
    }


}