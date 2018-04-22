package be.kuleuven.softdev.jordi.futsal.classes;

import java.util.ArrayList;

public class Substitution {

    private ArrayList<Player> in;
    private ArrayList<Player> out;
    private int time;

    public Substitution( ArrayList<Player> in, ArrayList<Player> out, int subTime) {

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

    public int getTime()
    {
        return time;
    }
}
