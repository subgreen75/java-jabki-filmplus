package ru.jabki.filmplus.model;

public class Grade {
    private Long filmId;
    private Long userId;
    private int grade;

    public Grade(final Long filmId, final Long userId, final int grade) {
        this.filmId = filmId;
        this.userId = userId;
        this.grade = grade;
    }

    public Long getFilmId() {
        return this.filmId;
    }

    public void setFilmId(final Long filmId) {
        this.filmId = filmId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public int getGrade() {
        return this.grade;
    }
    public void setGrade(final int grade) {
        this.grade = grade;
    }

}
