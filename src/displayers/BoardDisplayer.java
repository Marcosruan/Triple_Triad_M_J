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

        // UP: rank na parte superior
        for (int col = 0; col < 3; col++) {
            CardState card = board[row][col];

            String text = card == null
                    ? ""
                    : rank(card.getRanks()[0]);

            printCell(center(text), card);
        }
        System.out.println("|");

     // LEFT, nome e RIGHT
        for (int col = 0; col < 3; col++) {
            CardState card = board[row][col];

            String text;

            if (card == null) {
                text = center("---");
            } else {
                int[] ranks = card.getRanks();

                String left = rank(ranks[1]);
                String name = abbreviate(card.getName());
                String right = rank(ranks[2]);

                text = left + " " + center(name, 16) + " " + right;
            }

            printCell(text, card);
        }
        System.out.println("|");

     // DOWN: rank na parte inferior
        for (int col = 0; col < 3; col++) {
            CardState card = board[row][col];

            String text = card == null
                    ? ""
                    : rank(card.getRanks()[3]);

            printCell(center(text), card);
        }
        System.out.println("|");
        
     // ELEMENTO
        for (int col = 0; col < 3; col++) {
            CardState card = board[row][col];

            String text = card == null
                    ? ""
                    : card.getElement().toString();

            printCell(center(text), card);
        }

        System.out.println("|");

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
	
	private String center(String text) {
		return center(text, CELL_WIDTH);
	}
	
	private String center(String text, int width) {
	       if (text == null) {
	           text = "";
	       }

	       if (text.length() > width) {
	           text = text.substring(0, width);
	       }

	       int leftPadding = (width - text.length()) / 2;
	       int rightPadding = width - text.length() - leftPadding;

	       return " ".repeat(leftPadding)
	               + text
	               + " ".repeat(rightPadding);
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
