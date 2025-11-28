package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.User;

@Repository
@AllArgsConstructor
public class UserRepository {

    private static final String INSERT = """
       INSERT INTO filmplus.user(login, name, email, birthday)
       VALUES (:login, :name, :email, :birthday)
       RETURNING *;   
    """;

    private static final String UPDATE = """
            UPDATE filmplus.user
            SET login = :login, name = :name, email = :email, birthday = :birthday
            WHERE id = :id
            RETURNING *;
            """;

    private static final String DELETE = """
            DELETE FROM filmplus.user
            WHERE id = :id;
            """;
    private static final String GET_BY_ID = """
            SELECT * 
            FROM filmplus.user
            WHERE id = :id;
            """;

    private NamedParameterJdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    public User insert(final User user) {
        return jdbcTemplate.queryForObject(INSERT, userToSql(user), userMapper);
    }

    public User update(final User user) {
        return jdbcTemplate.queryForObject(UPDATE, userToSql(user), userMapper);
    }

    public void delete(final Long id) {
        try {
            jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
        }
        catch (Exception e) {
            throw new BadRequestException(String.format("Пользователь с id: %d не найден", id));
        }
    }

    public User getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), userMapper);
        }
        catch (Exception e) {
            throw new BadRequestException(String.format("Пользователь с id: %d не найден", id));
        }
    }

    private MapSqlParameterSource userToSql(final User user) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", user.getId());
        params.addValue("name", user.getName());
        params.addValue("email", user.getEmail());
        params.addValue("login", user.getLogin());
        params.addValue("birthday", user.getBirthday());
        return params;
    }
}