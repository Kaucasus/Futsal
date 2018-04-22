package be.kuleuven.softdev.jordi.futsal.listadapters;

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

    /**
     * View holder class
     * */
    public class SubstitionViewHolder extends RecyclerView.ViewHolder {
        public TextView inText1;
        public TextView inText2;
        public TextView outText1;
        public TextView outText2;
        public TextView timeText;

        public SubstitionViewHolder(View view) {
            super(view);
            inText1 = (TextView) view.findViewById(R.id.in_1);
            inText2 = (TextView) view.findViewById(R.id.in_2);
            outText1 = (TextView) view.findViewById(R.id.out_1);
            outText2 = (TextView) view.findViewById(R.id.out_2);
            timeText = (TextView) view.findViewById(R.id.time);


        }
    }

    public SubstitutionListAdapter(ArrayList<Substitution> substitutions) {
        this.substitutions= substitutions;
    }

    @Override
    public void onBindViewHolder(SubstitionViewHolder holder, int position) {
        Substitution substitution = substitutions.get(position);
        holder.inText1.setText(substitution.getIn().get(0).getName());
        holder.inText2.setText(substitution.getIn().get(1).getName());
        holder.outText1.setText(substitution.getOut().get(0).getName());
        holder.outText2.setText(substitution.getOut().get(1).getName());
        holder.timeText.setText(String.valueOf(substitution.getTime()));
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
