package be.kuleuven.softdev.jordi.futsal;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import be.kuleuven.softdev.jordi.futsal.classes.Player;
import be.kuleuven.softdev.jordi.futsal.dialogfragments.PickPlayerDialogFragment;
import be.kuleuven.softdev.jordi.futsal.listadapters.PlayerListAdapter;
import be.kuleuven.softdev.jordi.futsal.listadapters.RecyclerItemClickListener;

public class AddPlayers extends AppCompatActivity implements PickPlayerDialogFragment.PickPlayerDialogFragmentListener {
    private ArrayList<Player> playerList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Player> knownPlayers;
    //TODO: Make more novel implementation of knownPlayers


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

        if(playerList.size() <=13){
         Toast.makeText(this,"Too many players!",Toast.LENGTH_SHORT).show();

        }if(playerList.size() >= 6)
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
        PickPlayerDialogFragment pickPlayerDialogFragment = new PickPlayerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("players",knownPlayers);
        pickPlayerDialogFragment.setArguments(bundle);
        pickPlayerDialogFragment.show(getSupportFragmentManager(),"tag");


    }


    private void populateKnownPlayers(ArrayList<Player> knownPlayers)
    {
        knownPlayers.clear();
        knownPlayers.add(new Player("Jordi"));
        knownPlayers.add(new Player("Arthur"));
        knownPlayers.add(new Player("Milo"));
        knownPlayers.add(new Player("Robin"));
        knownPlayers.add(new Player("Jonas D"));
        knownPlayers.add(new Player("Jonas B"));
        knownPlayers.add(new Player("Floris"));
        knownPlayers.add(new Player("Sandro"));
        knownPlayers.add(new Player("Marten"));
        knownPlayers.add(new Player("Rik"));
        knownPlayers.add(new Player("Ruben"));
        knownPlayers.add(new Player("Jannes"));


    }

    @Override
    public void onDialogPlayerPositiveClick(DialogFragment dialog, ArrayList<Player> players) {
        playerList.addAll(players);
        mAdapter.notifyDataSetChanged();
    }
}
