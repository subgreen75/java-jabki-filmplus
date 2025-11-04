package ru.jabki.filmplus.model;

import ru.jabki.filmplus.exception.UserException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class User {
    private long id;
    private String name;
    private String email;
    private String login;
    private LocalDate birthday;

    public User(long id, String name, String email, String login, String birthday) {
        validateUser(email, birthday);
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getLogin() {
        return this.login;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    private static void validateUser(String email, String birthday) {
        if (!Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                .matcher(email)
                .matches()) {
            throw new UserException("Некорректное значение email");
        }
        if (!Utils.isValidDate(birthday, "yyyy-MM-dd")) {
            throw new UserException("Ошибка форматирования даты или некорректный месяц или день. Дата должна быть в формате yyyy-MM-dd");
        }
        if (LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd")).isAfter(LocalDate.now())) {
            throw new UserException("Дата рождения не может быть в будущем");
        }
    }
}