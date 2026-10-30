
package displayers;

import java.util.List;

import domain.entities.BattleEvent;
import domain.entities.Player;
import domain.enums.EventType;

public class GameDisplayer {

    private final BoardDisplayer boardDisplayer;
    private final DeckDisplayer deckDisplayer;

    public GameDisplayer() {
        this.boardDisplayer = new BoardDisplayer();
        this.deckDisplayer = new DeckDisplayer();
    }

    public void displayGame(BattleEvent event) {

        if (event == null || event.type == null) {
            throw new IllegalArgumentException("Evento inválido.");
        }

        switch (event.type) {

            case STARTED:
                System.out.println("\n===== A PARTIDA COMEÇOU! =====");
                break;

            case PLACED:
                System.out.println("\nCarta posicionada!");
                boardDisplayer.displayBoard(event.board);
                break;

            case PICKINGCARD:
                System.out.println("\nEscolha uma carta do seu deck.");
                break;

            case PICKEDCARD:
                System.out.println("\nCarta selecionada!");
                break;

            case PICKINGPOSITION:
                System.out.println("\nEscolha uma posição no tabuleiro.");
                boardDisplayer.displayBoard(event.board);
                break;

            case PICKEDPOSITION:
                System.out.println("\nPosição selecionada!");
                break;

            case FLIPPED:
                System.out.println("\nUma ou mais cartas foram viradas!");
                boardDisplayer.displayBoard(event.board);
                break;

            case BONUS:
                System.out.println("\nBônus elemental! Os ranks receberam +1.");
                boardDisplayer.displayBoard(event.board);
                break;

            case FAILED:
                System.out.println("\nJogada inválida ou posição ocupada!");
                break;

            case FINISHED:
                System.out.println("\n===== FIM DE PARTIDA =====");

                if (event.side == 0) {
                    System.out.println("A partida terminou em empate!");
                } else {
                    System.out.println("Jogador " + event.side + " venceu!");
                }
                break;

            case SWAPPING:
                System.out.println("\nPreparando troca de cartas...");
                break;

            case SWAPPED:
                System.out.println("\nTroca de cartas realizada!");
                break;
        }
    }

    public void displayDecks(List<Player> players) {

        if (players == null || players.isEmpty()) {
            System.out.println("Nenhum jogador encontrado.");
            return;
        }

        for (Player player : players) {

            System.out.println("\nDeck de " + player.getName() + ":");

            deckDisplayer.displayDeck(player.getDeck());
        }
    }
}
