package builders;

import domain.entities.Card;
import domain.enums.ElementType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardBuilder {

    public List<Card> loadCards(String filePath) {
        List<Card> cards = new ArrayList<>();

        String delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(delimiter);

                int id = Integer.parseInt(data[0].trim());
                String name = data[1].trim().replaceAll("^\"|\"$", "");

                int[] ranks = parseRanks(data[2]);

                ElementType element = ElementType.valueOf(data[3].trim().toUpperCase());

                Card card = new Card(id, name, element, ranks);
                cards.add(card);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erro ao processar linha: " + e.getMessage());
        }

        return cards;
    }

    private int[] parseRanks(String rawRanks) {
        String cleanString = rawRanks.replaceAll("[\"\\[\\]]", "");
        int[] ranks = new int[4]; // UP, LEFT, RIGHT, DOWN

        for (String part : cleanString.split(",")) {
            String[] kv = part.split(":");
            int value = Integer.parseInt(kv[1].trim());
            switch (kv[0].trim().toUpperCase()) {
                case "UP"    -> ranks[0] = value;
                case "LEFT"  -> ranks[1] = value;
                case "RIGHT" -> ranks[2] = value;
                case "DOWN"  -> ranks[3] = value;
                default -> throw new IllegalArgumentException("Lado invalido: " + kv[0]);
            }
        }
        return ranks;
    }
}
