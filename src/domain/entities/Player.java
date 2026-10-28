package domain.entities;

import java.util.List;

public class Player {
    private String name;
    private List<CardState> deck;

    public Player(String name, List<CardState> deck) {
        validateName(name);
        validateDeck(deck);
        this.name = name;
        this.deck = deck;
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
