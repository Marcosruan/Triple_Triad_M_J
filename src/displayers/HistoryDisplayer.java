package displayers;

import java.util.ArrayList;
import java.util.List;

import domain.entities.Player;

public class HistoryDisplayer {

    private final List<String> history = new ArrayList<>();

    public void addMatch(Player player1, Player player2, int winner) {
        String result;

        if (winner == 0) {
            result = "Empate";
        } else if (winner == 1) {
            result = "Vencedor: " + player1.getName();
        } else {
            result = "Vencedor: " + player2.getName();
        }

        String match = player1.getName() + " x "
                + player2.getName() + " - " + result;

        history.add(match);
    }

    public void displayHistory() {
        if (history.isEmpty()) {
            System.out.println("\nNenhuma partida registrada.");
            return;
        }

        System.out.println("\n===== HISTÓRICO DE PARTIDAS =====");

        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + " - " + history.get(i));
        }
    }
}