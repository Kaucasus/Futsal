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

import be.kuleuven.softdev.jordi.futsal.listadapters.PlayerListAdapter;

public class AddPlayers extends AppCompatActivity {
    private ArrayList<String> playerList;
    private ArrayList<String> starterPlayers;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        playerList = new ArrayList<>();
        starterPlayers = new ArrayList<>();
        setContentView(R.layout.activity_add_players);
        mRecyclerView = (RecyclerView) findViewById(R.id.playerListShower);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PlayerListAdapter(playerList);
        mRecyclerView.setAdapter(mAdapter);





    }

    public void addPlayer(View view) {
        //Method by https://stackoverflow.com/questions/10903754/input-text-dialog-android#10904665
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add player here!");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newPlayer = input.getText().toString();
                if(newPlayer.equals("a"))
                {
                    playerList.add("Jordi");
                    playerList.add("JonasDV");
                    playerList.add("JonasB");
                    playerList.add("Arti");
                    playerList.add("Marten");
                    playerList.add("Milo");
                    playerList.add("Barry");
                    playerList.add("Sandro");
                }else if(newPlayer.trim().length() > 0 && !playerList.contains(newPlayer))
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
        if(playerList.size() >= 4)
        {
            //dialogChooseStarters();
            Intent intent = new Intent(this, ActiveMatch.class);
            intent.putStringArrayListExtra("playerList",playerList);
            intent.putStringArrayListExtra("starterPlayers",starterPlayers);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"Too few players!",Toast.LENGTH_SHORT).show();
        }

    }


 /*   public void dialogChooseStarters()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ArrayList<String> mSelectedItems = new ArrayList<>();
        builder.setTitle("Choose players here").setMultiChoiceItems(, null,
                new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        })
        };*/



}
