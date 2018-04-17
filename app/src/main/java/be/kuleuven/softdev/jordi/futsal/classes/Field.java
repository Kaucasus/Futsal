package be.kuleuven.softdev.jordi.futsal.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Field {
    private ArrayList<Player> fieldPlayers;
    private ArrayList<Player> benchPlayers;
    private int size;

    public ArrayList<Player> getFieldPlayers() {
        return fieldPlayers;
    }

    public ArrayList<Player> getBenchPlayers() {
        return benchPlayers;
    }

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

    private void substitute(Substitution substitution) {
        ArrayList<Player> subIn = substitution.getIn();
        ArrayList<Player> subOut = substitution.getOut();
        benchPlayers.addAll(subOut);
        fieldPlayers.addAll(subIn);
        Iterator<Player> fieldit = fieldPlayers.iterator();
        Iterator<Player> benchit = benchPlayers.iterator();

        while (benchit.hasNext()) {
            Player currentPlayer = benchit.next();
            if (subIn.contains(currentPlayer)) {
                benchit.remove();
            }

        }

        while (fieldit.hasNext()) {
            Player currentPlayer = fieldit.next();
            if (subOut.contains(currentPlayer)) {
                fieldit.remove();
            }
        }

    }


    public ArrayList<Substitution> generateSubstitutions() {

        ArrayList<Substitution> substitutions = new ArrayList<>();

        if (size != fieldPlayers.size()) {
            int subAmount = (gameLength / subLength) - 1;

            //if(playerList.size()%2 !=0) {

            for (int i = 0; i < subAmount; i++) {
                ArrayList<Player> in;
                ArrayList<Player> out;
                in = getPlayCount(2, benchPlayers, true);
                out = getPlayCount(2, fieldPlayers, false);

                Substitution substitution = new Substitution(in, out);
                substitutions.add(substitution);
                substitute(substitution);

                //IncrementPlaytime
                for (Player player : fieldPlayers) {
                    player.incrementPlayTime();
                }

            }

//            }else
//            {
//
//                for (int i = 0; i < subAmount; i++)
//                {
//
//                }
//
//            }
        }
        return substitutions;
    }


private ArrayList<Player> getPlayCount(int amount, ArrayList<Player> list, boolean in) {
        if (amount >= list.size()) {
            return list;
        } else {
            ArrayList<Player> countList = new ArrayList<>();
            //Sorter
            Collections.sort(list, new playerListComparator());

            if(in)
            {
                Collections.reverse(list);
            }

            for (int i = 0; i < amount; i++) {
                countList.add(list.get(i));
            }


            return countList;
        }

    }



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
