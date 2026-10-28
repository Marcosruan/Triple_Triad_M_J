package domain.entities;

import domain.enums.ElementType;

public class CardState {
    private final int id;
    private final String name;
    private final ElementType element;
    private final int[] ranks; //UP - LEFT - RIGHT - DOWN
    private int side = 0; //0 = player1; 1 = player2

    public CardState(int id, String name, ElementType element, int[] ranks, int side) {
        this.id = id;
        this.name = name;
        this.element = element;
        this.ranks = ranks;
        this.side = side;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        validateSide(side);
        this.side = side;
    }

    public int[] getRanks() {
        return ranks;
    }

    public void setBonus() {
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i] < 10) ranks[i]++;
        }
    }

    public ElementType getElement() {
        return element;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    private void validateSide(int side) {
        if (side < 0 || side > 1) throw new IllegalArgumentException("Invalid side value.");
    }
}
