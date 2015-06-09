import javafx.util.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class Test {
    public static void main(String[] args) throws IOException {
        String getMatchHistory = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key=DC4EE859CADCA39B8ED09FDE65451627";
        String getMatchDetails = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?key=DC4EE859CADCA39B8ED09FDE65451627&match_id=1537393363";
        String getHeroes = "https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=DC4EE859CADCA39B8ED09FDE65451627";
        Http http = new Http(getMatchHistory);
        Parser parser = new Parser(http.toString());
        LinkedList<Integer> matches = parser.getMatches();


        http = new Http(getHeroes);
        parser = new Parser(http.toString());
        HashMap<Integer, String> table = parser.makeMap();


        http = new Http(getMatchDetails);
        parser = new Parser(http.toString());
        Pair<Long, Long> matchResult = parser.getHeroes();
        Long winners = matchResult.getKey();
        System.out.println("Winners:");
        for (int i = 0; i < 5; i++) {
            winners = winners / 113;
            int hero = (int) (winners % 113);
            System.out.println(table.get(hero));
        }
    }


}
