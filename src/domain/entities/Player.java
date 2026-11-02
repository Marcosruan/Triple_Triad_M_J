package domain.entities;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private List<CardState> deck = new ArrayList<>();

    public Player(String name) {
        validateName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<CardState> getDeck() {
        return deck;
    }

    public void setDeck(List<CardState> deck) {
        validateDeck(deck);
        this.deck = deck;
    }

    public void setCard(CardState oldCard, CardState newCard) {
        if (oldCard != null && newCard != null) {
            this.deck.remove(oldCard);
            this.deck.add(newCard);
        }
    }

    public void clearDeck() {
        this.deck.clear();
    }

    private void validateName(String text) {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("Invalid name.");
    }

    private void validateDeck(List<CardState> deck) {
        for (CardState card: deck) {
            if (card == null) throw new IllegalArgumentException("Invalid card.");
        }
    }
}
