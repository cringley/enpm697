package com.umd.sdlc.example.sdlc_project.query;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.Nullable;

import com.umd.sdlc.example.sdlc_project.models.UserDetails;

public class UserDetailsRepository {
    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    /**
     * Query using string formatter
     * @param id
     * @return
     */
    public UserDetails getUserDetailsById(Long id) {
        String queryString = "SELECT * FROM UserDetails WHERE id = %s";
        String query = String.format(queryString, id);
        return jdbcTemplate.queryForObject(query, new UserDetailsRowMapper());
    }

    /**
     * Query using raw query string
     * @param address
     * @return
     */
    public UserDetails getUserDetailsByAddress(String address) {
        String query = "SELECT * FROM UserDetails WHERE address = '" + address + "'";
        return jdbcTemplate.queryForObject(query, new UserDetailsRowMapper());
    }

    /**
     * Query using named parameters
     * @param userId
     * @return
     */
    public UserDetails getUserDetailsByUser(Long userId) {
        String query = "SELECT * FROM UserDetails WHERE user_id = :user_id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("user_id", userId);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, new UserDetailsRowMapper());
    }

    /**
     * Insert query using string.format()
     * @param userDetails
     */
    public void insertNewUserDetails(long id, int age, String birthday, String address) {
        String queryString = "INSERT INTO UserDetails (user_id, age, birthday, address) VALUES (%s, %s, %s, %s)";
        String query = String.format(queryString, id, age, birthday, address);
        jdbcTemplate.execute(query);
    }

    /**
     * Attempting to detect SQL injection using string.format()
     * @param userDetailsId
     * @param age
     */
    public void updateUserDetailsAge(Long userDetailsId, Long age) {
        String queryString = "UPDATE UserDetails SET age = %s WHERE id = %s";
        String query = String.format(queryString, age, userDetailsId);
        jdbcTemplate.execute(query);
    }

    public class UserDetailsRowMapper implements RowMapper<UserDetails>{
        @Override
        @Nullable
        public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            final UserDetails userDetails = new UserDetails();
            userDetails.setId(rs.getLong("id"));
            userDetails.setUserId(rs.getLong("user_id"));
            userDetails.setAge(rs.getInt("age"));
            userDetails.setBirtday(rs.getString("birthday"));
            userDetails.setAddress(rs.getString("address"));
            return userDetails;
        }
    }
}
