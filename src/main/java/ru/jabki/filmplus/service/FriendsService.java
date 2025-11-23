package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import ru.jabki.filmplus.exception.FriendsException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.Comment;
import ru.jabki.filmplus.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class FriendsService {
    private final UserService userService;

    public FriendsService(UserService userService) {
        this.userService = userService;
    }

    public void addFriends(long fromUserId, long toUserId) {
        validate(fromUserId, toUserId);
        User fromUser = userService.getById(fromUserId);
        Set<Long> friends = fromUser.getFriends();
        friends.add(toUserId);
        fromUser.setFriends(friends);
    }

    public Set<Long> getFriendsById(User user) {
        return user.getFriends();
    }

    private void validate(long fromUserId, long toUserId) {
        if (fromUserId == toUserId) {
            throw new FriendsException("Нельзя добавлять самого себя в друзья");
        }
        User fromUser = userService.getById(fromUserId);
        User toUser = userService.getById(toUserId);
        if (fromUser.getFriends().contains(toUserId)) {
            throw new FriendsException("Пользователь с id " + fromUserId + " уже дружит с пользователем с id " + toUserId);
        }
    }
}
