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
    byte[] concat(byte[] A, byte[] B) {
        int aLen = A.length;
        int bLen = B.length;
        byte[] C= new byte[aLen+bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }

    public byte[] getHeroes() {
        if (text.charAt(text.indexOf("lobby_type") + 13) == '7') {
            byte[] radiantTeam = new byte[5];
            byte[] direTeam = new byte[5];

            for (int i = 0; i < 5; i++) {
                text = text.substring(text.indexOf("hero_id") + 10, text.length());
                Byte heroId = Byte.valueOf(text.substring(0, text.indexOf(",")));
                radiantTeam[i] = heroId;
            }
            for (int i = 0; i < 5; i++) {
                text = text.substring(text.indexOf("hero_id") + 10, text.length());
                Byte heroId = Byte.valueOf(text.substring(0, text.indexOf(",")));
                direTeam[i] = heroId;
            }
            text = text.substring(text.indexOf("radiant_win") + 14, text.length());
            Boolean radiant_win = Boolean.valueOf(text.substring(0, text.indexOf(",")));
            if (radiant_win) {
                return  concat(radiantTeam, direTeam);
            } else {
                return concat(direTeam, radiantTeam);
            }
        }
        return null;
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
