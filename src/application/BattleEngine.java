package application;

import domain.entities.BattleEvent;
import domain.entities.CardState;
import domain.enums.EventType;

import java.util.Arrays;

public class BattleEngine {
    private final int UP = 0;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private final int DOWN = 3;

    private final CardState[][] matrix = new CardState[3][3];
    private boolean over;

    public boolean isOver() {
        return over;
    }

    public BattleEvent[] placeCard(CardState card, int[] position) {
        int l = position[0];
        int c = position[1];

        if (isFree(position)) {
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

    private boolean manageSame(int l, int c) {
        if (manageSameUp(l, c, l - 1)) return true;
        if (manageSameLeft(l, c, c - 1)) return true;
        return manageSameDown(l, c, l + 1);
    }

    private boolean manageSameUp(int l, int c, int nl) {
        if (nl >= 0) {
            if (matrix[nl][c] != null) {
                boolean flipped = false;
                boolean same = false;
                CardState currentCard = matrix[l][c];
                CardState aboveCard = matrix[nl][c];
                boolean sameSide = aboveCard.getSide() == currentCard.getSide();

                if (currentCard.getRanks()[UP] == aboveCard.getRanks()[DOWN]) {
                    if (sameLeft(l, c, c - 1)) {
                        if (!sameSide) {
                            aboveCard.setSide(currentCard.getSide());
                            flipped = true;
                        }
                        same = true;
                    }
                    if (sameDown(l, c, l + 1)) {
                        if (!sameSide && !flipped) {
                            aboveCard.setSide(currentCard.getSide());
                            flipped = true;
                        }
                        same = true;
                    }
                    if (sameRight(l, c, c + 1)) {
                        if (!sameSide && !flipped) {
                            aboveCard.setSide(currentCard.getSide());
                        }
                        same = true;
                    }
                    return same;
                }
            }
        }
        return false;
    }

    private boolean manageSameLeft(int l, int c, int nc) {
        if (nc >= 0) {
            if (matrix[l][nc] != null) {
                boolean flipped = false;
                boolean same = false;
                CardState currentCard = matrix[l][c];
                CardState leftCard = matrix[l][nc];
                boolean sameSide = leftCard.getSide() == currentCard.getSide();

                if (currentCard.getRanks()[LEFT] == leftCard.getRanks()[RIGHT]) {
                    if (sameDown(l, c, l + 1)) {
                        if (!sameSide) {
                            leftCard.setSide(currentCard.getSide());
                            flipped = true;
                            same = true;
                        }
                    }
                    if (sameRight(l, c, c + 1)) {
                        if (!sameSide && !flipped) {
                            leftCard.setSide(currentCard.getSide());
                            same = true;
                        }
                    }
                    return same;
                }
            }
        }
        return false;
    }

    private boolean sameLeft(int l, int c, int nc) {
        if (nc >= 0) {
            if (matrix[l][nc] != null) {
                CardState currentCard = matrix[l][c];
                CardState leftCard = matrix[l][nc];

                if (currentCard.getRanks()[LEFT] == leftCard.getRanks()[RIGHT]) {
                    leftCard.setSide(currentCard.getSide());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean sameRight(int l, int c, int nc) {
        if (nc <= 2) {
            if (matrix[l][nc] != null) {
                CardState currentCard = matrix[l][c];
                CardState rightCard = matrix[l][nc];

                if (currentCard.getRanks()[RIGHT] == rightCard.getRanks()[LEFT]) {
                    rightCard.setSide(currentCard.getSide());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean manageSameDown(int l, int c, int nl) {
        if (nl <= 2) {
            if (matrix[nl][c] != null) {
                CardState currentCard = matrix[l][c];
                CardState belowCard = matrix[nl][c];
                boolean sameSide = belowCard.getSide() == currentCard.getSide();

                if (currentCard.getRanks()[DOWN] == belowCard.getRanks()[UP]) {
                    if (sameRight(l, c, c + 1)) {
                        if (!sameSide) {
                            belowCard.setSide(currentCard.getSide());
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean sameDown(int l, int c, int nl) {
        if (nl <= 2) {
            if (matrix[nl][c] != null) {
                CardState currentCard = matrix[l][c];
                CardState belowCard = matrix[nl][c];

                if (currentCard.getRanks()[DOWN] == belowCard.getRanks()[UP]) {
                    belowCard.setSide(currentCard.getSide());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean compareRanks(int l, int c) {
        boolean up = compareUp(l, c, l - 1);
        boolean left = compareLeft(l, c, c - 1);
        boolean right = compareRight(l, c, c + 1);
        boolean down = compareDown(l, c, l + 1);
        return up || left || right || down;
    }

    private boolean compareDown(int l, int c, int nl) {
        if (nl <= 2) {
            if (matrix[nl][c] != null) {
                boolean greater = matrix[l][c].getRanks()[DOWN] > matrix[nl][c].getRanks()[UP];
                boolean sameSide = matrix[nl][c].getSide() == matrix[l][c].getSide();
                if (greater && !sameSide) {
                    matrix[nl][c].setSide(matrix[l][c].getSide());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean compareRight(int l, int c, int nc) {
        if (nc <= 2) {
            if (matrix[l][nc] != null) {
                boolean greater = matrix[l][c].getRanks()[RIGHT] > matrix[l][nc].getRanks()[LEFT];
                boolean sameSide = matrix[l][nc].getSide() == matrix[l][c].getSide();
                if (greater && !sameSide) {
                    matrix[l][nc].setSide(matrix[l][c].getSide());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean compareLeft(int l, int c, int nc) {
        if (nc >= 0) {
            if (matrix[l][nc] != null) {
                boolean greater = matrix[l][c].getRanks()[LEFT] > matrix[l][nc].getRanks()[RIGHT];
                boolean sameSide = matrix[l][nc].getSide() == matrix[l][c].getSide();
                if (greater && !sameSide) {
                    matrix[l][nc].setSide(matrix[l][c].getSide());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean compareUp(int l, int c, int nl) {
        if (nl >= 0) {
            if (matrix[nl][c] != null) {
                boolean greater = matrix[l][c].getRanks()[UP] > matrix[nl][c].getRanks()[DOWN];
                boolean sameSide = matrix[nl][c].getSide() == matrix[l][c].getSide();
                if (greater && !sameSide) {
                    matrix[nl][c].setSide(matrix[l][c].getSide());
                    return true;
                }
            }        }
        return false;
    }

    private boolean compareElements(CardState currentCard) {
        boolean applied = false;
        for (CardState[] line : matrix) {
            for (CardState card: line) {
                if (card != null && card.getId() != currentCard.getId()) {
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
                if (matrix[i][j] != null) {
                    if (matrix[i][j].getSide() == side) {
                        cont++;
                    }
                }
            }
        }
        return cont;
    }
}
