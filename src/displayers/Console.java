package displayers;

import domain.entities.CardState;
import domain.entities.Player;

import java.util.ArrayList;
import java.util.List;

import application.BattleEngine;
import builders.CardBuilder;
import controllers.MatchManager;

import java.util.Scanner;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);
    private final HistoryDisplayer historyDisplayer = new HistoryDisplayer();

    public static CardState swapCards(Player player) {
    	DeckDisplayer deckDisplayer = new DeckDisplayer();

        while (true) {
            System.out.println("\n" + player.getName() + ", escolha uma carta para trocar:");

            deckDisplayer.displayDeck(player.getDeck());

            int option = lerNumero("Escolha uma carta (1 a " + player.getDeck().size() + "):", 1, player.getDeck().size());

            return player.getDeck().get(option - 1);
        }
    }

    public void displayMainMenu() {
        while (true) {
            System.out.println("\n===== TRIPLE TRIAD =====");
            System.out.println("1 - Iniciar partida");
            System.out.println("2 - Histórico");
            System.out.println("3 - Sair");

            int option = lerNumero("Escolha uma opção:", 1, 3);

            switch (option) {
                case 1:
                    List<Player> players = new ArrayList<>();

                    System.out.println("\n===== JOGADOR 1 =====");
                    players.add(createPlayer());

                    System.out.println("\n===== JOGADOR 2 =====");
                    players.add(createPlayer());

                    MatchManager matchManager = new MatchManager( new GameDisplayer(), new CardBuilder(), new BattleEngine(),
                        players, historyDisplayer);

                    matchManager.startDuel();

                    while (finishMatch()) {
                        matchManager.startDuel();
                    }
                    break;

                case 2:
                    displayHistory();
                    break;

                case 3:
                    System.out.println("Encerrando o jogo...");
                    return;
            }
        }
    }
    
    public boolean finishMatch(){
    	while (true) {
            System.out.println("\nDesejam jogar novamente?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");

            int option = lerNumero("Escolha uma opção:", 1, 2);

            if (option == 1) {
                return true;
            }

            return false;
        } 
    }
    
    private Player createPlayer() {
    	while (true) {
            System.out.println("Digite o nome do jogador:");
            String nome = scanner.nextLine().trim();

            if (nome.isEmpty()) {
                System.out.println("O nome nao pode ficar vazio.");
                continue;
            }

            try {
                return new Player(nome, new java.util.ArrayList<CardState>());
            } catch (IllegalArgumentException e) {
                System.out.println("Nome invalido. Tente novamente.");
            }
        }
    }

    private void displayHistory() {
        historyDisplayer.displayHistory();
    }

    public static int[] selectPlacementPosition(){
    	int row = lerNumero("Escolha a linha (1 a 3):", 1, 3);
        int col = lerNumero("Escolha a coluna (1 a 3):", 1, 3);

        return new int[]{row - 1, col - 1};
    }
    
    public static CardState selectCard(Player player) { 
    	DeckDisplayer deckDisplayer = new DeckDisplayer();

        while (true) {
            deckDisplayer.displayDeck(player.getDeck());

            int option = lerNumero("Escolha uma carta (1 a " + player.getDeck().size() + "):", 1, player.getDeck().size());

            return player.getDeck().get(option - 1);
        } 
    }
    

	private static int lerNumero(String mensagem, int minimo, int maximo) {
	
	    while (true) {
	        System.out.println(mensagem);
	
	        try {
	            int numero = Integer.parseInt(scanner.nextLine());
	            if (numero >= minimo && numero <= maximo) {
	                return numero;
	            }
	
	            System.out.println("Numero fora do intervalo. Tente novamente.");
	            
	        } catch (NumberFormatException e) {
	            System.out.println("Entrada invalida. Digite um numero.");
	        }
	    }
	}
}
