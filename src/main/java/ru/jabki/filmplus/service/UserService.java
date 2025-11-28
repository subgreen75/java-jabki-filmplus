package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.repository.UserRepository;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public User create(User user) {
        validate(user);
        return userRepository.insert(user);
    }

    @Transactional(readOnly = true)
    public User getById(long id) {
        final User user = userRepository.getById(id);
        if (user == null) {
            throw new UserException("Пользователь по id " + id + " не найден");
        }
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) {
        userRepository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public User update(User user) {
        validate(user);
        User existsUser = getById(user.getId());
        existsUser.setName(user.getName());
        existsUser.setEmail(user.getEmail());
        existsUser.setLogin(user.getLogin());
        existsUser.setBirthday(user.getBirthday());
        return userRepository.update(user);
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
}