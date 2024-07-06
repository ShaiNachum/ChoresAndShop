package com.example.choresandshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choresandshop.Model.Chore;
import com.example.choresandshop.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

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
        Chore chore = chores.get(position);
        holder.choreName.setText(chore.getName());
        holder.chorePrice.setText(String.valueOf(chore.getPrice()));
    }

    @Override
    public int getItemCount() {
        return chores.size();
    }

    public static class ChoresViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView choreName;
        private MaterialTextView chorePrice;
        private ShapeableImageView coinsPic;
        private CheckBox doneCheckBox;

        public ChoresViewHolder(@NonNull View itemView) {
            super(itemView);
            choreName = itemView.findViewById(R.id.chore_LBL_chore);
            chorePrice = itemView.findViewById(R.id.chore_LBL_prize);
            coinsPic = itemView.findViewById(R.id.chore_SIV_coinsPic);
            doneCheckBox = itemView.findViewById(R.id.chore_TB_done);
        }
    }
}
