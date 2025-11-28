package ru.jabki.filmplus.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabki.filmplus.model.Friends;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FriendMapper implements RowMapper<Friends> {

    @Override
    public Friends mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Friends.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .friendUserId(rs.getLong("friend_user_id"))
                .build();
    }
}