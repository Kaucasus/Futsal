package be.kuleuven.softdev.jordi.futsal.classes;

import java.util.ArrayList;

public class Substitution {

    private  ArrayList<Player> in;
    private  ArrayList<Player> out;
    private long time;

    public Substitution( ArrayList<Player> in, ArrayList<Player> out, long subTime) {

        this.in = in;
        this.out = out;
        time = subTime;

    }

    public ArrayList<Player> getIn() {
        return in;
    }

    public ArrayList<Player> getOut() {
        return out;
    }

    public long getTime()
    {
        return time;
    }

    public ArrayList<String> inToStringList(){
        ArrayList<String> list = new ArrayList<>();
        for (Player player: in
             ) {
            list.add(player.getName());
        }
        return list;
    }

    public ArrayList<String> outToStringList(){
        ArrayList<String> list = new ArrayList<>();
        for (Player player: out
                ) {
            list.add(player.getName());
        }
        return list;
    }

    public String inToString(){
        String inPlayers = "";
        for (Player player: in
             ) {
            inPlayers += player.getName();
            inPlayers += " ";
        }
        return inPlayers;

    }

    public  String outToString(){
        String outPlayers = "";
        for (Player player: out
                ) {
            outPlayers += player.getName();
            outPlayers += " ";
        }
        return outPlayers;
    }
}
