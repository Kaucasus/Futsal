package be.kuleuven.softdev.jordi.futsal.dialogfragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.R;
import be.kuleuven.softdev.jordi.futsal.classes.Player;
import be.kuleuven.softdev.jordi.futsal.listadapters.PlayerSpinAdapter;


public class GoalDialogFragment extends DialogFragment{
    public interface GoalDialogFragmentListener{
        public void onDialogPositiveClick(DialogFragment dialog,Player scorer, Player assister);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    GoalDialogFragmentListener listener;
    Player scorer;
    Player assister;


    public GoalDialogFragment()
    {

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (GoalDialogFragmentListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ArrayList<Player> players;
        scorer = null;
        assister = null;

        Bundle bundle = getArguments();
        players = bundle.getParcelableArrayList("fieldPlayers");


        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog_goal_scored,null);

        //Setup goal Spinner
        Spinner spinnerGoal = view.findViewById(R.id.goalmaker_spinner);
        PlayerSpinAdapter goalDataAdapter = new PlayerSpinAdapter(getActivity(),
                android.R.layout.simple_spinner_item, players) {
        };

        goalDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoal.setAdapter(goalDataAdapter);
        spinnerGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                scorer = (Player) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                scorer = null;
            }

        });

        //Setup assist Spinner
        Spinner spinnerAssist = view.findViewById(R.id.assister_spinner);
        PlayerSpinAdapter assistDataAdapter = new PlayerSpinAdapter(getActivity(),
                android.R.layout.simple_spinner_item, players);

        assistDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAssist.setAdapter(assistDataAdapter);
        spinnerAssist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                assister = (Player) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                assister = null;
            }

        });


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(GoalDialogFragment.this,scorer,assister);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GoalDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }




}
