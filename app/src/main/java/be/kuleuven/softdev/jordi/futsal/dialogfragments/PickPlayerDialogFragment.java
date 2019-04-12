package be.kuleuven.softdev.jordi.futsal.dialogfragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import be.kuleuven.softdev.jordi.futsal.R;
import be.kuleuven.softdev.jordi.futsal.classes.Player;

public class PickPlayerDialogFragment extends DialogFragment {

    private PickPlayerDialogFragmentListener mListener;
    ArrayList<Integer> selectedItems;
    ArrayList<Player> players;

    public PickPlayerDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        players = getArguments().getParcelableArrayList("players");
        ArrayList<String> playerString = copyToString(players);
        selectedItems = new ArrayList<>();

        String[] names = new String[players.size()];
        names = playerString.toArray(names);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select players")
                .setMultiChoiceItems(names, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    selectedItems.add(which);
                                } else if (selectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    selectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })


                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<Player> chosenPlayers = new ArrayList<>();
                        for (Integer position:selectedItems
                        ) {
                            chosenPlayers.add(players.get(position));

                        }
                        mListener.onDialogPlayerPositiveClick(PickPlayerDialogFragment.this,chosenPlayers);
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_fragment_pick_player, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PickPlayerDialogFragmentListener) {
            mListener = (PickPlayerDialogFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMatchSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface PickPlayerDialogFragmentListener {
        public void onDialogPlayerPositiveClick(DialogFragment dialog, ArrayList<Player> players);

    }

    private ArrayList<String> copyToString(ArrayList<Player> players){
        ArrayList<String> names= new ArrayList<>();
        for (Player player:players
        ) {
            names.add(player.getName());
        }
        return names;

    }
}
