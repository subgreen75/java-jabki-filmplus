package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Friends;
import ru.jabki.filmplus.service.FriendsService;
import ru.jabki.filmplus.service.UserService;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/friends")
@Tag(name = "Пользователи-друзья")
public class FriendsController {
    private final FriendsService friendsService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Добавить пользователя в друзья")
    public Friends create(@RequestBody Friends friends) {
        return friendsService.create(friends);
    }

    @GetMapping("/id")
    @Operation(summary = "Найти друзей пользователя по id")
    public List<Friends> findFriendsById(@RequestParam long id) {
        return friendsService.findFriendsById(userService.getById(id));
    }
}