package be.kuleuven.softdev.jordi.futsal;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.softdev.jordi.futsal.classes.Goal;
import be.kuleuven.softdev.jordi.futsal.classes.Match;
import be.kuleuven.softdev.jordi.futsal.classes.Player;
import be.kuleuven.softdev.jordi.futsal.fragments.AlertDialogGoal;
import be.kuleuven.softdev.jordi.futsal.handlers.TimerHandler;
import be.kuleuven.softdev.jordi.futsal.listadapters.RecyclerItemClickListener;
import be.kuleuven.softdev.jordi.futsal.listadapters.SpinnerItemClickListener;
import be.kuleuven.softdev.jordi.futsal.listadapters.SubstitutionListAdapter;

public class ActiveMatch extends AppCompatActivity {
    private static final String TAG = "Active Match";

    Match match;
    int gameLength = 50;
    int subLength = 5;
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
        match = new Match(playerList,gameLength,subLength);


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
        mAdapter = new SubstitutionListAdapter(match.getSubstitutions(),this);
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
            // TODO: 5/29/18 pick players who scored via spinner

/*            DialogFragment newFragment = new AlertDialogGoal();
            Bundle data = new Bundle();
            data.putParcelableArrayList("players",match.getFieldPlayers());

            newFragment.setArguments(data);
            newFragment.show(getSupportFragmentManager(),"goal select");*/

//AlertDialog:
            LayoutInflater li = getLayoutInflater();
            View alertLayout = li.inflate(R.layout.alert_dialog_goal_scored, null);

            ArrayList<Player> goalmakers = match.getFieldPlayers();
            ArrayList<Player> assisters = match.getFieldPlayers();

            Spinner goalmaker = alertLayout.findViewById(R.id.goalmaker_spinner);
            ArrayAdapter<Player> gmAdapter = new ArrayAdapter<Player>(getApplicationContext()
                    ,  android.R.layout.simple_spinner_item,goalmakers);
            gmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            goalmaker.setAdapter(gmAdapter);
            goalmaker.setOnItemSelectedListener(new SpinnerItemClickListener());

            Spinner assister = alertLayout.findViewById(R.id.assister_spinner);
            ArrayAdapter<Player> assistAdapter = new ArrayAdapter<Player>(getApplicationContext()
                    ,  android.R.layout.simple_spinner_item, assisters);
            assistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            assister.setAdapter(assistAdapter);
            assister.setOnItemSelectedListener(new SpinnerItemClickListener());

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Goal scored")
                    .setView(alertLayout)
                    .setCancelable(false)
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    match.updateScore(true, th.getTime());
                    match.addGoal(new Goal(match.getFieldPlayers().get(0), match.getFieldPlayers().get(1)
                            , th.getTime(), match.getScore()));

                    TextView mScoreBoard = (TextView) findViewById(R.id.scoreboard);
                    mScoreBoard.setText(match.getScore().scoreBoardString());
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
            TextView mScoreBoard = (TextView) findViewById(R.id.scoreboard);
            mScoreBoard.setText(match.getScore().scoreBoardString());
        }
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

        if(match.getSubstitutions().size()>0 && match.getSubstitutions().get(0).getTime() < minutes)
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
                .setContentText("In: "+ match.getSubstitutions().get(0).inToString()
                        + ", Out: "+ match.getSubstitutions().get(0).outToString() +".")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationID, mBuilder.build());

    }


/*    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }*/
}



