package ru.jabki.filmplus.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class FilmMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        // преобразуем строку [жанр1, жанр2] в массив строк, а затем в Set<Genre>
        String genresString = rs.getString("genres");
        genresString = genresString.substring(1, genresString.length() - 1);
        String[] genresStrings = genresString.split(", ");
        Set<Genre> genres = new HashSet<>();
        for (String genreString : genresStrings) {
            genres.add(Genre.valueOf(genreString.toUpperCase()));
        }
        return Film.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("releasedate").toLocalDate())
                .duration(rs.getInt("duration"))
                .genres(genres)
                .build();
    }
}