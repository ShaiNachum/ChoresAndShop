package com.example.choresandshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choresandshop.CurrentUserManager;
import com.example.choresandshop.Model.User;
import com.example.choresandshop.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder> {

    private Context context;
    private ArrayList<User> FamilyList;
    private CurrentUserManager currentUserManager;

    public FamilyAdapter(Context context, ArrayList<User> FamilyList) {
        this.context = context;
        this.FamilyList = FamilyList;
        currentUserManager = CurrentUserManager.getInstance();
        sortFamilyList();
    }

    private void sortFamilyList() {
        Collections.sort(FamilyList, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                // Check if both are parents
                if (!user1.getAvatar().split("#")[0].equals("child") && !user2.getAvatar().split("#")[0].equals("child")) {
                    return 0;
                }
                // Check if user1 is a parent and user2 is a child
                if (!user1.getAvatar().split("#")[0].equals("child")) {
                    return -1;
                }
                // Check if user1 is a child and user2 is a parent
                if (!user2.getAvatar().split("#")[0].equals("child")) {
                    return 1;
                }
                // Both are children, sort by points
                int points1 = Integer.parseInt(user1.getAvatar().split("#")[1]);
                int points2 = Integer.parseInt(user2.getAvatar().split("#")[1]);
                return Integer.compare(points2, points1);
            }
        });
    }

    @NonNull
    @Override
    public FamilyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.family_member_card, parent, false);
        return new FamilyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyViewHolder holder, int position) {
        User member = FamilyList.get(position);
        holder.FMC_MTV_name.setText(member.getUserId().getEmail().split("@")[0]);
        if(member.getAvatar().split("#")[0].equals("child")) {
            holder.FMC_MTV_points.setText(member.getAvatar().split("#")[1]);
        }
        else {
            holder.FMC_MTV_points.setText("Parent");
        }
        if(member.getUserId().getEmail().equals(currentUserManager.getUser().getUserId().getEmail())){
            holder.FMC_RL.setBackgroundColor(ContextCompat.getColor(context,R.color.teal_200));
        }

    }

    @Override
    public int getItemCount() {
        return FamilyList.size();
    }

    public static class FamilyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout FMC_RL;
        private MaterialTextView FMC_MTV_name;
        private MaterialTextView FMC_MTV_points;

        public FamilyViewHolder(@NonNull View itemView) {
            super(itemView);
            FMC_RL = itemView.findViewById(R.id.FMC_RL);
            FMC_MTV_name = itemView.findViewById(R.id.FMC_MTV_name);
            FMC_MTV_points = itemView.findViewById(R.id.FMC_MTV_points);
        }
    }
}
