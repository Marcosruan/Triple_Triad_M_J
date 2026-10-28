package controllers;

import application.BattleEngine;
import domain.entities.BattleEvent;
import domain.entities.Card;
import domain.entities.CardState;
import domain.entities.Player;
import domain.enums.ElementType;
import domain.enums.EventType;
import builders.CardBuilder;
import displayers.Console;
import displayers.GameDisplayer;

import java.util.*;

public class MatchManager {

    private List<Card> cards = new ArrayList<>();

    private final List<Player> players;
    private final CardBuilder cardBuilder;
    private final BattleEngine battleEngine;
    private final GameDisplayer gameDisplayer;

    public MatchManager(GameDisplayer gameDisplayer, CardBuilder cardBuilder, BattleEngine battleEngine, List<Player> players) {
        this.gameDisplayer = gameDisplayer;
        this.cardBuilder = cardBuilder;
        this.battleEngine = battleEngine;
        this.players = players;
    }

    public void startDuel() {
        int cont = 0;
        setup();
        gameDisplayer.displayDecks(players);
        swapCards();
        gameDisplayer.displayGame(new BattleEvent(EventType.STARTED));

        do {
            int index = cont % 2;

            gameDisplayer.displayGame(new BattleEvent(EventType.PICKINGCARD, index));
            CardState pickedCard = Console.selectCard(players.get(index));
            gameDisplayer.displayGame(new BattleEvent(EventType.PICKEDCARD, index));

            int[] position;
            do {
                gameDisplayer.displayGame(new BattleEvent(EventType.PICKINGPOSITION));
                position = Console.selectPlacementPosition();
            } while (!battleEngine.isFree(position));
            gameDisplayer.displayGame(new BattleEvent(EventType.PICKEDPOSITION));

            BattleEvent[] events = battleEngine.placeCard(pickedCard, position);

            for (BattleEvent event : events) {
                if (event != null) {
                    gameDisplayer.displayGame(event);
                }
            }

            players.get(index).getDeck().remove(pickedCard);
            cont++;
        } while (!battleEngine.isOver());
        int winner = findWinner(players.get(0), players.get(1));
        gameDisplayer.displayGame(new BattleEvent(EventType.FINISHED, winner));
    }

    private void swapCards() {
        List<CardState> choices = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            gameDisplayer.displayGame(new BattleEvent(EventType.SWAPPING, i));
            choices.add(Console.swapCards(players.get(1 - i)));
            gameDisplayer.displayGame(new BattleEvent(EventType.SWAPPED));
        }
        CardState toPlayer1 = choices.get(0);
        CardState toPlayer2 = choices.get(1);

        toPlayer1.setSide(0);
        toPlayer2.setSide(1);

        players.get(0).setCard(toPlayer2, toPlayer1);
        players.get(1).setCard(toPlayer1, toPlayer2);
    }

    private void setup() {
        buildCards();
        battleEngine.clear();

        players.get(0).clearDeck();
        players.get(0).setDeck(buildDeck(0));

        players.get(1).clearDeck();
        players.get(1).setDeck(buildDeck(1));
    }

    private List<CardState> buildDeck(int side) {
        Random random = new Random();
        List<CardState> deck = new ArrayList<>();

        List<Card> pool = new ArrayList<>(cards);

        if (pool.isEmpty()) {
            throw new IllegalStateException("No card loaded from CSV!");
        }

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(pool.size());

            Card cardFromPool = pool.get(randomIndex);

            CardState card = setupCard(cardFromPool, side);
            deck.add(card);

            pool.remove(cardFromPool);
        }
        return deck;
    }

    private CardState setupCard(Card card, int side) {
        int id = card.getId();
        String name = card.getName();
        ElementType element = card.getElement();
        int[] ranks = card.getRanks();

        return new CardState(id, name, element, ranks, side);
    }

    private int findWinner(Player player1, Player player2) {
        int p1Points = battleEngine.getPoints(0) + player1.getDeck().size();
        int p2Points = battleEngine.getPoints(1) + player2.getDeck().size();
        if (p1Points == p2Points) return 0;
        else if (p1Points > p2Points) return 1;
        else return 2;
    }

    private void buildCards() {
        String FILEPATH = "resources/cards.csv";
        cards = cardBuilder.loadCards(FILEPATH);
    }

    public void changePlayers(List<Player> newPlayers) {
        this.players.clear();
        this.players.addAll(newPlayers);
    }
}