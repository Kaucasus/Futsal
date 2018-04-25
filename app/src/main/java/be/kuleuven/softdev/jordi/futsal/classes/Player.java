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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;

        return player.name.equals(name);
    }

    @Override
    public int hashCode() {

        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }
}
