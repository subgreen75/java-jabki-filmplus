package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.model.Comment;
import ru.jabki.filmplus.service.CommentService;
import ru.jabki.filmplus.service.UserService;

import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "Отзывы")
public class CommentsController {
    private final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{filmId}/{userId}/{comments}")
    @Operation(summary = "Добавить отзыв пользователя про фильм")
    public void addComments(@RequestBody Comment comment) {
        commentService.addComments(comment);
    }

    @GetMapping("{filmId}")
    @Operation(summary = "Найти отзывы про фильм")
    public Set<Comment> getComments(@PathVariable("filmId") Long filmId) {
        return commentService.getComments(filmId);

    }
}