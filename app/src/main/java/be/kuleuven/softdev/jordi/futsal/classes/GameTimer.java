package be.kuleuven.softdev.jordi.futsal.classes;
//Todo make and understand a handler



public class GameTimer {
    private long time;

    public GameTimer() {
        time = 0;
    }

    public long getTimeStamp(){
        return time;
    }

    public String getTimeString()
    {
        StringBuilder string = new StringBuilder();
        string.append(time);
        string.insert(0,":");
        string.insert(0,time);

        return string.toString();
    }

}
