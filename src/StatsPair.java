public class StatsPair {
    private int amount;
    private int wins;

    public StatsPair() {
        this.amount = 0;
        this.wins = 0;
    }

    public StatsPair(Integer amount,Integer wins){
        this.amount = amount;
        this.wins = wins;
    }

    public void incAmount() {
        this.amount++;
    }

    public void incWins() {
        this.wins++;
    }

    public void incAll() {
        this.amount++;
        this.wins++;
    }

    public void init() {
        this.amount = 0;
        this.wins = 0;
    }

    public double getRate() {
        if (amount == 0) {
            return 0;
        }
        return (double) wins / (double) amount;
    }

    @Override
    public String toString() {
        return amount + "|" + wins;

    }
}
