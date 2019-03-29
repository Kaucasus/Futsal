package be.kuleuven.softdev.jordi.futsal.listadapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.R;
import be.kuleuven.softdev.jordi.futsal.classes.Substitution;

public class SubstitutionListAdapter extends
        RecyclerView.Adapter<SubstitutionListAdapter.SubstitionViewHolder> {
    private ArrayList<Substitution> substitutions;
    private Context context;

    // DONE: add a way for multiple substitutes (not implemented)
    public class SubstitionViewHolder extends RecyclerView.ViewHolder {
        public TextView timeText;
        public RecyclerView inRecyclerView;
        public RecyclerView outRecyclerView;

        public SubstitionViewHolder(View view) {
            super(view);
            inRecyclerView = (RecyclerView) view.findViewById(R.id.player_in_recyclerview);
            outRecyclerView = (RecyclerView) view.findViewById(R.id.player_out_recyclerview);
            timeText = (TextView) view.findViewById(R.id.time);
        }
    }


    public SubstitutionListAdapter(ArrayList<Substitution> substitutions, Context context) {
        this.substitutions= substitutions;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(SubstitionViewHolder holder, int position) {
        Substitution substitution = substitutions.get(position);
        holder.timeText.setText(String.valueOf(substitution.getTime()));

        //Child Recyclerview Setup
        //In
        PlayerListAdapter inPlayerAdapter = new PlayerListAdapter(substitutions.get(position).getIn());
        holder.inRecyclerView.setAdapter(inPlayerAdapter);
        holder.inRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager inLayoutManager = new LinearLayoutManager(context);
        holder.inRecyclerView.setLayoutManager(inLayoutManager);
        //Out
        PlayerListAdapter outPlayerAdapter = new PlayerListAdapter(substitutions.get(position).getOut());
        holder.outRecyclerView.setAdapter(outPlayerAdapter);
        holder.outRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager outLayoutManager = new LinearLayoutManager(context);
        holder.outRecyclerView.setLayoutManager(outLayoutManager);

    }

    @Override
    public int getItemCount() {
        return substitutions.size();
    }

    @Override
    public SubstitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.substitute_row,parent, false);


        return new SubstitionViewHolder(v);
    }

}
