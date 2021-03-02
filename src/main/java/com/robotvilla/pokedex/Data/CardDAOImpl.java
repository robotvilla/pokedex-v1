package com.robotvilla.pokedex.Data;

import com.robotvilla.pokedex.Model.Card;
import com.robotvilla.pokedex.Model.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class CardDAOImpl implements CardDAO {

    final JdbcTemplate jdbcTemplate;

    @Autowired
    public CardDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Card> getAllCards() {
        final String SQL  = "SELECT p.cardId, p.name, ps.setName, p.cardNumber, p.isRare, p.quantity, ps.setId FROM Pokemon p \n" +
                "LEFT JOIN Pokemon_Set ps ON p.setId = ps.setId;";
        return jdbcTemplate.query(SQL, new CardMapper());
    }

    @Override
    public Card getCardById(int id) {
        final String SQL  = "SELECT p.cardId, p.name, ps.setName, p.cardNumber, p.isRare, p.quantity, ps.setId FROM Pokemon p \n" +
                "LEFT JOIN Pokemon_Set ps ON p.setId = ps.setId \n" +
                "WHERE p.cardId = (?);";
        return jdbcTemplate.queryForObject(SQL, new CardMapper(), id);
    }

    @Override
    public Card addCard(Card card) {
        int qty;
        if(card.getQuantity() == 0) {
            qty = 1;
            final String SQL_NO_QTY = "INSERT INTO Pokemon (name, cardNumber, isRare, quantity, setId) \n" +
                    "VALUES (?, ?, ?, ?, ?);";
            return insertQuery(card, SQL_NO_QTY, qty);
        }
        qty = card.getQuantity();
        final String INSERT_SQL = "INSERT INTO Pokemon (name, cardNumber, isRare, quantity, setId) \n" +
                   "VALUES (?, ?, ?, ?, ?);";
        return insertQuery(card, INSERT_SQL, qty);
    }

    @Override
    public Card updateCard(Card card) {
        final String SQL = "UPDATE Pokemon \n" +
                "SET name = ?, cardNumber = ?, isRare = ?, setId = ?, quantity = ? \n" +
                "WHERE cardId = ?;";
        jdbcTemplate.update(SQL, card.getName(), card.getCardNumber(), card.isRare(), card.getSet().getSetId(), card.getQuantity(), card.getCardId());
        return card;
    }

    @Override
    public List<Card> deleteCard(int id) {
        final String DELETE_SQL = "DELETE FROM Pokemon WHERE cardId = ?;";
        jdbcTemplate.update(DELETE_SQL, id);

        final String SELECT_SQL  = "SELECT p.cardId, p.name, ps.setName, p.cardNumber, p.isRare, p.quantity, ps.setId FROM Pokemon p \n" +
                "LEFT JOIN Pokemon_Set ps ON p.setId = ps.setId;";
        return jdbcTemplate.query(SELECT_SQL, new CardMapper());
    }

    public Card insertQuery(Card card, final String sql, int qty) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, card.getName());
            stmt.setString(2, card.getCardNumber());
            stmt.setBoolean(3, card.isRare());
            stmt.setInt(4, qty);
            stmt.setInt(5, card.getSet().getSetId());
            return stmt;

        }, keyHolder);

        card.setCardId(keyHolder.getKey().intValue());
        return card;
    }

    public static final class CardMapper implements RowMapper<Card> {

        @Override
        public Card mapRow(ResultSet resultSet, int i) throws SQLException {
            Set s = new Set();
            s.setSetId(resultSet.getInt("setId"));
            s.setSetName(resultSet.getString("setName"));

            Card c = new Card();
            c.setCardId(resultSet.getInt("cardId"));
            c.setName(resultSet.getString("name"));
            c.setCardNumber(resultSet.getString("cardNumber"));
            c.setRare(resultSet.getBoolean("isRare"));
            c.setQuantity(resultSet.getInt("quantity"));
            c.setSet(s);

            return c;
        }
    }




}



