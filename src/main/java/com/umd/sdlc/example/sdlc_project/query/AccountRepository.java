package com.umd.sdlc.example.sdlc_project.query;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.umd.sdlc.example.sdlc_project.models.Account;

@Component
public class AccountRepository {
    
    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    /**
     * Query using string formatter
     * @param id
     * @return
     */
    public Account getAccountById(Long id) {
        String queryString = "SELECT * FROM Accounts WHERE id = %d";
        String query = String.format(queryString, id);
        return jdbcTemplate.queryForObject(query, new AccountRowMapper());
    }

    /**
     * Query using raw query string
     * @param accountNumber
     * @return
     */
    public Account getAccountByNumber(String accountNumber) {
        String query = "SELECT * FROM Accounts WHERE account_number = '" + accountNumber + "'";
        return jdbcTemplate.queryForObject(query, new AccountRowMapper());
    }

    /**
     * Query using named parameters
     * @param userId
     * @return
     */
    public Account getAccountByUser(Long userId) {
        String query = "SELECT * FROM Accounts WHERE user_id = :user_id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("user_id", userId);
        return namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, new AccountRowMapper());
    }

    /**
     * Query using update command
     * @param accountId
     * @param amount
     * @param numPurchased
     */
    public void updateAccountAmount(Long accountId, double price, int numPurchased) {
        double totalAmount = price * numPurchased;
        Account account = this.getAccountById(accountId);
        account.setAmount(account.getAmount() - (float)totalAmount);
        jdbcTemplate.update("UPDATE Accounts SET amount = ? WHERE id = ?", account.getAmount(), account.getId());
    }

    public class AccountRowMapper implements RowMapper<Account>{
        @Override
        @Nullable
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Account account = new Account();
            account.setId(rs.getLong("id"));
            account.setUserId(rs.getLong("user_id"));
            account.setAccountNumber(rs.getString("account_number"));
            account.setAmount(rs.getFloat("ammount"));
            return account;
        }
    }
}
