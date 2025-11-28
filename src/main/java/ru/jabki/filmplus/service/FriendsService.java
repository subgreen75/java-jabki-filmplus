package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jabki.filmplus.exception.FriendsException;
import ru.jabki.filmplus.model.Friends;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.repository.FriendsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class FriendsService {
    private final UserService userService;
    private final FriendsRepository friendsRepository;


    public Friends create(Friends friends) {
        validate(friends);
        return friendsRepository.insert(friends);
    }

    public List<Friends> findFriendsById(User user) {

        return friendsRepository.findByUserId(user.getId());
    }

    private void validate(Friends friends) {
        if (friends.getUserId() == friends.getFriendUserId()) {
            throw new FriendsException("Нельзя добавлять самого себя в друзья");
        }
        User fromUser = userService.getById(friends.getUserId());
        User toUser = userService.getById(friends.getFriendUserId());
        if (friendsRepository.existsFriendOnUsers(fromUser.getId(), toUser.getId())) {
            throw new FriendsException("Пользователь с id " + fromUser.getId() + " уже дружит с пользователем с id " + toUser.getId());
        }

    }
}