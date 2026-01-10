package ru.jabki.filmplus.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabki.filmplus.model.Comment;
import ru.jabki.filmplus.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CommentMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Comment.builder()
                .id(rs.getInt("id"))
                .filmId(rs.getLong("film_id"))
                .userId(rs.getLong("user_id"))
                .comment(rs.getString("comment"))
                .build();
    }
}