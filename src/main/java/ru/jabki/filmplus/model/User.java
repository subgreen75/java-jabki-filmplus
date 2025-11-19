package ru.jabki.filmplus.model;

import ru.jabki.filmplus.exception.UserException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class User {
    private long id;
    private String name;
    private String email;
    private String login;
    private LocalDate birthday;
    private Set<Long> friends;

    public User(long id, String name, String email, String login, String birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.friends = new HashSet<>();
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

    public Set<Long> getFriends() {
        return this.friends;
    }

    public void setFriends(Set<Long> friends) {
        this.friends = friends;
    }
}