package be.kuleuven.softdev.jordi.futsal.classes;

public class Score {
    private int home;
    private int out;
    private long timestamp;

    public Score(int home, int out, long timestamp) {
        this.home = home;
        this.out = out;
        this.timestamp = timestamp;
    }

    public int getHome() {
        return home;
    }

    public int getOut() {
        return out;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
