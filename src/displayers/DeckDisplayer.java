
package displayers;

import java.util.List;

import domain.entities.CardState;

public class DeckDisplayer {

    private static final int CARD_WIDTH = 16;
    private static final int GAP = 2;

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";

    public void displayDeck(List<CardState> players) {

        if (players == null || players.isEmpty()) {
            System.out.println("\nO deck está vazio!");
            return;
        }

        // Todas as cartas do deck usam a mesma cor.
        // Considera o lado da primeira carta.
        String color = players.get(0).getSide() == 0 ? RED : BLUE;

        System.out.println("\n============================== DECK ==============================\n");

        // Numeração centralizada sobre cada carta
        for (int i = 0; i < players.size(); i++) {
            printCell("CARTA " + (i + 1), "");
        }
        System.out.println();

        printBorders(players.size());

        // UP
        for (CardState card : players) {
            String text = card == null ? "" : rank(card.getRanks()[0]);
            printCell(text, color);
        }
        System.out.println("|");

        // LEFT, NOME e RIGHT
        for (CardState card : players) {

            if (card == null) {
                printCell("---", "");
                continue;
            }

            String left = rank(card.getRanks()[1]);
            String name = abbreviate(card.getName());
            String right = rank(card.getRanks()[2]);

            String text = left + " " + center(name, 10) + " " + right;

            printCell(text, color);
        }
        System.out.println("|");

        // DOWN
        for (CardState card : players) {
            String text = card == null ? "" : rank(card.getRanks()[3]);
            printCell(text, color);
        }
        System.out.println("|");

        // ELEMENTO
        for (CardState card : players) {
            String element = card == null
                    ? ""
                    : card.getElement().toString();

            printCell(element, color);
        }
        System.out.println("|");

        printBorders(players.size());

        System.out.println("\n==================================================================");
    }

    private void printBorders(int amount) {

        for (int i = 0; i < amount; i++) {
            System.out.print("+" + "-".repeat(CARD_WIDTH + 2) + "+");

            if (i < amount - 1) {
                System.out.print(" ".repeat(GAP));
            }
        }

        System.out.println();
    }

    private void printCell(String text, String color) {

        System.out.print("| " + color + center(text, CARD_WIDTH) + RESET + " |");

        System.out.print(" ".repeat(GAP));
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

    private String abbreviate(String name) {

        if (name == null) {
            return "";
        }

        if (name.length() > 10) {
            return name.substring(0, 7) + "...";
        }

        return name;
    }

    private String rank(int value) {
        return value == 10 ? "A" : String.valueOf(value);
    }
}
