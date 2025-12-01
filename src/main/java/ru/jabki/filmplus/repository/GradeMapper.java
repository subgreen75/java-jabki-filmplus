package ru.jabki.filmplus.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabki.filmplus.model.Grade;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GradeMapper implements RowMapper<Grade> {

    @Override
    public Grade mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Grade.builder()
                .id(rs.getInt("id"))
                .filmId(rs.getLong("film_id"))
                .userId(rs.getLong("user_id"))
                .grade(rs.getInt("grade"))
                .build();
    }
}