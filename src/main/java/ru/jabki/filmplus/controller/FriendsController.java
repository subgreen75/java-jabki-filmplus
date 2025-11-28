package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.service.FriendsService;
import ru.jabki.filmplus.service.UserService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/friends")
@Tag(name = "Пользователи-друзья")
public class FriendsController {
    private final FriendsService friendsService;
    private final UserService userService;

    public FriendsController(FriendsService friendsService, UserService userService) {
        this.friendsService = friendsService;
        this.userService = userService;
    }

    @PostMapping("/{fromUserId}/{toUserId}")
    @Operation(summary = "Добавить пользователя в друзья")
    public void addFriends(@PathVariable("fromUserId") Long fromUserId, @PathVariable("toUserId") Long toUserId) {
        friendsService.addFriends(fromUserId, toUserId);
    }

    @GetMapping("/id")
    @Operation(summary = "Найти друзей пользователя по id")
    public Set<Long> getFriendsById(@RequestParam long id) {
        return friendsService.getFriendsById(userService.getById(id));
    }
}
