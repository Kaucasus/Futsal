package be.kuleuven.softdev.jordi.futsal;


import android.content.Intent;
import androidx.fragment.app.DialogFragment;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.classes.Goal;
import be.kuleuven.softdev.jordi.futsal.classes.Match;
import be.kuleuven.softdev.jordi.futsal.classes.Player;
import be.kuleuven.softdev.jordi.futsal.dialogfragments.GoalDialogFragment;
import be.kuleuven.softdev.jordi.futsal.handlers.TimerHandler;
import be.kuleuven.softdev.jordi.futsal.listadapters.RecyclerItemClickListener;
import be.kuleuven.softdev.jordi.futsal.listadapters.SubstitutionListAdapter;

public class ActiveMatch extends AppCompatActivity implements
        GoalDialogFragment.GoalDialogFragmentListener{
    private static final String TAG = "Active Match";

    Match match;
    //Todo: change gamelength when it's in settings
    //Time in seconds
    int gameLength = 3000;
    int notificationID;
    boolean active;

    //Views
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Handler
    public TimerHandler th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_match);
        Intent intent = getIntent();
        Log.d(TAG, "onCreate: Started intent unwrapping");

        //get the players selected in previous screen
        ArrayList<Player> playerList;
        playerList = intent.getParcelableArrayListExtra("playerList");

        active = true;

        //Set timer prompt
        TextView mTimer = (TextView) findViewById(R.id.timer);
        mTimer.setText(R.string.timer_start_prompt);

        //create new handler to update the UI every second. And to see  the value.
        th = new TimerHandler(this);
        th.sendEmptyMessage(TimerHandler.DO_UPDATE_TIMER);

        //Create new match
        match = new Match(playerList);


        //Setup views
        TextView mScoreBoard = (TextView) findViewById(R.id.scoreboard);
        mScoreBoard.setText(match.getScore().scoreBoardString());

        //setup recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.substitution_recycler_view);


        //via: https://stackoverflow.com/questions/24471109/recyclerview-onclick#26196831
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        ;
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        if (position == 0){
                        match.substituteFirstSubstitution();
                        mAdapter.notifyDataSetChanged();
                        th.setNotificationFired(false);
                        Toast.makeText(ActiveMatch.this, "removed substitution", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ActiveMatch.this, "select the newest substitution", Toast.LENGTH_SHORT).show();
                        }
                        

                    }
                })
        );
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SubstitutionListAdapter(match.getFutureSubstitutions(),this);
        mRecyclerView.setAdapter(mAdapter);

        //give the notification ID
        notificationID = 0;
        Log.d(TAG, "onCreate: Finished Oncreate without errors");
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Can't go back, match in progress", Toast.LENGTH_SHORT).show();
    }

    //On click methods
    public void homeScored(View view) {
        if(!th.isPaused()) {
            GoalDialogFragment goalDialogFragment = new GoalDialogFragment();
            Bundle bundle = new Bundle();
            ArrayList<Player> allPlayers = match.getBenchPlayers();
            allPlayers.addAll( match.getFieldPlayers());
            bundle.putParcelableArrayList("fieldPlayers",allPlayers);
            goalDialogFragment.setArguments(bundle);
            goalDialogFragment.show(getSupportFragmentManager(),"tag");
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Player scorer, Player assister) {
        match.updateScore(true, th.getTime());
        match.addGoal(new Goal(scorer, assister
                , th.getTime(), match.getScore()));

        TextView mScoreBoard = (TextView) findViewById(R.id.scoreboard);
        mScoreBoard.setText(match.getScore().scoreBoardString());
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
    public void opponentScored(View view) {
        if(!th.isPaused()){
            //Goals of opponents don't get tracked

            match.updateScore(false,th.getTime());
            TextView mScoreBoard = (TextView) findViewById(R.id.scoreboard);
            mScoreBoard.setText(match.getScore().scoreBoardString());
        }
    }

    public void addSubstitute(View view) {
        Toast.makeText(this, "on the fly substitution not allowed", Toast.LENGTH_SHORT).show();
    }

    public void forceEndGame(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, "huysejordi@yahoo.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "MatchReport");
        intent.putExtra(Intent.EXTRA_TEXT, match.toEmailReport());
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

    }

    public void pause(View view) {
        if(active) {
            th.sendEmptyMessage(TimerHandler.DO_TOGGLE_TIMER);
            Toast.makeText(this, (th.isPaused() ? "continued timer" : "stopped timer"), Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this, Statistics.class);
            intent.putParcelableArrayListExtra("goals", match.getGoals());
            startActivity(intent);
        }

    }

    public void endGame()
    {
        Toast.makeText(this, "Game ended", Toast.LENGTH_SHORT).show();
        active = false;

    }
    
    public void halftime() {
        Toast.makeText(this, "Half Time", Toast.LENGTH_SHORT).show();
    }
    //Methods to make the timing work

    public void updateUI(String time) {
        TextView timerView = (TextView) findViewById(R.id.timer);
        timerView.setText(time);
    }

    public boolean checkTimeSubstitution(long time)
    {
        //time is in seconds
        boolean check = false;
        long minutes = (time/60);

        if(match.getFutureSubstitutions().size()>0 && match.getFutureSubstitutions().get(0).getTime() < minutes)
        {
            check = true;
        }

        return check;
    }

    public boolean checkHalfTime(long time)
    {
        //time is in seconds
        boolean check = false;
        long minutes = (time/60);
        if(minutes==gameLength/2){
            check = true;
        }
        return check;
    }

    public boolean checkFullTime(long time){
        //time is in seconds
        boolean check = false;
        long minutes = (time/60);
        if(minutes==gameLength){
            check = true;
        }
        return check;
    }


    //Notification logic
    //TODO: Make sure Notifications follow all guidelines

    public void substitutionNotification()
    {
        notificationID++;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"1")
                .setSmallIcon(android.R.color.transparent)
                .setContentTitle("Substitute")
                .setContentText("In: "+ match.getFutureSubstitutions().get(0).inToString()
                        + ", Out: "+ match.getFutureSubstitutions().get(0).outToString() +".")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationID, mBuilder.build());

    }




}



