package be.kuleuven.softdev.jordi.futsal.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.R;
import be.kuleuven.softdev.jordi.futsal.classes.Goal;
import be.kuleuven.softdev.jordi.futsal.classes.Player;
import be.kuleuven.softdev.jordi.futsal.listadapters.SpinnerItemClickListener;

public class AlertDialogGoal extends DialogFragment {

        /* The activity that creates an instance of this dialog fragment must
         * implement this interface in order to receive event callbacks.
         * Each method passes the DialogFragment in case the host needs to query it. */
        public interface NoticeDialogListener {
            public void onDialogPositiveClick(DialogFragment dialog);
            public void onDialogNegativeClick(DialogFragment dialog);
        }

        // Use this instance of the interface to deliver action events
        NoticeDialogListener mListener;

        // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            // Verify that the host activity implements the callback interface
            try {
                // Instantiate the NoticeDialogListener so we can send events to the host
                mListener = (NoticeDialogListener) activity;
            } catch (ClassCastException e) {
                // The activity doesn't implement the interface, throw exception
                throw new ClassCastException(activity.toString()
                        + " must implement NoticeDialogListener");
            }
        }


    public AlertDialogGoal() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        ArrayList<Player> selection = arguments.getParcelableArrayList("players");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater li = getLayoutInflater();
        View alertLayout = li.inflate(R.layout.alert_dialog_goal_scored, null);

        ArrayList<Player> goalmakers = selection;
        ArrayList<Player> assisters = selection;

        Spinner goalmaker = alertLayout.findViewById(R.id.goalmaker_spinner);
        ArrayAdapter<Player> gmAdapter = new ArrayAdapter<Player>(getContext()
                , android.R.layout.simple_spinner_item, goalmakers);
        gmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalmaker.setAdapter(gmAdapter);
        goalmaker.setOnItemSelectedListener(new

                                                    SpinnerItemClickListener() {

                                                    });

        Spinner assister = alertLayout.findViewById(R.id.assister_spinner);
        ArrayAdapter<Player> assistAdapter = new ArrayAdapter<Player>(getContext()
                , android.R.layout.simple_spinner_item, assisters);
        assistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assister.setAdapter(assistAdapter);
        assister.setOnItemSelectedListener(new

                                                   SpinnerItemClickListener() {

                                                   });

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Goal scored")
                .setView(alertLayout)
                .setCancelable(false)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(AlertDialogGoal.this);
                    }
                });

        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogPositiveClick(AlertDialogGoal.this);

            }
        });
        return builder.create();
    }

}
