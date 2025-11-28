package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Friends;

import java.util.List;


@Repository
@AllArgsConstructor
public class FriendsRepository {

    private static final String INSERT = """
            INSERT INTO filmplus.friends(user_id, friend_user_id)
            VALUES (:user_id, :friend_user_id)
            RETURNING *;
            """;

    private static final String FIND_BY_ID = """
            SELECT * 
            FROM filmplus.friends WHERE user_id = :user_id
           
            """;

    private NamedParameterJdbcTemplate jdbcTemplate;
    private JdbcTemplate jdbcTemplateWithOutParams;
    private final FriendMapper friendMapper;

    public Friends insert (Friends friends) {
        return jdbcTemplate.queryForObject(INSERT, friendToSql(friends), friendMapper);
    }

    public List<Friends> findByUserId(final Long userId) {
        try {
            return jdbcTemplate.query(FIND_BY_ID, new MapSqlParameterSource("user_id", userId), friendMapper);
        }
        catch (Exception e) {
            throw new BadRequestException(String.format("Пользователь с id: %d не найден", userId));
        }
    }

    //проверяет есть ли дружба между двумя пользователями
    public boolean existsFriendOnUsers(final Long userId, final Long friendUserId) {
        String sql = "SELECT COUNT(*) FROM filmplus.friends WHERE user_id = ? and friend_user_id = ?";
        if (jdbcTemplateWithOutParams.queryForObject(sql, Integer.class, userId, friendUserId) == 0) {
            return true;
        }
        return false;
    }

    private MapSqlParameterSource friendToSql(final Friends friends) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", friends.getId());
        params.addValue("user_id", friends.getUserId());
        params.addValue("friend_user_id", friends.getFriendUserId());
        return params;
    }

    private MapSqlParameterSource friendsIdsToSql(final Long userId, final Long friendUserId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        params.addValue("friend_user_id", friendUserId);
        return params;
    }
}