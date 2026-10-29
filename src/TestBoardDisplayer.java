
import displayers.BoardDisplayer;
import domain.entities.CardState;
import domain.enums.ElementType;

public class TestBoardDisplayer {

    public static void main(String[] args) {

        CardState[][] board = new CardState[3][3];

        // Uma carta na primeira posição
        board[0][0] = new CardState(
            1,
            "Dragon",
            ElementType.values()[0],
            new int[]{10, 5, 7, 3},
            0
        );

        // Outra carta na posição central
        board[1][1] = new CardState(
            2,
            "Mage",
            ElementType.values()[0],
            new int[]{4, 8, 6, 2},
            1
        );

        BoardDisplayer displayer = new BoardDisplayer();

        displayer.displayBoard(board);
    }
}
