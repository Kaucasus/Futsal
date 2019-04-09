package be.kuleuven.softdev.jordi.futsal.classes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import static java.lang.Math.floor;
//Todo FIX PLAYER ALGORITHM!!!

public class Match {
    //General Match Information
    private boolean active;
    private Score score;
    private String Opponents;
    private Date date;

    //Player and Field information
    private ArrayList<Player> fieldPlayers;
    private ArrayList<Player> benchPlayers;
    private Player keeper;
    private ArrayList<Substitution> futureSubstitutions;
    private ArrayList<Substitution> substitutions;
    private ArrayList<Goal> goals;

    //TODO Make settings page
    //GameLength and Sublength should be settings, but for now are hardcoded in. They're in seconds
    //(50 mins and 5 mins)/3000 s and 300 sec
    //Substitution Amount should also be setting, but for now is hardcoded in as 2
    //FieldPlayerSize should also be in setting (for games with more then 5 total players)
    //But for now is hardcoded as 4
    //extraPlayersMinin is the minimum extra players available before you go through with
    // generating futureSubstitutions (should also be a setting)
    private long gameLength = 5*60;
    private long subLength = 5*6;
    private int substitutionAmount = 2;
    private int fieldPlayerSize = 4;
    private int extraPlayersMin = 2;



    public Match(ArrayList<Player> playerList) {
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

        futureSubstitutions = generateSubstitutions(fieldPlayers,benchPlayers);
    }

    //overLoaded constructor for saved instance state recall.
    public Match(ArrayList<Player> fieldPlayers, ArrayList<Player> benchPlayers
            , ArrayList<Substitution> futureSubstitutions, ArrayList<Goal> goals,
                 Score score) {
        this.score = score;

        fieldPlayers = new ArrayList<>();
        benchPlayers = new ArrayList<>();
        futureSubstitutions = new ArrayList<>();
        goals = new ArrayList<>();
        this.fieldPlayers = fieldPlayers;
        this.benchPlayers = benchPlayers;
        this.futureSubstitutions = futureSubstitutions;
        this.goals = goals;
    }


    //Getters and Setters
    public ArrayList<Player> getFieldPlayers() {
        return fieldPlayers;
    }

    public ArrayList<Player> getBenchPlayers() {
        return benchPlayers;
    }

    public ArrayList<Substitution> getFutureSubstitutions() {
        return futureSubstitutions;
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
        substitute(futureSubstitutions.get(0),fieldPlayers,benchPlayers);
        futureSubstitutions.remove(0);
    }

    public void addGoal(Goal goal){
        if(!(goals.contains(goal))){
            goals.add(goal);
        }
    }

    public void endMatch(){
        active = false;
    }

    public String toEmailReport(){
        //TODO: pretty up the report
        String report = "";
        String nl = System.getProperty("line.separator");
        report += "We:" + score.getHome() + " Opponents: " + score.getOut() +nl;
        for (Goal goal: goals
             ) {
            report +=  "Scorer: " + goal.getScorer() + " Assister: " +goal.getAssister() + nl;
        }

        return report;
    }

    public void generateNewSubstitutions(Substitution substitution)
    {
        ;
    }

    //Private helper Methods
    private ArrayList<Substitution> generateSubstitutions(ArrayList<Player> fieldPlayers
            , ArrayList<Player> benchPlayers) {

        ArrayList<Substitution> substitutions = new ArrayList<>();

        if (fieldPlayerSize + extraPlayersMin <= fieldPlayers.size() + benchPlayers.size()) {
            int subAmount = (int) floor((gameLength / subLength) - 1);

            for (int i = 0; i < subAmount; i++) {
                int time = (i + 1) *(int) (subLength/60);
                ArrayList<Player> in;
                ArrayList<Player> out;

                //IncrementPlaytime
                for (Player player : fieldPlayers) {
                    player.addPlayTime(subLength);
                }

                in = getPlayCount(substitutionAmount, benchPlayers, false);
                out = getPlayCount(substitutionAmount, fieldPlayers, true);

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
        //you need to deepcopy the Players so that the reference to the substitutions don't get lost!
        ArrayList<Player> subOut = substitution.getOut();

        //Add outgoingplayers to bench, add incomingplayers to field
        benchPlayers.addAll(subOut);
        fieldPlayers.addAll(subIn);


        Iterator<Player> fieldIt = fieldPlayers.iterator();
        Iterator<Player> benchIt = benchPlayers.iterator();
        //Delete all incoming players from bench
        while (benchIt.hasNext()) {

            Player currentPlayer = benchIt.next();

            if (fieldPlayers.contains(currentPlayer)) {
                benchIt.remove();
            }

        }

        //Delete all outgoing players from field
        while (fieldIt.hasNext()) {

            Player currentPlayer = fieldIt.next();

            if ( benchPlayers.contains(currentPlayer)) {
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
            Collections.sort(copyList, new playerTimeComparator());

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

    class playerTimeComparator implements Comparator<Player> {
        @Override
        public int compare(Player o1, Player o2) {
            long time1 = o1.getPlayTime();
            long time2 = o2.getPlayTime();

            //Is in this form and not long.compare since API call doesn't support it.
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