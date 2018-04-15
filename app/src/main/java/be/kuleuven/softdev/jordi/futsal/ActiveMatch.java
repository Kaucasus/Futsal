package be.kuleuven.softdev.jordi.futsal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ActiveMatch extends AppCompatActivity {
    ArrayList<String> playerList;
    ArrayList<Substitution> substitutions;
    Field field;
    int gameLength = 50;
    int subLength = 5;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_match);

        Intent intent = getIntent();
        playerList = intent.getStringArrayListExtra("playerList");

        //Create new gamefield to get the substitutions
        field = new Field(playerList,gameLength,subLength);

        //Create substitution List and display it
        substitutions = new ArrayList<>();
        substitutions = field.generateSubstitutions();

        //setup recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.substitution_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SubstitutionListAdapter(substitutions);
        mRecyclerView.setAdapter(mAdapter);





    }


}



