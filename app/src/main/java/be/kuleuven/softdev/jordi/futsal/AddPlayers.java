package be.kuleuven.softdev.jordi.futsal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.classes.Player;
import be.kuleuven.softdev.jordi.futsal.listadapters.PlayerListAdapter;
import be.kuleuven.softdev.jordi.futsal.listadapters.RecyclerItemClickListener;

public class AddPlayers extends AppCompatActivity {
    private ArrayList<Player> playerList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        playerList = new ArrayList<>();
        setContentView(R.layout.activity_add_players);
        mRecyclerView = (RecyclerView) findViewById(R.id.player_list_recycler_view);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,  mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        ;
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        playerList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }

                })
        );

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PlayerListAdapter(playerList);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void addPlayer(View view) {
        if(playerList.size()<10)
        {
            dialogAddPlayer();
            mAdapter.notifyDataSetChanged();

        }
        else {
            Toast.makeText(this, "Too many players!", Toast.LENGTH_SHORT).show();

        }
    }

    private void dialogAddPlayer() {
        //Method by https://stackoverflow.com/questions/10903754/input-text-dialog-android#10904665
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add player here!");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Player newPlayer = new Player(input.getText().toString());
                if(newPlayer.getName().trim().length() > 0 && !playerList.contains(newPlayer))
                {
                    playerList.add(newPlayer);

                }else{
                    Toast.makeText(AddPlayers.this, "Same or invalid player"
                            , Toast.LENGTH_SHORT).show();
                    dialog.cancel();

                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void continueToActiveMatch(View view) {
        if(playerList.size() >= 6)
        {
            //dialogChooseStarters();
            //This basically to pass through an arrayList of players to the activity.
            Intent intent = new Intent(this, ActiveMatch.class);
            intent.putParcelableArrayListExtra("playerList",playerList);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this,"Too few players!",Toast.LENGTH_SHORT).show();
        }

    }

    public void addExistingPlayer(View view) {
        // TODO: 3/24/19 Add the builder so that you can add multiple players at a time
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("pick a player")
                .setItems(R.array.known_players, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        Player tempPlayer = new Player(getResources()
                                .getStringArray(R.array.known_players)[which]);
                            if(playerList.size()<10 && !playerList.contains(tempPlayer)){
                                playerList.add(tempPlayer);
                                mAdapter.notifyDataSetChanged();
                            }

                    }
                });

        builder.show();


    }

}
