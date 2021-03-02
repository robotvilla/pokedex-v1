package com.robotvilla.pokedex.Controller;

import com.robotvilla.pokedex.Data.CardDAO;
import com.robotvilla.pokedex.Model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokedex")
public class Controller {

    @Autowired
    private final CardDAO cardDAO;

    public Controller(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    // get all cards
    @CrossOrigin
    @GetMapping("/cards")
    public List<Card> getAllCards() {
        return cardDAO.getAllCards();
    }

    // get cards by id
    @CrossOrigin
    @GetMapping("/card/{id}")
    public Card getCardById(@PathVariable int id) {
        return cardDAO.getCardById(id);
    }

    // add card
    @CrossOrigin
    @PostMapping(
            value = "/card",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Card addCard(@RequestBody Card card) {
        return cardDAO.addCard(card);
    }

    // update card
    @CrossOrigin
    @PutMapping("/card/{id}")
    public Card updateCard(@RequestBody Card card) {
        return cardDAO.updateCard(card);
    }

    // delete card
    @CrossOrigin
    @DeleteMapping("/card/{id}")
    public List<Card> deleteCard(@PathVariable int id) {
        return cardDAO.deleteCard(id);
    }
}
