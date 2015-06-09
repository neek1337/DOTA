import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;

public class Parser {
    private String text;

    public Parser(String text) {
        this.text = text;
    }

    public LinkedList<Integer> getMatches() {
        LinkedList<Integer> result = new LinkedList<Integer>();
        while (text.contains("match_id")) {
            text = text.substring(text.indexOf("match_id") + 11, text.length());
            Integer matchId = Integer.valueOf(text.substring(0, text.indexOf(",")));
            result.add(matchId);
        }
        return result;
    }

    public Pair<Long, Long> getHeroes() {
        Long firstTeam = Long.valueOf(0), secondTeam = Long.valueOf(0);
        Long local = Long.valueOf(113);
        for (int i = 0; i < 5; i++) {
            text = text.substring(text.indexOf("hero_id") + 10, text.length());
            Integer heroId = Integer.valueOf(text.substring(0, text.indexOf(",")));
            firstTeam += local * heroId;
            local *= 113;
        }
        local = Long.valueOf(113);
        for (int i = 0; i < 5; i++) {
            text = text.substring(text.indexOf("hero_id") + 10, text.length());
            Integer heroId = Integer.valueOf(text.substring(0, text.indexOf(",")));
            secondTeam += local * heroId;
            local *= 113;
        }
        text = text.substring(text.indexOf("radiant_win") + 14, text.length());
        Boolean radiant_win = Boolean.valueOf(text.substring(0, text.indexOf(",")));
        if (radiant_win) {
            return new Pair<Long, Long>(firstTeam, secondTeam);
        } else {
            return new Pair<Long, Long>(secondTeam, firstTeam);
        }
    }

    public HashMap<Integer, String> makeMap() {
        HashMap<Integer, String> result = new HashMap<Integer, String>();
        Integer id = 0;
        String name = "";
        while (text.contains("name")) {
            text = text.substring(text.indexOf("name") + 22, text.length());
            name = text.substring(0, text.indexOf(",") - 1);
            if (name.equals("crystal_maiden") || name.equals("tidehunter") || name.equals("faceless_void")
                    || name.equals("batrider") || name.equals("obsidian_destroyer") || name.equals("lone_druid")) {
                text = text.substring(text.indexOf("id") + 5, text.length());
            }
            text = text.substring(text.indexOf("id") + 5, text.length());
            id = Integer.valueOf(text.substring(0, text.indexOf("\n")));
            result.put(id, name);
        }
        return result;
    }

}
