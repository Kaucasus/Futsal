package be.kuleuven.softdev.jordi.futsal.classes;

public class Goal {
    private Player scorer;
    private Player assister;
    private long timestamp;
    private Score score;

    public Goal(Player scorer, Player assister, long timestamp, Score score) {
        this.scorer = scorer;
        this.assister = assister;
        this.timestamp = timestamp;
        this.score = score;
    }

    public Player getScorer() {
        return scorer;
    }

    public Player getAssister() {
        return assister;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Score getScore() {
        return score;
    }
}
