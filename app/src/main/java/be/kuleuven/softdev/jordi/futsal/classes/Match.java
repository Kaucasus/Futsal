package be.kuleuven.softdev.jordi.futsal.classes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Match {
    private ArrayList<Player> fieldPlayers;
    private ArrayList<Player> benchPlayers;
    private ArrayList<Substitution> substitutions;
    private ArrayList<Goal> goals;
    private int size;

    //gameLength and subLength are in minutes
    private int gameLength;
    private int subLength;
    private Score score;


    public Match(ArrayList<Player> playerList, int gameLength, int subLength) {
        this.gameLength = gameLength;
        this.subLength = subLength;
        size = playerList.size();
        score = new Score(0,0,0);

        fieldPlayers = new ArrayList<>();
        benchPlayers = new ArrayList<>();
        goals = new ArrayList<>();


        //First four in the playerList are the first fieldPlayers
        //The rest is in the benchPlayers
        for (int i = 0; i < 4; i++) {
            fieldPlayers.add(playerList.get(i));
        }
        for (int i = 4; i < playerList.size(); i++) {
            benchPlayers.add(playerList.get(i));
        }

        substitutions = generateSubstitutions(fieldPlayers,benchPlayers);
    }

    //overLoaded constructor for saved instance state recall.
    public Match(ArrayList<Player> fieldPlayers, ArrayList<Player> benchPlayers
            , ArrayList<Substitution> substitutions, ArrayList<Goal> goals,
                 Score score, int gameLength, int subLength) {
        this.gameLength = gameLength;
        this.subLength = subLength;
        this.score = score;
        size = fieldPlayers.size() + benchPlayers.size();

        fieldPlayers = new ArrayList<>();
        benchPlayers = new ArrayList<>();
        substitutions = new ArrayList<>();
        goals = new ArrayList<>();
        this.fieldPlayers = fieldPlayers;
        this.benchPlayers = benchPlayers;
        this.substitutions = substitutions;
        this.goals = goals;
    }


    //Getters and Setters
    public ArrayList<Player> getFieldPlayers() {
        return fieldPlayers;
    }

    public ArrayList<Player> getBenchPlayers() {
        return benchPlayers;
    }

    public ArrayList<Substitution> getSubstitutions() {
        return substitutions;
}

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public Score getScore() {
        return score;
    }

    //Main Methods
    public void updateScore(boolean homeScored,long timestamp)
    {
        score.setTimestamp(timestamp);
        if(homeScored){
            score = new Score(score.getHome()+1,score.getOut(), timestamp);
        }else {
            score = new Score(score.getHome(),score.getOut() + 1, timestamp);
        }
    }

    public void substituteFirstSubstitution() {
        substitute(substitutions.get(0),fieldPlayers,benchPlayers);
        substitutions.remove(0);
    }

    public void addGoal(Goal goal){
        if(!(goals.contains(goal))){
            goals.add(goal);
        }
    }

/*    public void generateNewSubstitutions(Substitution substitution)
    {
        ;
    }*/

    //Private helper Methods
    private ArrayList<Substitution> generateSubstitutions(ArrayList<Player> fieldPlayers
            , ArrayList<Player> benchPlayers) {

        ArrayList<Substitution> substitutions = new ArrayList<>();

        if (size != fieldPlayers.size()) {
            int subAmount = (gameLength / subLength) - 1;

            for (int i = 0; i < subAmount; i++) {
                int time = (i + 1) * subLength;
                ArrayList<Player> in;
                ArrayList<Player> out;

                //IncrementPlaytime
                for (Player player : fieldPlayers) {
                    player.addPlayTime(subLength*60);
                }

                in = getPlayCount(2, benchPlayers, false);
                out = getPlayCount(2, fieldPlayers, true);

                Substitution substitution = new Substitution(in, out, time);
                substitutions.add(substitution);
                substitute(substitution, fieldPlayers,benchPlayers);


            }

        }
        return substitutions;
    }

    private void substitute(Substitution substitution, ArrayList<Player> fieldPlayers
            , ArrayList<Player> benchPlayers) {

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

            if (out) {
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
            long time1 = o1.getPlayTime();
            long time2 = o2.getPlayTime();

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