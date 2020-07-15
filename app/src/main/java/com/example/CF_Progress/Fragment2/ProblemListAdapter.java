package com.example.CF_Progress.Fragment2;

import android.content.Context;
import android.system.StructTimeval;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.CF_Progress.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ViewHolder> implements Filterable {

    private static ClickListener clickListener;
    Context context;
    List<String> problemNames;
    List<String> problemNamesAll;
    List<Integer> problemRating;
    ArrayList<ArrayList<String>> problemTags;

    public ProblemListAdapter(Context context, List<String> problemNames, List<String> problemNamesAll,
                              ArrayList<ArrayList<String>> problemTags, List<Integer> problemRating) {
        this.context = context;
        this.problemNames = problemNames;
        this.problemNamesAll = problemNamesAll;
        this.problemTags = problemTags;
        this.problemRating = problemRating;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_2_sample_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.problemNameTV.setText(problemNames.get(position));
        holder.problemRatingTV.setText("R" + problemRating.get(position));
        String tags = "";
        for (int i = 0; i < problemTags.get(position).size(); i++) {
            tags += problemTags.get(position).get(i);
            if (i != problemTags.get(position).size() - 1) {
                tags += ", ";
            }
        }
        holder.problemTagTV.setText(tags);
    }

    @Override
    public int getItemCount() {
        return problemNames.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        // runs on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredList.addAll(problemNamesAll);
            } else {
                for (String problemsName: problemNamesAll) {
                    if (problemsName.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(problemsName);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        // runs on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            problemNames.clear();
            problemNames.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView problemNameTV, problemTagTV, problemRatingTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            problemNameTV = itemView.findViewById(R.id.problemNameId);
            problemTagTV = itemView.findViewById(R.id.problemTagId);
            problemRatingTV = itemView.findViewById(R.id.problemRatingId);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.OnItemClick(getAdapterPosition(), v);
        }
    }

    public interface ClickListener {
        void OnItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ProblemListAdapter.clickListener = clickListener;
    }
}
