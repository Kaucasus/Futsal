package be.kuleuven.softdev.jordi.futsal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.classes.Goal;
import be.kuleuven.softdev.jordi.futsal.classes.Match;
import be.kuleuven.softdev.jordi.futsal.classes.Player;

public class testActivity extends AppCompatActivity {

    Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(new Player("Jordi"));
        playerList.add(new Player("JonasDV"));
        playerList.add(new Player("JonasB"));
        playerList.add(new Player("Arti"));
        playerList.add(new Player("Marten"));
        playerList.add(new Player("Milo"));
        playerList.add(new Player("Barry"));
        playerList.add(new Player("Sandro"));


        match = new Match(playerList,5,50);
        match.updateScore(true,50);
        match.addGoal(new Goal(match.getFieldPlayers().get(0), match.getFieldPlayers().get(1)
                , 50, match.getScore()));
        match.updateScore(true, 100);
        match.addGoal(new Goal(match.getFieldPlayers().get(0), match.getFieldPlayers().get(1)
                , 100, match.getScore()));

    }

    public void continueToStats(View view) {
        Intent intent = new Intent(this, Statistics.class);
        intent.putParcelableArrayListExtra("goals", match.getGoals());
        startActivity(intent);
    }
}
