import java.io.*;

public class Table {

    private StatsPair[][] midTableSynergy;
    private StatsPair[][] midTableCounter;
    private double[][] winRate;
    private double[][] counterRate;

    public Table() {
        initiateMidTable();
        this.winRate = new double[112][112];
    }

    public Table(File file) {
        if (file.getName().equals("midTableSynergy.txt")) {

        }
    }

    public void updateMidTable(byte[] allHeroes) {


        for (int i = 0; i < allHeroes.length / 2; i++) {
            for (int j = i + 1; j < allHeroes.length / 2; j++) {
                midTableSynergy[allHeroes[i] - 1][allHeroes[j] - 1].incAll();
                midTableSynergy[allHeroes[j] - 1][allHeroes[i] - 1].incAll();
            }
        }
        for (int i = allHeroes.length / 2; i < allHeroes.length; i++) {
            for (int j = i + 1; j < allHeroes.length; j++) {
                midTableSynergy[allHeroes[i] - 1][allHeroes[j] - 1].incAmount();
                midTableSynergy[allHeroes[j] - 1][allHeroes[i] - 1].incAmount();
            }
        }


        for(int i = 0; i<allHeroes.length / 2; i++ ){
            for (int j = allHeroes.length / 2; j < allHeroes.length; j++) {
                midTableCounter[allHeroes[i] - 1][allHeroes[j] - 1].incAll();
                midTableCounter[allHeroes[j] - 1][allHeroes[i] - 1].incAmount();
            }
        }


    }


    private void initiateMidTable() {
        this.midTableSynergy = new StatsPair[112][112];
        this.midTableCounter = new StatsPair[112][112];
        for (int i = 0; i < 112; i++) {
            for (int j = 0; j < 112; j++) {
                this.midTableSynergy[i][j] = new StatsPair();
                this.midTableCounter[i][j] = new StatsPair();
            }
        }
    }

    public void updateWinRate() {
        for (int i = 0; i < 112; i++) {
            for (int j = 0; j < 112; j++) {
                winRate[i][j] = this.midTableSynergy[i][j].getRate();
            }
        }

    }

    public void updateCounterRate() {
        for (int i = 0; i < 112; i++) {
            for (int j = 0; j < 112; j++) {
                counterRate[i][j] = this.midTableCounter[i][j].getRate();
            }
        }
    }

    public void saveToFile(Integer match) throws IOException {
        File fOutSynergy = new File("midTableSynergy.txt");
        File fOutCounter = new File("midTableCounter.txt");
        FileOutputStream fos = new FileOutputStream(fOutSynergy);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write("Match id: " + match);
        bw.newLine();
        for (int i = 0; i < 112; i++) {
            for (int j = 0; j < 112; j++) {
                bw.write(midTableSynergy[i][j].toString() + " ");
            }
            bw.newLine();
        }
        bw.flush();
        fos = new FileOutputStream(fOutCounter);
        bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (int i = 0; i < 112; i++) {
            for (int j = 0; j < 112; j++) {
                bw.write(midTableCounter[i][j].toString() + " ");
            }
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }


    @Override
    public String toString() {
        updateWinRate();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stringBuilder.append(" ");
        }
        stringBuilder.append("|");
        for (int i = 0; i < 112; i++) {
            if (i == 23 || i == 107)
                continue;
            String name = "";
            if (Test.idNames.get(i + 1).length() < 5) {
                name = Test.idNames.get(i + 1);
                for (int j = 0; j < 5 - Test.idNames.get(i + 1).length(); j++) {
                    name += " ";
                }
            } else {
                name = Test.idNames.get(i + 1).substring(0, 5);
            }
            stringBuilder.append(name + "|");
        }
        stringBuilder.append("\n");
        for (int i = 0; i < 112; i++) {
            if (i == 23 || i == 107)
                continue;
            String name = "";
            if (Test.idNames.get(i + 1).length() < 5) {
                name = Test.idNames.get(i + 1);
                for (int j = 0; j < 5 - Test.idNames.get(i + 1).length(); j++) {
                    name += " ";
                }
            } else {
                name = Test.idNames.get(i + 1).substring(0, 5);
            }
            stringBuilder.append(name + "|");
            for (int j = 0; j < 112; j++) {
                if (j == 23 || j == 107)
                    continue;
                String strDouble = "";
                if (winRate[i][j] * 100 < 10) {
                    strDouble = " ";
                }
                if (winRate[i][j] == new Double(1)) {
                    strDouble = "100.0";
                } else {
                    strDouble += String.format("%.2f", winRate[i][j] * 100);
                }
                stringBuilder.append(strDouble + "|");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }


}
