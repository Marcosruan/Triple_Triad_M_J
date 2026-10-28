package application;

import domain.entities.BattleEvent;
import domain.entities.CardState;
import domain.enums.EventType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BattleEngine {

    private final CardState[][] matrix = new CardState[3][3];
    private boolean over;

    public boolean isOver() {
        return over;
    }

    public BattleEvent[] placeCard(CardState card, int[] position) {
        if (isFree(position)) {
            int l = position[0];
            int c = position[1];
            matrix[l][c] = card;
            boolean flipped = compareRanks(l, c);
            boolean same = manageSame(l, c);
            boolean ascension = compareElements(card);
            BattleEvent[] events = new BattleEvent[3];

            events[0] = new BattleEvent(EventType.PLACED, matrix);
            if (flipped || same) events[1] = new BattleEvent(EventType.FLIPPED);
            if (ascension) events[2] = new BattleEvent(EventType.BONUS);
            verifyOver();
            return events;
        }
        return new BattleEvent[]{new BattleEvent(EventType.FAILED)};
    }

    public boolean isFree(int[] position) {
        int l = position[0];
        int c = position[1];
        return matrix[l][c] == null;
    }

    private boolean compareRanks(int l, int c) {
        boolean flipped = false;
        CardState currentCard = matrix[l][c];
        int j = 2, k = 1;
        for (int i = -1; i < 2; i += 2) {
            int nl = l + i;
            int nc = c + i;
            if (nl >= 0 && nl <= 2) {
                if (matrix[nl][c] != null) {
                    CardState neighbor = matrix[nl][c];
                    if (currentCard.getRanks()[k + i] > neighbor.getRanks()[j - i]) {
                        if (currentCard.getSide() != neighbor.getSide()) {
                            neighbor.setSide(currentCard.getSide());
                            flipped = true;
                        }
                    }
                }
            }
            if (nc >= 0 && nc <= 2) {
                if (matrix[l][nc] != null) {
                    CardState neighbor = matrix[l][nc];
                    if (currentCard.getRanks()[j + i] > neighbor.getRanks()[k - i]) {
                        if (currentCard.getSide() != neighbor.getSide()) {
                            neighbor.setSide(currentCard.getSide());
                            flipped = true;
                        }
                    }
                }
            }
            j--;
            k++;
        }
        return flipped;
    }

    private boolean manageSame(int l, int c) {
        int currentCardSide = matrix[l][c].getSide();
        List<CardState> sameNeighbors = getSameNeighbors(l, c);
        if (sameNeighbors.size() >= 2) {
            boolean flipped = false;
            for (CardState card: sameNeighbors) {
                if (card.getSide() != currentCardSide) {
                    card.setSide(currentCardSide);
                    flipped = true;
                }
            }
            return flipped;
        }
        return false;
    }

    private List<CardState> getSameNeighbors(int l, int c) {
        List<CardState> neighbors = new ArrayList<>();
        CardState currentCard = matrix[l][c];
        int j = 2, k = 1;
        for (int i = -1; i < 2; i += 2) {
            int nl = l + i;
            int nc = c + i;
            if (nl >= 0 && nl <= 2) {
                if (matrix[nl][c] != null) {
                    CardState neighbor = matrix[nl][c];
                    if (currentCard.getRanks()[k + i] == neighbor.getRanks()[j - i]) {
                        neighbors.add(neighbor);
                    }
                }
            }
            if (nc >= 0 && nc <= 2) {
                if (matrix[l][nc] != null) {
                    CardState neighbor = matrix[l][nc];
                    if (currentCard.getRanks()[j + i] == neighbor.getRanks()[k - i]) {
                        neighbors.add(neighbor);
                    }
                }
            }
            j--;
            k++;
        }
        return neighbors;
    }

    private boolean compareElements(CardState currentCard) {
        boolean applied = false;
        for (CardState[] line : matrix) {
            for (CardState card: line) {
                if (card != null && card != currentCard) {
                    if (card.getElement() == currentCard.getElement()) {
                        card.setBonus(); //ascension
                        applied = true;
                    }
                }
            }
        }
        if (applied) currentCard.setBonus();
        return applied;
    }

    public void verifyOver() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == null) {
                    over = false;
                    return;
                }
            }
        }
        over = true;
    }

    public void clear() {
        over = false;
        for (CardState[] line : matrix) {
            Arrays.fill(line, null);
        }
    }

    public int getPoints(int side) {
        int cont = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] != null && matrix[i][j].getSide() == side) {
                    cont++;
                }
            }
        }
        return cont;
    }
}
