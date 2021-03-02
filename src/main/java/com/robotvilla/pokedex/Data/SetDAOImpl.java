package com.robotvilla.pokedex.Data;

import com.robotvilla.pokedex.Model.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SetDAOImpl implements SetDAO {

    @Autowired
    final JdbcTemplate jdbc;

    public SetDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Set> getAllSets() {
        final String sql = "SELECT * FROM Pokemon_Set;";

        return jdbc.query(sql, new SetMapper());
    }

    @Override
    public Set getSetById(int id) {
        final String sql = "SELECT * FROM Pokemon_Set WHERE setId = ?;";
        return jdbc.queryForObject(sql, new SetMapper(), id);
    }

    public static final class SetMapper implements RowMapper<Set> {

        @Override
        public Set mapRow(ResultSet resultSet, int i) throws SQLException {
            Set s = new Set();
            s.setSetId(resultSet.getInt("setId"));
            s.setSetName(resultSet.getString("setName"));
            return s;
        }
    }
}
