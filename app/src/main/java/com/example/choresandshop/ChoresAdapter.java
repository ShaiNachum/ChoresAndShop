package com.example.choresandshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChoresAdapter extends RecyclerView.Adapter<ChoresAdapter.ChoresViewHolder> {

    private Context context;
    private ArrayList<Chore> chores;

    public ChoresAdapter(Context context, ArrayList<Chore> chores) {
        this.context = context;
        this.chores = chores;
    }

    @NonNull
    @Override
    public ChoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chore_card,parent,false);
        return new ChoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoresViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return chores.size();
    }

    public static class ChoresViewHolder extends RecyclerView.ViewHolder{

        public ChoresViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
