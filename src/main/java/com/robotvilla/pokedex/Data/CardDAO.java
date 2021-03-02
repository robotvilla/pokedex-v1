package com.robotvilla.pokedex.Data;

import com.robotvilla.pokedex.Model.Card;

import java.util.List;

public interface CardDAO {

    List<Card> getAllCards();

    Card getCardById(int id);

    Card addCard(Card card);

    Card updateCard(Card card);

    List<Card> deleteCard(int id);
}
