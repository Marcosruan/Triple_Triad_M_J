
import java.util.ArrayList;
import java.util.List;

import displayers.GameDisplayer;
import domain.entities.BattleEvent;
import domain.entities.CardState;
import domain.entities.Player;
import domain.enums.ElementType;
import domain.enums.EventType;

public class TestGameDisplayer {

    public static void main(String[] args) {

        GameDisplayer gameDisplayer = new GameDisplayer();

        // Criando cartas para os jogadores
        List<CardState> deck1 = new ArrayList<>();
        deck1.add(new CardState(1, "Ifrit", ElementType.FIRE,
                new int[]{8, 5, 7, 6}, 0));
        deck1.add(new CardState(2, "Shiva", ElementType.ICE,
                new int[]{6, 9, 5, 8}, 0));

        List<CardState> deck2 = new ArrayList<>();
        deck2.add(new CardState(3, "Ramuh", ElementType.THUNDER,
                new int[]{7, 6, 8, 5}, 1));
        deck2.add(new CardState(4, "Titan", ElementType.EARTH,
                new int[]{9, 5, 6, 7}, 1));

        Player player1 = new Player("Jogador 1", deck1);
        Player player2 = new Player("Jogador 2", deck2);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        // Criando tabuleiro
        CardState[][] board = new CardState[3][3];
        board[1][1] = deck1.get(0);
        board[0][1] = deck2.get(0);

        // Testando exibição dos decks
        gameDisplayer.displayDecks(players);

        // Testando início da partida
        gameDisplayer.displayGame(
                new BattleEvent(EventType.STARTED));

        // Testando escolha de carta
        gameDisplayer.displayGame(
                new BattleEvent(EventType.PICKINGCARD));

        // Testando escolha de posição
        gameDisplayer.displayGame(
                new BattleEvent(EventType.PICKINGPOSITION, board));

        // Testando carta posicionada
        gameDisplayer.displayGame(
                new BattleEvent(EventType.PLACED, board));

        // Testando cartas viradas
        gameDisplayer.displayGame(
                new BattleEvent(EventType.FLIPPED, board));

        // Testando bônus
        gameDisplayer.displayGame(
                new BattleEvent(EventType.BONUS, board));

        // Testando jogada inválida
        gameDisplayer.displayGame(
                new BattleEvent(EventType.FAILED));

        // Testando troca de cartas
        gameDisplayer.displayGame(
                new BattleEvent(EventType.SWAPPING));

        gameDisplayer.displayGame(
                new BattleEvent(EventType.SWAPPED));

        // Testando empate
        gameDisplayer.displayGame(
                new BattleEvent(EventType.FINISHED, 0));

        // Testando vitória do jogador 1
        gameDisplayer.displayGame(
                new BattleEvent(EventType.FINISHED, 1));

        // Testando vitória do jogador 2
        gameDisplayer.displayGame(
                new BattleEvent(EventType.FINISHED, 2));
    }
}
