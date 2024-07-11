package com.example.choresandshop.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choresandshop.Adapters.ChoresAdapter;
import com.example.choresandshop.Adapters.PrizeAdapter;
import com.example.choresandshop.Callbacks.PrizePurchaseCallback;
import com.example.choresandshop.CurrentUserManager;
import com.example.choresandshop.Model.Chore;
import com.example.choresandshop.Model.Object;
import com.example.choresandshop.Model.ShopItem;
import com.example.choresandshop.Model.User;
import com.example.choresandshop.R;
import com.example.choresandshop.UserApi.ApiController;
import com.example.choresandshop.UserApi.ObjectApi;
import com.example.choresandshop.UserApi.UserApi;
import com.example.choresandshop.databinding.FragmentDashboardBinding;
import com.example.choresandshop.ui.AddChoreActivity;
import com.example.choresandshop.ui.AddPrizeActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment implements PrizePurchaseCallback { //Shop
    private FragmentDashboardBinding binding;
    private RecyclerView shop_RV_list;
    private MaterialButton shop_MB_NewPrize;
    private CurrentUserManager currentUserManager;
    private ObjectApi objectApi;
    private UserApi userApi;
    private PrizeAdapter prizeAdapter;
    private ArrayList<Object> fetchedObjects;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUserManager = CurrentUserManager.getInstance();
        objectApi = ApiController.getRetrofitInstance().create(ObjectApi.class);
        userApi = ApiController.getRetrofitInstance().create(UserApi.class);
        findVies(view);
        initViews(view);
    }

    private void findVies(View view) {
        shop_RV_list = view.findViewById(R.id.shop_RV_list);
        shop_MB_NewPrize = view.findViewById(R.id.shop_MB_NewPrize);
    }

    private void initViews(View view) {
        User user = currentUserManager.getUser();
        if (currentUserManager.getUser().getAvatar().split("#")[0].equals("parent"))
            shop_MB_NewPrize.setVisibility(View.VISIBLE);
        else
            shop_MB_NewPrize.setVisibility(View.INVISIBLE);
        shop_MB_NewPrize.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddPrizeActivity.class);
            startActivity(intent);
        });
        Call<Object[]> call = objectApi.getChores("PRIZE%", "MiniHeros", user.getUserId().getEmail(), 10, 0);
        call.enqueue(new Callback<Object[]>() {
            @Override
            public void onResponse(Call<Object[]> call, Response<Object[]> response) {
                if (response.isSuccessful()) {
                    Object[] FetchedObjects = response.body();
                    if (FetchedObjects != null) {
                        FetchDataToAdapter(view, new ArrayList<>(Arrays.asList(FetchedObjects)));
                    } else {
                        FetchDataToAdapter(view, new ArrayList<>());
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch objects", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object[]> call, Throwable t) {
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void FetchDataToAdapter(View view, ArrayList<Object> objects) {
        this.fetchedObjects = objects;
        ArrayList<ShopItem> shopItems = new ArrayList<>();
        for (Object object : objects) {
            ShopItem shopItem = new ShopItem();
            String[] alias = object.getAlias().split("#");
            shopItem.setName(object.getType());
            shopItem.setPrice(Integer.parseInt(alias[1]));
            shopItem.setPurchased(!object.getActive());
            shopItems.add(shopItem);

        }
        prizeAdapter = new PrizeAdapter(view.getContext(), shopItems, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        shop_RV_list.setLayoutManager(gridLayoutManager);
        shop_RV_list.setAdapter(prizeAdapter);
    }

    @Override
    public void purchasePressed(int position) {
        Object object = fetchedObjects.get(position);
        int userBalance = Integer.parseInt(currentUserManager.getUser().getAvatar().split("#")[1]);
        int prizePrice = Integer.parseInt(object.getAlias().split("#")[1]);
//        if (prizePrice > userBalance) {
//            Toast.makeText(requireContext(), "Not enough coins ", Toast.LENGTH_SHORT).show();
//        } else {
            String[] newAvatar = currentUserManager.getUser().getAvatar().split("#");
            newAvatar[1] = (userBalance - prizePrice) + "";
            currentUserManager.getUser().setAvatar(newAvatar[0] + "#" + newAvatar[1]);
            Call<Void> call = userApi.updateUser("MiniHeros",
                    currentUserManager.getUser().getUserId().getEmail(),
                    currentUserManager.getUser());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(getContext(), "Successfully updated object", Toast.LENGTH_SHORT).show();
                    updateObjectPurchasedInDB(object);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {
                    Toast.makeText(getContext(), "Failed update user", Toast.LENGTH_SHORT).show();

                }
            });
        }
  //  }

    public void updateObjectPurchasedInDB(Object o){
        Map<String, java.lang.Object> NewObjectDetails = o.getObjectDetails();
        NewObjectDetails.put("Purchased by",currentUserManager.getUser().getUserId().getEmail());
        o.setObjectDetails(NewObjectDetails);
        o.setActive(false);
        Call<Void> call = objectApi.updateObject(
                "MiniHeros"
                ,o.getObjectId().getId()
                ,"MiniHeros"
                ,currentUserManager.getUser().getUserId().getEmail()
                ,o);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(), "Successfully updated object", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(getContext(), "Failed update object", Toast.LENGTH_SHORT).show();
            }
        });
    }
}