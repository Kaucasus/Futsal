package be.kuleuven.softdev.jordi.futsal.listadapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import be.kuleuven.softdev.jordi.futsal.R;
import be.kuleuven.softdev.jordi.futsal.classes.Substitution;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ParentViewHolder> {
        private ArrayList<Substitution> substitutions;

        /**
         * View holder class
         * */
        public class ParentViewHolder extends RecyclerView.ViewHolder {
            public RecyclerView parent1;
            public RecyclerView parent2;
            public TextView test;

            public ParentViewHolder(View view) {
                super(view);
                parent1 = (RecyclerView) view.findViewById(R.id.recyclerview_parent1);
                parent2 = (RecyclerView) view.findViewById(R.id.recyclerview_parent2);
                test = (TextView) view.findViewById(R.id.testID);


            }
        }

    public ParentAdapter(ArrayList<Substitution> substitutions) {
            this.substitutions= substitutions;
        }

        @Override
        public void onBindViewHolder(ParentViewHolder holder, int position) {
            Substitution substitution = substitutions.get(position);
            holder.test.setText(String.valueOf(substitutions.get(position).getTime()));
        }

        @Override
        public int getItemCount() {
            return substitutions.size();
        }

        @Override
        public ParentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.substitute_row,parent, false);


            return new ParentViewHolder(v);
        }

}
