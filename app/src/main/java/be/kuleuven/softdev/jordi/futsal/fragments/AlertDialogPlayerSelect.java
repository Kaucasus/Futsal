package be.kuleuven.softdev.jordi.futsal.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import be.kuleuven.softdev.jordi.futsal.R;

public class AlertDialogPlayerSelect extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("pick a player")
                    .setItems(R.array.known_players, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                        }
                    });

            return builder.create();
    }

}

