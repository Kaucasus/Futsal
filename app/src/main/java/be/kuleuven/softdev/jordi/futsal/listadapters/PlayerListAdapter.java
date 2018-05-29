package be.kuleuven.softdev.jordi.futsal.listadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.R;
import be.kuleuven.softdev.jordi.futsal.classes.Player;


public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {
    private ArrayList<Player> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView playerText;

        public PlayerViewHolder(View v) {
            super(v);
            playerText = (TextView) v.findViewById(R.id.name_id);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PlayerListAdapter(Context context, ArrayList<Player> myDataset) {
        mContext = context;
        mDataset = myDataset;
        Log.d("PlayerListAdapter",
                "First thing: "+(myDataset == null || myDataset.size() <1 ? "null" :myDataset.get(0).getName()));
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String playerName = mDataset.get(position).getName();
        holder.playerText.setText(playerName);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_row, parent, false);


        PlayerViewHolder vh = new PlayerViewHolder(v);
        return vh;
    }

}


