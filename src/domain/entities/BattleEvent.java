package domain.entities;

import domain.enums.EventType;

public class BattleEvent {
    public EventType type;
    public int side = 0;
    public CardState[][] board = new CardState[3][3];

    public BattleEvent(EventType type, CardState[][] board) {
        this.type = type;
        this.board = board;
    }

    public BattleEvent(EventType type, int side) {
        this.type = type;
        this.side = side;
    }

    public BattleEvent(EventType type) {
        this.type = type;
    }
}
