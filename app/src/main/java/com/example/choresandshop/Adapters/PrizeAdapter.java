package com.example.choresandshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choresandshop.Callbacks.PrizePurchaseCallback;
import com.example.choresandshop.CurrentUserManager;
import com.example.choresandshop.Model.Chore;
import com.example.choresandshop.Model.ShopItem;
import com.example.choresandshop.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class PrizeAdapter extends RecyclerView.Adapter<PrizeAdapter.PrizesViewHolder> {
    private Context context;
    private ArrayList<ShopItem> shopItems;
    private PrizePurchaseCallback prizePurchaseCallback;
    private CurrentUserManager currentUserManager;

    public PrizeAdapter(Context context, ArrayList<ShopItem> shopItems, PrizePurchaseCallback prizePurchaseCallback) {
        this.context = context;
        this.shopItems = shopItems;
        this.prizePurchaseCallback = prizePurchaseCallback;
        currentUserManager = CurrentUserManager.getInstance();
    }

    @NonNull
    @Override
    public PrizesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prize_card,parent,false);
        return new PrizeAdapter.PrizesViewHolder(view, prizePurchaseCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull PrizesViewHolder holder, int position) {
        ShopItem shopItem = shopItems.get(position);
        holder.prize_MTV_description.setText(shopItem.getName());
        holder.prize_MTV_price.setText(String.valueOf(shopItem.getPrice()));
        if (currentUserManager.getUser().getAvatar().split("#")[0].equals("parent")) {
            holder.prize_MB_buy.setVisibility(View.GONE);
        }
        if(shopItem.isPurchased())
        {
            holder.prize_MB_buy.setVisibility(View.GONE);
            holder.prize_MTV_PurchasedBy.setText("Purchased by " + shopItem.getPurchasedBy());
        }
        else{
            holder.prize_MTV_PurchasedBy.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }

    public static class PrizesViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView prize_MTV_description;
        private ImageView prize_IV_IMG;
        private MaterialTextView prize_MTV_price;
        private MaterialButton prize_MB_buy;
        private MaterialTextView prize_MTV_PurchasedBy;

        public PrizesViewHolder(@NonNull View itemView, PrizePurchaseCallback prizePurchaseCallback) {
            super(itemView);
            prize_MTV_description = itemView.findViewById(R.id.prize_MTV_description);
            prize_IV_IMG = itemView.findViewById(R.id.prize_IV_IMG);
            prize_MTV_price = itemView.findViewById(R.id.prize_MTV_price);
            prize_MB_buy = itemView.findViewById(R.id.prize_MB_buy);
            prize_MTV_PurchasedBy = itemView.findViewById(R.id.prize_MTV_PurchasedBy);
            prize_MB_buy.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    prizePurchaseCallback.purchasePressed(position);
                }
            });
        }
    }

}
