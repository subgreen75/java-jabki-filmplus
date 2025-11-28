package ru.jabki.filmplus.model;

public class Comment {
    private Long filmId;
    private Long userId;
    private String comment;

    public Comment(Long filmId, Long userId, String comment ) {
        this.filmId = filmId;
        this.userId = userId;
        this.comment = comment;
    }

    public Long getFilmId() {
        return this.filmId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getComment() {
        return this.comment;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
