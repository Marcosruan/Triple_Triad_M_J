package domain.entities;

import domain.enums.ElementType;

public class Card {
    private final int id;
    private final String name;
    private final ElementType element;
    private final int[] ranks; //UP - LEFT - RIGHT - DOWN

    public Card(int id, String name, ElementType element, int[] ranks) {
        validateId(id);
        validateName(name);
        validateRanks(ranks);
        validateElement(element);
        this.id = id;
        this.name = name;
        this.element = element;
        this.ranks = ranks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ElementType getElement() {
        return element;
    }

    public int[] getRanks() {
        return ranks;
    }

    private void validateId(int value) {
        if (value <= 0) throw new IllegalArgumentException("Invalid id.");
    }

    private void validateName(String text) {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("Invalid name.");
    }

    private void validateElement(ElementType element) {
        if (element == null) {
            throw new IllegalArgumentException("Invalid element.");
        }
    }

    private void validateRanks(int[] ranks) {
        for (int rank: ranks) {
            if (rank <= 0) throw new IllegalArgumentException("Invalid rank value.");
        }
    }
}
