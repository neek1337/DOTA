import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Test {
    public static HashMap<Integer, String> idNames;

    public static void main(String[] args) throws IOException {
        String key = "DC4EE859CADCA39B8ED09FDE65451627";
        int matchId = 1538788663;
        String getMatchHistory = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key=";
        String getMatchDetails = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?key=DC4EE859CADCA39B8ED09FDE65451627&match_id=";
        String getHeroes = "https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=DC4EE859CADCA39B8ED09FDE65451627";
        Http http = new Http(getMatchHistory + key + "&min_players=10&skill=3");
        Parser parser = new Parser(http.toString());
        LinkedList<Integer> matches = parser.getMatches();

        http = new Http(getHeroes);
        parser = new Parser(http.toString());
        idNames = parser.makeMap();

        int counter = 0;


        Table winTable = new Table();
        System.out.println(new Date(System.currentTimeMillis()));
        while (counter < 50) {
            for (Integer match : matches) {
                //   System.out.println("Послали запрос на инфу о матче");
                //  System.out.println(System.currentTimeMillis());
                http = new Http(getMatchDetails + match);
                //   System.out.println(System.currentTimeMillis());
                parser = new Parser(http.toString());
                byte[] matchResult = parser.getHeroes();
                if (matchResult != null) {
                    winTable.updateMidTable(matchResult);
                    System.out.println(counter);
               /*     System.out.println("Match id:" + match);
                    System.out.println("Winners:");
                    for (int i = 0; i < 5; i++) {
                        int hero = matchResult[i];
                        System.out.println(idNames.get(hero));
                    }
                    System.out.println("Losers:");
                    for (int i = 0; i < 5; i++) {
                        int hero = matchResult[i + 5];
                        System.out.println(idNames.get(hero));
                    }
               */     if (counter % 50 == 0) {
                        winTable.saveToFile(match);
                    }
                    counter++;
                }

                matchId = parser.getNext();
             //   System.out.println("Послали запрос на 100 игр");
             //   System.out.println(System.currentTimeMillis());
                http = new Http(getMatchHistory + key + "&min_players=10&skill=3&start_at_match_id=" + matchId+"game_mode=1");
             //   System.out.println(System.currentTimeMillis());
                parser = new Parser(http.toString());
                matches = parser.getMatches();
            }
        }
        System.out.println(winTable);
        System.out.println(new Date(System.currentTimeMillis()));

    }
}
