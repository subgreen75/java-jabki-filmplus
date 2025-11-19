package ru.jabki.filmplus.service;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.model.Utils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Set<User> users = new HashSet<>();

    public User create(User user) {
        validate(user);
        user.setId((long) users.size() + 1);
        users.add(user);
        return user;
    }

    public static User getById(long id) {
        final User user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst().orElse(null);
        if (user == null) {
            throw new UserException("Пользователь по id " + id + " не найден");
        }
        return user;
    }

    public void delete(long id) {
        users.remove(getById(id));
    }

    public User update(User user) {
        validate(user);
        User existsUser = getById(user.getId());
        existsUser.setName(user.getName());
        existsUser.setEmail(user.getEmail());
        existsUser.setLogin(user.getLogin());
        existsUser.setBirthday(user.getBirthday());
        return existsUser;
    }

    private void validate(User user) {
        if (user == null) {
            throw new UserException("Пользователь не может быть пустым");
        }

        if (!StringUtils.hasText(user.getName())) {
            throw new UserException("Имя пользователя не может быть пустым");
        }

        if (!StringUtils.hasText(user.getEmail())) {
            throw new UserException("Емейл пользователя не может быть пустым");
        }

        if (!StringUtils.hasText(user.getLogin())) {
            throw new UserException("Логин пользователя не может быть пустым");
        }

        if (user.getBirthday() == null) {
            throw new UserException("Дата рождения не может быть пустой");
        }
        if (!Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                .matcher(user.getEmail())
                .matches()) {
            throw new UserException("Некорректное значение email");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new UserException("Дата рождения не может быть в будущем");
        }
    }

    public void addFriends(long fromUserId, long toUserId) {
        User fromUser = getById(fromUserId);
        Set<Long> friends = fromUser.getFriends();
        friends.add(toUserId);
        fromUser.setFriends(friends);
    }

    public Set<Long> getFriendsById(User user) {
        return user.getFriends();
    }
}