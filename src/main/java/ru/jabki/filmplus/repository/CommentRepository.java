package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.model.Comment;

import java.util.List;

@Repository
@AllArgsConstructor
public class CommentRepository {

    private static final String INSERT = """
            INSERT INTO filmplus.comment(film_id, user_id, comment)
            VALUES (:film_id, :user_id, :comment)
            RETURNING *;
            """;

    private static final String FIND_BY_FILM_ID = """
            SELECT * 
            FROM filmplus.comment
            WHERE film_id = :film_id;
            """;
    private final CommentMapper commentMapper;

    private NamedParameterJdbcTemplate jdbcTemplate;
    private JdbcTemplate jdbcTemplateWithOutParams;

    public Comment insert (Comment comment) {
        return jdbcTemplate.queryForObject(INSERT, commentToSql(comment), commentMapper);
    }

    public List<Comment> getComments(Long filmId) {
        return jdbcTemplate.query(FIND_BY_FILM_ID, new MapSqlParameterSource("film_id", filmId), commentMapper);
    }

    //проверяет есть ли отзыв у фильма у пользователя
    public boolean existsCommentOnUser(final Long filmId, final Long userId) {
        String sql = "SELECT COUNT(*) FROM filmplus.comment WHERE film_id = ? and user_id = ? ";
        if (jdbcTemplateWithOutParams.queryForObject(sql, Integer.class, filmId, userId) == 0) {
            return true;
        }
        return false;
    }

    private MapSqlParameterSource commentToSql(final Comment comment) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", comment.getId());
        params.addValue("film_id", comment.getFilmId());
        params.addValue("user_id", comment.getUserId());
        params.addValue("comment", comment.getComment());
        return params;
    }
}