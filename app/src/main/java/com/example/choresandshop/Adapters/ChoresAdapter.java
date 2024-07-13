package com.example.choresandshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choresandshop.Callbacks.ChoreCheckedCallback;
import com.example.choresandshop.CurrentUserManager;
import com.example.choresandshop.Model.Chore;
import com.example.choresandshop.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChoresAdapter extends RecyclerView.Adapter<ChoresAdapter.ChoresViewHolder> {

    private Context context;
    private ArrayList<Chore> chores;
    private ChoreCheckedCallback choreCheckedCallback;
    private CurrentUserManager currentUserManager;


    public ChoresAdapter(Context context, ArrayList<Chore> chores, ChoreCheckedCallback choreCheckedCallback) {
        this.context = context;
        this.chores = chores;
        this.choreCheckedCallback = choreCheckedCallback;
        currentUserManager = CurrentUserManager.getInstance();
    }



    @NonNull
    @Override
    public ChoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chore_card,parent,false);
        return new ChoresViewHolder(view,choreCheckedCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoresViewHolder holder, int position) {
        Chore chore = chores.get(position);
        holder.choreName.setText(chore.getName());
        holder.chorePrice.setText(String.valueOf(chore.getPrice()));
        holder.doneCheckBox.setChecked(chore.isDone());
        holder.chores_MTV_DoneBy.setText("Done By: " + chore.getDoneBy());
        if(currentUserManager.getUser().getAvatar().split("#")[0].equals("parent")) {
            holder.doneCheckBox.setVisibility(View.INVISIBLE);
            if (chore.isDone())
                holder.chore_card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }

            if (chore.isDone()) {
                holder.doneCheckBox.setEnabled(false);
            } else {
                holder.doneCheckBox.setEnabled(true);
                holder.chores_MTV_DoneBy.setVisibility(View.GONE);
            }

        holder.doneCheckBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            //position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                choreCheckedCallback.onChoreChecked(position, isChecked);
                if (isChecked) {
                    holder.doneCheckBox.setEnabled(false);
                }
                chore.setDoneBy(currentUserManager.getUser().getUserId().getEmail().split("@")[0]);
                notifyItemChanged(position);
            }
        }));

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
        private MaterialTextView chores_MTV_DoneBy;
        private CardView chore_card;

        public ChoresViewHolder(@NonNull View itemView, ChoreCheckedCallback choreCheckedCallback) {
            super(itemView);
            choreName = itemView.findViewById(R.id.chore_LBL_chore);
            chorePrice = itemView.findViewById(R.id.chore_LBL_prize);
            coinsPic = itemView.findViewById(R.id.chore_SIV_coinsPic);
            doneCheckBox = itemView.findViewById(R.id.chore_TB_done);
            chores_MTV_DoneBy = itemView.findViewById(R.id.chores_MTV_DoneBy);
            chore_card = itemView.findViewById(R.id.chore_card);
            //TODO doneCheckBox listener
//            doneCheckBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    choreCheckedCallback.onChoreChecked(position, isChecked);
//                    if (isChecked) {
//                        doneCheckBox.setEnabled(false);
//                    }
//                }
//            }));
        }
    }
}
