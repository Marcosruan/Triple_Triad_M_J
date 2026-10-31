
package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.entities.BattleEvent;
import domain.entities.CardState;
import domain.enums.ElementType;
import domain.enums.EventType;

public class BattleEngineTest {

    private BattleEngine engine;

    @BeforeEach
    public void setUp() {
        engine = new BattleEngine();
    }

    private CardState criarCarta(
            int id, String name, ElementType element,
            int[] ranks, int side) {

        return new CardState(id, name, element, ranks, side);
    }

    @Test
    public void testarColocacaoEmPosicaoLivre() {
        CardState card = criarCarta(
            1, "Ifrit", ElementType.FIRE,
            new int[]{5, 5, 5, 5}, 0
        );

        BattleEvent[] events = engine.placeCard(card, new int[]{1, 1});

        assertEquals(EventType.PLACED, events[0].type);
        assertEquals(1, engine.getPoints(0));
        assertEquals(0, engine.getPoints(1));
    }

    @Test
    public void testarColocacaoEmPosicaoOcupada() {
        CardState firstCard = criarCarta(
            1, "Ifrit", ElementType.FIRE,
            new int[]{5, 5, 5, 5}, 0
        );

        CardState secondCard = criarCarta(
            2, "Shiva", ElementType.ICE,
            new int[]{5, 5, 5, 5}, 1
        );

        engine.placeCard(firstCard, new int[]{1, 1});

        BattleEvent[] events = engine.placeCard(secondCard, new int[]{1, 1});

        assertEquals(1, events.length);
        assertEquals(EventType.FAILED, events[0].type);
        assertEquals(1, engine.getPoints(0));
        assertEquals(0, engine.getPoints(1));
    }

    @Test
    public void testarSePosicaoEstaLivre() {
        CardState card = criarCarta(
            1, "Ifrit", ElementType.FIRE,
            new int[]{5, 5, 5, 5}, 0
        );

        assertTrue(engine.isFree(new int[]{0, 0}));

        engine.placeCard(card, new int[]{0, 0});

        assertFalse(engine.isFree(new int[]{0, 0}));
    }

    @Test
    public void testarViradaParaCima() {
        CardState enemyCard = criarCarta(
            1, "Shiva", ElementType.ICE,
            new int[]{1, 1, 1, 5}, 1
        );

        CardState myCard = criarCarta(
            2, "Ifrit", ElementType.FIRE,
            new int[]{9, 1, 1, 1}, 0
        );

        engine.placeCard(enemyCard, new int[]{0, 1});
        engine.placeCard(myCard, new int[]{1, 1});

        assertEquals(0, enemyCard.getSide());
    }

    @Test
    public void testarViradaParaBaixo() {
        CardState enemyCard = criarCarta(
            1, "Shiva", ElementType.ICE,
            new int[]{5, 1, 1, 1}, 1
        );

        CardState myCard = criarCarta(
            2, "Ifrit", ElementType.FIRE,
            new int[]{1, 1, 1, 9}, 0
        );

        engine.placeCard(enemyCard, new int[]{2, 1});
        engine.placeCard(myCard, new int[]{1, 1});

        assertEquals(0, enemyCard.getSide());
    }
    
    @Test
    public void testarViradaParaEsquerda() {
        CardState cartaAdversaria = criarCarta(
            1, "Shiva", ElementType.ICE,
            new int[]{1, 1, 5, 1}, 1
        );

        CardState minhaCarta = criarCarta(
            2, "Ifrit", ElementType.FIRE,
            new int[]{1, 9, 1, 1}, 0
        );

        engine.placeCard(cartaAdversaria, new int[]{1, 0});
        engine.placeCard(minhaCarta, new int[]{1, 1});

        assertEquals(0, cartaAdversaria.getSide());
    }
    
    @Test
    public void testarViradaParaDireita() {
        CardState cartaAdversaria = criarCarta(
            1, "Shiva", ElementType.ICE,
            new int[]{1, 5, 1, 1}, 1
        );

        CardState minhaCarta = criarCarta(
            2, "Ifrit", ElementType.FIRE,
            new int[]{1, 1, 9, 1}, 0
        );

        engine.placeCard(cartaAdversaria, new int[]{1, 2});
        engine.placeCard(minhaCarta, new int[]{1, 1});

        assertEquals(0, cartaAdversaria.getSide());
    }
    
    @Test
    public void testarRegraSameAtivada() {
        CardState cartaCima = criarCarta(
            1, "Shiva", ElementType.ICE,
            new int[]{1, 1, 1, 5}, 1
        );

        CardState cartaEsquerda = criarCarta(
            2, "Ramuh", ElementType.THUNDER,
            new int[]{1, 1, 5, 1}, 1
        );

        CardState minhaCarta = criarCarta(
            3, "Ifrit", ElementType.FIRE,
            new int[]{5, 5, 1, 1}, 0
        );

        engine.placeCard(cartaCima, new int[]{0, 1});
        engine.placeCard(cartaEsquerda, new int[]{1, 0});
        engine.placeCard(minhaCarta, new int[]{1, 1});

        assertEquals(0, cartaCima.getSide());
        assertEquals(0, cartaEsquerda.getSide());
    }
    
    @Test
    public void testarRegraSameNaoAtivada() {
        CardState cartaCima = criarCarta(
            1, "Shiva", ElementType.ICE,
            new int[]{1, 1, 1, 5}, 1
        );

        CardState cartaEsquerda = criarCarta(
            2, "Ramuh", ElementType.THUNDER,
            new int[]{1, 1, 6, 1}, 1
        );

        CardState minhaCarta = criarCarta(
            3, "Ifrit", ElementType.FIRE,
            new int[]{5, 5, 1, 1}, 0
        );

        engine.placeCard(cartaCima, new int[]{0, 1});
        engine.placeCard(cartaEsquerda, new int[]{1, 0});
        engine.placeCard(minhaCarta, new int[]{1, 1});

        assertEquals(1, cartaCima.getSide());
        assertEquals(1, cartaEsquerda.getSide());
    }
    
    @Test
    public void testarBonusComMesmoElemento() {
        CardState primeiraCarta = criarCarta(
            1, "Ifrit", ElementType.FIRE,
            new int[]{5, 5, 5, 5}, 0
        );

        CardState segundaCarta = criarCarta(
            2, "Ifrit", ElementType.FIRE,
            new int[]{3, 3, 3, 3}, 1
        );

        engine.placeCard(primeiraCarta, new int[]{0, 0});
        engine.placeCard(segundaCarta, new int[]{1, 1});

        assertArrayEquals(new int[]{6, 6, 6, 6}, primeiraCarta.getRanks());
        assertArrayEquals(new int[]{4, 4, 4, 4}, segundaCarta.getRanks());
    }
    
    @Test
    public void testarBonusComElementosDiferentes() {
        CardState primeiraCarta = criarCarta(
            1, "Ifrit", ElementType.FIRE,
            new int[]{5, 5, 5, 5}, 0
        );

        CardState segundaCarta = criarCarta(
            2, "Shiva", ElementType.ICE,
            new int[]{3, 3, 3, 3}, 1
        );

        engine.placeCard(primeiraCarta, new int[]{0, 0});
        engine.placeCard(segundaCarta, new int[]{1, 1});

        assertArrayEquals(new int[]{5, 5, 5, 5}, primeiraCarta.getRanks());
        assertArrayEquals(new int[]{3, 3, 3, 3}, segundaCarta.getRanks());
    }
    
    @Test
    public void testarLimiteMaximoDoBonus() {
        CardState primeiraCarta = criarCarta(
            1, "Ifrit", ElementType.FIRE,
            new int[]{10, 9, 8, 7}, 0
        );

        CardState segundaCarta = criarCarta(
            2, "Ifrit", ElementType.FIRE,
            new int[]{10, 5, 5, 5}, 1
        );

        engine.placeCard(primeiraCarta, new int[]{0, 0});
        engine.placeCard(segundaCarta, new int[]{1, 1});

        assertArrayEquals(new int[]{10, 10, 9, 8}, primeiraCarta.getRanks());
        assertArrayEquals(new int[]{10, 6, 6, 6}, segundaCarta.getRanks());
    }
    
    @Test
    public void testarPartidaComTabuleiroIncompleto() {
        CardState carta = criarCarta(
            1, "Ifrit", ElementType.FIRE,
            new int[]{1, 1, 1, 1}, 0
        );

        engine.placeCard(carta, new int[]{0, 0});

        engine.verifyOver();

        assertFalse(engine.isOver());
    }
    
    @Test
    public void testarPartidaComTabuleiroCompleto() {
        ElementType[] elementos = ElementType.values();

        int id = 1;

        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {

                CardState carta = criarCarta(
                    id, "Carta " + id, elementos[id - 1],
                    new int[]{1, 1, 1, 1}, 0
                );

                engine.placeCard(carta, new int[]{linha, coluna});

                id++;
            }
        }

        assertTrue(engine.isOver());
    }
    
    @Test
    public void testarLimpezaDoTabuleiro() {
        CardState carta = criarCarta(
            1, "Ifrit", ElementType.FIRE,
            new int[]{1, 1, 1, 1}, 0
        );

        engine.placeCard(carta, new int[]{0, 0});

        engine.clear();

        assertFalse(engine.isOver());
        assertEquals(0, engine.getPoints(0));
        assertEquals(0, engine.getPoints(1));
        assertTrue(engine.isFree(new int[]{0, 0}));
    }
    
    @Test
    public void testarPontuacaoDosJogadores() {
        CardState carta1 = criarCarta(
            1, "Ifrit", ElementType.FIRE,
            new int[]{1, 1, 1, 1}, 0
        );

        CardState carta2 = criarCarta(
            2, "Shiva", ElementType.ICE,
            new int[]{1, 1, 1, 1}, 1
        );

        CardState carta3 = criarCarta(
            3, "Ramuh", ElementType.THUNDER,
            new int[]{1, 1, 1, 1}, 0
        );

        CardState carta4 = criarCarta(
            4, "Garuda", ElementType.WIND,
            new int[]{1, 1, 1, 1}, 1
        );

        engine.placeCard(carta1, new int[]{0, 0});
        engine.placeCard(carta2, new int[]{0, 2});
        engine.placeCard(carta3, new int[]{2, 0});
        engine.placeCard(carta4, new int[]{2, 2});

        assertEquals(2, engine.getPoints(0));
        assertEquals(2, engine.getPoints(1));
    }
    
    @Test
    public void testarViradaComRanksIguais() {
        CardState cartaAdversaria = criarCarta(
            1, "Shiva", ElementType.ICE,
            new int[]{1, 1, 1, 5}, 1
        );

        CardState minhaCarta = criarCarta(
            2, "Ifrit", ElementType.FIRE,
            new int[]{5, 1, 1, 1}, 0
        );

        engine.placeCard(cartaAdversaria, new int[]{0, 1});
        engine.placeCard(minhaCarta, new int[]{1, 1});

        assertEquals(1, cartaAdversaria.getSide());
    }
    
    @Test
    public void testarViradaComRankMenor() {
        CardState cartaAdversaria = criarCarta(
            1, "Shiva", ElementType.ICE,
            new int[]{1, 1, 1, 6}, 1
        );

        CardState minhaCarta = criarCarta(
            2, "Ifrit", ElementType.FIRE,
            new int[]{5, 1, 1, 1}, 0
        );

        engine.placeCard(cartaAdversaria, new int[]{0, 1});
        engine.placeCard(minhaCarta, new int[]{1, 1});

        assertEquals(1, cartaAdversaria.getSide());
    }
    
    @Test
    public void testarCartaDoMesmoJogadorNaoVira() {
        CardState primeiraCarta = criarCarta(
            1, "Shiva", ElementType.ICE,
            new int[]{1, 1, 1, 5}, 0
        );

        CardState segundaCarta = criarCarta(
            2, "Ifrit", ElementType.FIRE,
            new int[]{9, 1, 1, 1}, 0
        );

        engine.placeCard(primeiraCarta, new int[]{0, 1});
        engine.placeCard(segundaCarta, new int[]{1, 1});

        assertEquals(0, primeiraCarta.getSide());
    }
}
