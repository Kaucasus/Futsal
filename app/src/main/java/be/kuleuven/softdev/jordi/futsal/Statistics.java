package be.kuleuven.softdev.jordi.futsal;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.classes.Goal;
import be.kuleuven.softdev.jordi.futsal.listadapters.GoalListAdapter;


public class Statistics extends AppCompatActivity {
    private ArrayList<Goal> goals;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        goals = new ArrayList<>();
        goals = getIntent().getParcelableArrayListExtra("goals");

        //RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.goal_list_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GoalListAdapter(this,goals);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void goBackToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
