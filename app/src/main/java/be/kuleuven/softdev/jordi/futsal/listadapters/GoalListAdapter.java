package be.kuleuven.softdev.jordi.futsal.listadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.R;
import be.kuleuven.softdev.jordi.futsal.classes.Goal;

public class GoalListAdapter  extends  RecyclerView.Adapter<GoalListAdapter.GoalViewHolder> {
    private ArrayList<Goal> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView goalText;
        public TextView assistText;
        public TextView scoreText;


        public GoalViewHolder(View v) {
            super(v);
            goalText = (TextView) v.findViewById(R.id.goalmaker_recyclerview);
            assistText = (TextView) v.findViewById(R.id.assister_recyclerview);
            scoreText = (TextView) v.findViewById(R.id.score_recyclerview);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GoalListAdapter(Context context, ArrayList<Goal> myDataset) {
        mContext = context;
        mDataset = myDataset;

    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(GoalViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String goalPlayerName = mDataset.get(position).getScorer().getName();
        holder.goalText.setText(goalPlayerName);
        String scorerPlayerName = mDataset.get(position).getAssister().getName();
        holder.assistText.setText(scorerPlayerName);
        String score = mDataset.get(position).getScore().scoreBoardString();
        holder.scoreText.setText(score);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Create new views (invoked by the layout manager)

    @Override
    public GoalViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_row, parent, false);


        GoalViewHolder vh = new GoalViewHolder(v);
        return vh;
    }
}
