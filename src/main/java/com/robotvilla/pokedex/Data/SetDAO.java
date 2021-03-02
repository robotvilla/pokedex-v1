package com.robotvilla.pokedex.Data;

import com.robotvilla.pokedex.Model.Set;

import java.util.List;

public interface SetDAO {

    List<Set> getAllSets();

    Set getSetById(int id);
}
