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

    //Getters and setters

    public int getHome() {
        return home;
    }

    public int getOut() {
        return out;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;

    }

    //Public methods

    public void incrementOut()
    {
        out++;
    }

    public void incrementHome(){
        home++;
    }

    public String scoreBoardString()
    {
        StringBuilder string = new StringBuilder();
        string.append(home);
        string.append("-");
        string.append(out);

        return string.toString();
    }

}
