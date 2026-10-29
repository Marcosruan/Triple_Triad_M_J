package displayers;

import domain.entities.CardState;

public class BoardDisplayer {
	
	private static final int CELL_WIDTH = 20;

	private static final String RESET = "\u001B[0m";
	private static final String RED = "\u001B[31m";
	private static final String BLUE = "\u001B[34m";
	    

public void displayBoard(CardState[][] board) {

    if (board == null || board.length != 3) {
        throw new IllegalArgumentException("O tabuleiro deve ser 3x3.");
    }

    for (int row = 0; row < 3; row++) {
        if (board[row] == null || board[row].length != 3) {
            throw new IllegalArgumentException("O tabuleiro deve ser 3x3.");
        }
    }

    for (int row = 0; row < 3; row++) {

        printBorder();

        // Nome das cartas
        for (int col = 0; col < 3; col++) {
            CardState card = board[row][col];
            String name = card == null ? "---" : abbreviate(card.getName());
            printCell(name, card);
        }
        System.out.println("|");

        // Ranks UP e LEFT
        for (int col = 0; col < 3; col++) {
            CardState card = board[row][col];
            String text = "";

            if (card != null) {
                int[] ranks = card.getRanks();
                text = "U:" + rank(ranks[0]) + " L:" + rank(ranks[1]);
            }

            printCell(text, card);
        }
        System.out.println("|");

        // Ranks RIGHT e DOWN
        for (int col = 0; col < 3; col++) {
            CardState card = board[row][col];
            String text = "";

            if (card != null) {
                int[] ranks = card.getRanks();
                text = "R:" + rank(ranks[2]) + " D:" + rank(ranks[3]);
            }

            printCell(text, card);
        }
        System.out.println("|");

        // Dono
        for (int col = 0; col < 3; col++) {
            CardState card = board[row][col];
            String text = card == null ? "" : "Jogador " + (card.getSide() + 1);
            printCell(text, card);
        }
        System.out.println("|");

        // Fecha a linha de células
        printBorder();
    }
}

	private void printBorder() {
		for (int col = 0; col < 3; col++) {
			System.out.print("+");
			System.out.print("-".repeat(CELL_WIDTH + 2));
		}

		System.out.println("+");
	}
    
	private void printCell(String text, CardState card) {
	    String color = "";

	    if (card != null) {
	        color = card.getSide() == 0 ? RED : BLUE;
	    }

	    text = fit(text);

	    System.out.print("| " + color + text + RESET + " ");
	}
    
    private String fit(String text) {
    	if(text == null) {
    		text= "";
    	}
    	
    	if(text.length() > CELL_WIDTH) {
    		return text.substring(0, CELL_WIDTH);
    	}
    	
    	return String.format("%-" + CELL_WIDTH + "s", text);
    }
    
    private String abbreviate(String name) {
    	if(name == null) {
    		return "";
    	}
    	
    	if(name.length() > CELL_WIDTH) {
    		return name.substring(0, CELL_WIDTH - 3) + "...";
    	}
    	
    	return name;
    }
    
    private String rank(int value) {
    	return value == 10 ? "A" : String.valueOf(value);
    }
}
