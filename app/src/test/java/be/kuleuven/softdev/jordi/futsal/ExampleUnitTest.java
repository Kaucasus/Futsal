package be.kuleuven.softdev.jordi.futsal;

import org.junit.Test;

import java.util.ArrayList;


import be.kuleuven.softdev.jordi.futsal.classes.Match;
import be.kuleuven.softdev.jordi.futsal.classes.Player;
import be.kuleuven.softdev.jordi.futsal.classes.Substitution;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void substitutionTest() {
        //8 players

        //Create an ArrayList of 8 players for match
        ArrayList<Player> samplePlayers = new ArrayList<>();
        samplePlayers.add(new Player("a"));
        samplePlayers.add(new Player("b"));
        samplePlayers.add(new Player("c"));
        samplePlayers.add(new Player("d"));
        samplePlayers.add(new Player("e"));
        samplePlayers.add(new Player("f"));
        samplePlayers.add(new Player("g"));
        samplePlayers.add(new Player("h"));

        ArrayList<Substitution> substitutions = new ArrayList<>();


        Match veld = new Match(samplePlayers);
        substitutions = veld.getFutureSubstitutions();
        long lowest = Integer.MAX_VALUE;
        long highest = Integer.MIN_VALUE;

        ArrayList<Player> spelers = new ArrayList<>();
        spelers.addAll(veld.getBenchPlayers());
        spelers.addAll(veld.getFieldPlayers());

        for (Player speler: spelers)
        {
            if(speler.getPlayTime() < lowest)
            {lowest = speler.getPlayTime();}
            if(speler.getPlayTime() > highest){highest = speler.getPlayTime();}
        }


        //8 players means that the max difference 5 mins is now. Need to improve the alg.
        //Theoretically it should be possible to have a max delta of 0 mins.
        assert(lowest > 1);
        assert(lowest > highest/2);
        assert(lowest > highest-2);

        //7 players
        //Create an ArrayList of 7 players for match
        ArrayList<String> SampleArray7 = new ArrayList<>();
        SampleArray7.add(("a"));
        SampleArray7.add(("b"));
        SampleArray7.add(("c"));
        SampleArray7.add(("d"));
        SampleArray7.add(("e"));
        SampleArray7.add(("f"));
        SampleArray7.add(("g"));


        ArrayList<Substitution> substitutions7 = new ArrayList<>();


        Match veld7 = new Match(samplePlayers);

        long lowest7 = Integer.MAX_VALUE;
        long highest7 = Integer.MIN_VALUE;

        ArrayList<Player> spelers7 = new ArrayList<>();
        spelers7.addAll(veld.getBenchPlayers());
        spelers7.addAll(veld.getFieldPlayers());

        for (Player speler: spelers7)
        {
            if(speler.getPlayTime() < lowest7)
            {lowest7 = speler.getPlayTime();}
            if(speler.getPlayTime() > highest7){highest7 = speler.getPlayTime();}
        }

        assert(lowest7 > 1);
        assert(lowest7 > highest7/2);
        //7 players means that the max difference 10 mins is now. Need to improve the alg.
        //Theoretically it should be possible to have a max delta of 5 mins.
        assert(lowest7 > highest7-3);

        //6 players
        }

    @Test
    public void resumeMatchTest(){

    }

    @Test
    public void generateMatchSubstitutionsTest(){
        ArrayList<Player> players = generatePlayerArrayList(6);
        ArrayList<Substitution> substitutions;
        Match match = new Match(players);

        ArrayList<Player> check = new ArrayList<>();
        check.addAll(match.getBenchPlayers());

        assertNotNull(check);
        assertNotNull(match.getFieldPlayers());

        substitutions = match.getFutureSubstitutions();
        System.out.println("-------------------------- TEST --------------------");
        for (Substitution sub :substitutions
                ) {
            System.out.println("in: " + sub.inToString());
            System.out.println("out: "+sub.outToString());
        }
        assert (substitutions.size()>5);
    }

    private ArrayList<Player> generatePlayerArrayList(int size){
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            players.add(new Player(Integer.toString(i)));
        }
        return players;
    }






}
