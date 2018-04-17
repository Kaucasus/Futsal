package be.kuleuven.softdev.jordi.futsal.classes;

import java.util.ArrayList;

public class Substitution {

    private ArrayList<Player> in;
    private ArrayList<Player> out;

    public Substitution( ArrayList<Player> in, ArrayList<Player> out) {

        this.in = in;
        this.out = out;

    }

    public ArrayList<Player> getIn() {
        return in;
    }

    public ArrayList<Player> getOut() {
        return out;
    }
}
