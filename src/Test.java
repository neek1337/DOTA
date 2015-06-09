import javafx.util.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class Test {
    public static void main(String[] args) throws IOException {
        String key = "DC4EE859CADCA39B8ED09FDE65451627";
        int matchId = 1538693646;
        String getMatchHistory = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key=";
        String getMatchDetails = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?key=DC4EE859CADCA39B8ED09FDE65451627&match_id=";
        String getHeroes = "https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=DC4EE859CADCA39B8ED09FDE65451627";

        Http http = new Http(getMatchHistory + key + "&min_players=10&skill=3&start_at_match_id=" + matchId);
        Parser parser = new Parser(http.toString());
        LinkedList<Integer> matches = parser.getMatches();

        http = new Http(getHeroes);
        parser = new Parser(http.toString());
        HashMap<Integer, String> table = parser.makeMap();
        int counter = 0;
        while (counter < 100) {

            http = new Http(getMatchHistory + key + "&min_players=10&skill=3&start_at_match_id=" + matchId);
            parser = new Parser(http.toString());
            matches = parser.getMatches();

            for (Integer match : matches) {
                http = new Http(getMatchDetails + match);
                parser = new Parser(http.toString());
                Pair<Long, Long> matchResult = parser.getHeroes();
                if (matchResult != null) {
                    System.out.println(counter);
                    counter++;
                    System.out.println("Match id:" + match);
                    Long winners = matchResult.getKey();
                    System.out.println("Winners:");
                    for (int i = 0; i < 5; i++) {
                        winners = winners / 113;
                        int hero = (int) (winners % 113);
                        System.out.println(table.get(hero));
                    }
                    System.out.println("Losers:");
                    Long losers = matchResult.getValue();
                    for (int i = 0; i < 5; i++) {
                        losers = losers / 113;
                        int hero = (int) (losers % 113);
                        System.out.println(table.get(hero));
                    }
                }
                matchId = match;
            }

        }

    }
}
