package be.kuleuven.softdev.jordi.futsal.classes;

public class Player {
    private String name;
    private int playTime;

    public Player(String name)
    {
        this.name = name;
        playTime = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void incrementPlayTime()
    {
        playTime++;
    }

    public int getPlayTime(){
        return playTime;
    }
}
