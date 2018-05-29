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
    public void FieldTest() {
        //8 players

        //Create an ArrayList of 8 players for match
        ArrayList<String> SampleArray = new ArrayList<>();
        SampleArray.add(("a"));
        SampleArray.add(("b"));
        SampleArray.add(("c"));
        SampleArray.add(("d"));
        SampleArray.add(("e"));
        SampleArray.add(("f"));
        SampleArray.add(("g"));
        SampleArray.add( ("h"));

        ArrayList<Substitution> substitutions = new ArrayList<>();


        Match veld = new Match(SampleArray,50,5);
        veld.generateSubstitutions();

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


        Match veld7 = new Match(SampleArray,50,5);
        veld.generateSubstitutions();

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

}
