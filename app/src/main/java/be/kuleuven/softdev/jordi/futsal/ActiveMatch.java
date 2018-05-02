package be.kuleuven.softdev.jordi.futsal;
//Todo make an AlarmManager that can call whenever there is a substitute coming up
//Todo implement a way so you can add substitutes and something that you can ok a substitution

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.classes.Field;
import be.kuleuven.softdev.jordi.futsal.classes.GameTimer;
import be.kuleuven.softdev.jordi.futsal.classes.Score;
import be.kuleuven.softdev.jordi.futsal.classes.Substitution;
import be.kuleuven.softdev.jordi.futsal.handlers.TimerHandler;
import be.kuleuven.softdev.jordi.futsal.listadapters.SubstitutionListAdapter;

public class ActiveMatch extends AppCompatActivity {
    ArrayList<String> playerList;
    ArrayList<Substitution> substitutions;
    GameTimer gameTimer;
    Field field;
    Score score;
    int gameLength = 50;
    int subLength = 5;
    long startTime = System.currentTimeMillis();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public TextView mTimer = (TextView) findViewById(R.id.timer);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_match);

        Intent intent = getIntent();
        playerList = intent.getStringArrayListExtra("playerList");

        //Setup classes

        //Create new gameField to get the substitutions
        //Todo: make the field constructor accept an arraylist of Players instead of players.

        field = new Field(playerList,gameLength,subLength);
        score = new Score(0,0,0);
        gameTimer = new GameTimer();




        //Create substitution List and display it
        substitutions = new ArrayList<>();
        substitutions = field.generateSubstitutions();


        //Setup views



        TextView mScoreBoard = (TextView) findViewById(R.id.scoreboard);
        mScoreBoard.setText(score.scoreBoardString());

        //setup recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.substitution_recycler_view);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SubstitutionListAdapter(substitutions);
        mRecyclerView.setAdapter(mAdapter);

        //create new handler to update the UI every second. And to see  the value.

        //final TimerHandler th = new TimerHandler(Looper.getMainLooper());
        //th.sendEmptyMessage(1);

        final Handler timerHandler = new Handler();
        timerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis()-startTime;
                String timeString = (time / (1000*60)) % 60 + " : " + (time / 1000)%60;
                mTimer.setText(timeString);

                timerHandler.postDelayed(this, 1000)
            }
        })



    }


    public void homeScored(View view) {
        //Todo: create dialogFragment where you can put goal


        TextView mScoreBoard = (TextView) findViewById(R.id.scoreboard);
        mScoreBoard.setText(score.scoreBoardString());
    }

    public void opponentScored(View view) {

        score.incrementOut();
        TextView mScoreBoard = (TextView) findViewById(R.id.scoreboard);
        mScoreBoard.setText(score.scoreBoardString());
    }

    public void addSubstitute(View view) {
    }


}



