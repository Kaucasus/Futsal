package be.kuleuven.softdev.jordi.futsal;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.classes.Field;
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
        //Create an ArrayList of 8 players for field
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


        Field veld = new Field(SampleArray,50,5);
        veld.generateSubstitutions();

        int lowest = Integer.MAX_VALUE;

        ArrayList<Player> spelers = new ArrayList<>();
        spelers.addAll(veld.getBenchPlayers());
        spelers.addAll(veld.getFieldPlayers());

        for (Player speler: spelers)
        {
            if(speler.getPlayTime() < lowest)
            {lowest = speler.getPlayTime();}
        }

        assert(lowest > 1);

        }

}
