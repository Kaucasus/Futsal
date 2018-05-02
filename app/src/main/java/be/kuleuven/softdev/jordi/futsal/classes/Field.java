package be.kuleuven.softdev.jordi.futsal.classes;
//Todo: write documentation
//Todo: make the constructor accept an arraylist of Players instead of players.

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Field {
    private ArrayList<Player> fieldPlayers;
    private ArrayList<Player> benchPlayers;
    private int size;
    private int gameLength;
    private int subLength;




    public Field(ArrayList<String> playerList, int gameLength, int subLength) {
        this.gameLength = gameLength;
        this.subLength = subLength;
        size = playerList.size();

        fieldPlayers = new ArrayList<>();
        benchPlayers = new ArrayList<>();

        //First four in the playerList are the first fieldPlayers
        //The rest is in the benchPlayers
        for (int i = 0; i < 4; i++) {
            fieldPlayers.add(new Player(playerList.get(i)));
        }
        for (int i = 4; i < playerList.size(); i++) {
            benchPlayers.add(new Player(playerList.get(i)));
        }
    }

    //Getters and Setters
    public ArrayList<Player> getFieldPlayers() {
        return fieldPlayers;
    }

    public ArrayList<Player> getBenchPlayers() {
        return benchPlayers;
    }


    //Main Methods

    public ArrayList<Substitution> generateSubstitutions() {
        //Todo make a better algorithm

        ArrayList<Substitution> substitutions = new ArrayList<>();

        if (size != fieldPlayers.size()) {
            int subAmount = (gameLength / subLength) - 1;


            for (int i = 0; i < subAmount; i++) {
                int time = (i+1)*subLength;
                ArrayList<Player> in;
                ArrayList<Player> out;

                //IncrementPlaytime
                for (Player player : fieldPlayers) {
                    player.incrementPlayTime();
                }

                in = getPlayCount(2, benchPlayers, false);
                out = getPlayCount(2, fieldPlayers, true);

                Substitution substitution = new Substitution(in, out, time);
                substitutions.add(substitution);
                substitute(substitution);



            }

        }
        return substitutions;
    }


    //Private helper Methods

    private void substitute(Substitution substitution) {

        ArrayList<Player> subIn = substitution.getIn();
        ArrayList<Player> subOut = substitution.getOut();

        benchPlayers.addAll(subOut);
        fieldPlayers.addAll(subIn);

        Iterator<Player> fieldIt = fieldPlayers.iterator();
        Iterator<Player> benchIt = benchPlayers.iterator();

        while (benchIt.hasNext()) {

            Player currentPlayer = benchIt.next();

            if (subIn.contains(currentPlayer)) {
                benchIt.remove();
            }

        }

        while (fieldIt.hasNext()) {

            Player currentPlayer = fieldIt.next();

            if (subOut.contains(currentPlayer)) {
                fieldIt.remove();
            }
        }

    }





    private ArrayList<Player> getPlayCount(int amount, ArrayList<Player> list, boolean out) {

        if (amount >= list.size()) {
            return list;

        } else {
            ArrayList<Player> outList = new ArrayList<>();
            ArrayList<Player> copyList = new ArrayList<>(list);

            //Sorter
            Collections.sort(copyList, new playerListComparator());

            if(out)
            {
                Collections.reverse(copyList);
            }

            for (int i = 0; i < amount; i++) {
                outList.add(copyList.get(i));
            }



            return outList;
        }

    }

// Additional helper classes

    class playerListComparator implements Comparator<Player> {
        @Override
        public int compare(Player o1, Player o2) {
            int time1 = o1.getPlayTime();
            int time2 = o2.getPlayTime();

            if (time1 > time2) {
                return 1;
            } else if (time1 < time2) {
                return -1;
            } else {
                return 0;
            }

        }
    }
}
