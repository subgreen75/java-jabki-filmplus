package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Grade;

import java.util.List;

@Repository
@AllArgsConstructor
public class GradeRepository {

    private final FilmRepository filmRepository;

    private static final String INSERT = """
            INSERT INTO filmplus.grade(film_id, user_id, grade)
            VALUES (:film_id, :user_id, :grade)
            RETURNING *;
            """;

    private static final String FIND_BY_FILM_ID = """
            SELECT * 
            FROM filmplus.grade
            WHERE film_id = :film_id;
            """;

    private final GradeMapper gradeMapper;

    private NamedParameterJdbcTemplate jdbcTemplate;
    private JdbcTemplate jdbcTemplateWithOutParams;

    public Grade insert (Grade grade) {
        return jdbcTemplate.queryForObject(INSERT, gradeToSql(grade), gradeMapper);
    }

    public List<Grade> getGrades(Long filmId) {
        Film film = filmRepository.getById(filmId);
        return  jdbcTemplate.query(FIND_BY_FILM_ID, new MapSqlParameterSource("film_id", filmId), gradeMapper);
    }

    //проверяет есть ли оценка у фильма у пользователя
    public boolean existsGradeOnUser(final Long filmId, final Long userId) {
        String sql = "SELECT COUNT(*) FROM filmplus.grade WHERE film_id = ? and user_id = ? ";
        if (jdbcTemplateWithOutParams.queryForObject(sql, Integer.class, filmId, userId) == 0) {
            return true;
        }
        return false;
    }

    private MapSqlParameterSource gradeToSql(final Grade grade) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", grade.getId());
        params.addValue("film_id", grade.getFilmId());
        params.addValue("user_id", grade.getUserId());
        params.addValue("grade", grade.getGrade());
        return params;
    }
}