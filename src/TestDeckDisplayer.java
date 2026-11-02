
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import displayers.DeckDisplayer;
import domain.entities.CardState;
import domain.enums.ElementType;

public class TestDeckDisplayer {

    public static void main(String[] args) {

        DeckDisplayer displayer = new DeckDisplayer();

        List<CardState> deck = new ArrayList<>();

        deck.add(new CardState(1, "Dragon", ElementType.FIRE,
                new int[]{5, 3, 4, 2}, 0));

        deck.add(new CardState(2, "Wizard", ElementType.WATER,
                new int[]{8, 2, 5, 6}, 0));

        deck.add(new CardState(3, "Knight", ElementType.EARTH,
                new int[]{3, 7, 2, 9}, 0));

        deck.add(new CardState(4, "Goblin", ElementType.WIND,
                new int[]{6, 4, 3, 7}, 1));

        deck.add(new CardState(5, "Phoenix", ElementType.FIRE,
                new int[]{10, 5, 8, 4}, 1));

        // Teste com 5 cartas
        System.out.println("TESTE COM 5 CARTAS:");
        displayer.displayDeck(deck);

        // Teste com 3 cartas
        System.out.println("\nTESTE COM 3 CARTAS:");
        displayer.displayDeck(deck.subList(0, 3));

        // Teste com 0 cartas
        System.out.println("\nTESTE COM 0 CARTAS:");
        displayer.displayDeck(new ArrayList<>());
    }
}
