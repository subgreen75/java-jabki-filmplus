package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Film;

import java.util.List;


@Repository
@AllArgsConstructor
public class FilmRepository {

    private static final String INSERT = """
            INSERT INTO filmplus.film(name, description, duration, releasedate, genres)
            VALUES (:name, :description, :duration, :releasedate, :genres)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE filmplus.film
            SET name = :name, description = :description, duration = :duration, release = :release, genres = :genres
            WHERE id = :id
            RETURNING *;
            """;

    private static final String DELETE = """
            DELETE FROM filmplus.film
            WHERE id = :id;
            """;

    private static final String GET_BY_ID = """
            SELECT * 
            FROM filmplus.film
            WHERE id = :id;
            """;
    private static final String SEARCH = """
            SELECT * 
            FROM filmplus.film
            WHERE upper(name) like upper('%'||?||'%')
            and  extract(YEAR from releasedate) = ?;
            """;

    private NamedParameterJdbcTemplate jdbcTemplate;
    private JdbcTemplate jdbcTemplateWithoutParams;
    private final FilmMapper filmMapper;

    public Film insert (Film film) {
        return jdbcTemplate.queryForObject(INSERT, filmToSql(film), filmMapper);
    }

    public Film update (Film film) {
        return jdbcTemplate.queryForObject(UPDATE, filmToSql(film), filmMapper);
    }

    public void delete(final Long id) {
        try {
            jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
        }
        catch (Exception e) {
            throw new BadRequestException(String.format("Фильм с id: %d не найден", id));
        }
    }

    public Film getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), filmMapper);
        }
        catch (Exception e) {
            throw new BadRequestException(String.format("Фильм с id: %d не найден", id));
        }
    }

    public List<Film> search(final String name, final int year) {
        return jdbcTemplateWithoutParams.query(SEARCH, filmMapper, name, year);
    }

    private MapSqlParameterSource filmToSql(final Film film) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", film.getId());
        params.addValue("name", film.getName());
        params.addValue("description", film.getDescription());
        params.addValue("duration", film.getDuration());
        params.addValue("releasedate", film.getReleaseDate());
        params.addValue("genres", film.getGenres().toString());
        return params;
    }
}