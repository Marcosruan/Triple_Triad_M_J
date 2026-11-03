import java.util.ArrayList;
import java.util.List;

import application.BattleEngine;
import builders.CardBuilder;
import controllers.MatchManager;
import displayers.Console;
import displayers.GameDisplayer;
import displayers.HistoryDisplayer;
import domain.entities.Player;

public class Main {

    public static void main(String[] args) {

        List<Player> players = new ArrayList<>();

        GameDisplayer gameDisplayer = new GameDisplayer();
        CardBuilder cardBuilder = new CardBuilder();
        BattleEngine battleEngine = new BattleEngine();
        HistoryDisplayer historyDisplayer = new HistoryDisplayer();

        MatchManager matchManager = new MatchManager(gameDisplayer, cardBuilder, battleEngine, players, historyDisplayer);

        Console console = new Console(matchManager, historyDisplayer);

        console.displayMainMenu();
    }
}