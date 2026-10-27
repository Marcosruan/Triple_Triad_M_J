package displayers;

import domain.entities.CardState;
import domain.entities.Player;

import java.util.Scanner;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);

    public void displayMainMenu(){}
    public boolean finishMatch(){ return true; }
    private void createPlayer() {}

    private void displayHistory() {}
    private void updateHistory() {}

    public static int[] selectPlacementPosition(){
        return new int[]{0, 0};
    } // Retorna as posições que o jogador quer colocar a carta dele. {linha, coluna}
    public static CardState selectCard(Player player) { return null; }
}
