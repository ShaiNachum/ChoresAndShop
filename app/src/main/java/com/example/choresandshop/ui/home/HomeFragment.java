package com.example.choresandshop.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choresandshop.CurrentUserManager;
import com.example.choresandshop.Model.Chore;
import com.example.choresandshop.Adapters.ChoresAdapter;
import com.example.choresandshop.Model.Object;
import com.example.choresandshop.Model.User;
import com.example.choresandshop.R;
import com.example.choresandshop.UserApi.ApiController;
import com.example.choresandshop.UserApi.ObjectApi;
import com.example.choresandshop.UserApi.UserApi;
import com.example.choresandshop.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment { //Chores
    private FragmentHomeBinding binding;
    private ChoresAdapter choresAdapter;
    private RecyclerView chore_RV_list;
    private MaterialButton chore_MB_NewChore;
    private List<Chore> chores;
    private ObjectApi objectApi;

    private CurrentUserManager currentUserManager;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

   //     final TextView textView = binding.textHome;
    //    homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUserManager = CurrentUserManager.getInstance();
        objectApi =  ApiController.getRetrofitInstance().create(ObjectApi.class);
        findViews(view);
        initViews(view);
    }

    private void findViews(View view) {
        chore_RV_list = view.findViewById(R.id.chore_RV_list);
        chore_MB_NewChore = view.findViewById(R.id.chore_MB_NewChore);
    }

    private void initViews(View view) {
        chore_MB_NewChore.setOnClickListener(v -> {
            //TODO - New Chore create
        });

        User user = currentUserManager.getUser();

//        ArrayList<Chore> chores = new ArrayList<>();
        Call<Object[]> call = objectApi.getAllChores("chore", "Mini_Heros", user.getUserId().getEmail(), 10, 1);
        call.enqueue(new Callback<Object[]>() {
            @Override
            public void onResponse(Call<Object[]> call, Response<Object[]> response) {
                if (response.isSuccessful()) {
                    Object[] FetchedObjects = response.body();
                    if(FetchedObjects != null){
                        FetchDataToAdapter(view,new ArrayList<>(Arrays.asList(FetchedObjects)));
                    }
                    else {
                        FetchDataToAdapter(view,new ArrayList<>());

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

    private void FetchDataToAdapter(View view,ArrayList<Object> objects){
        ArrayList<Chore> chores = new ArrayList<>();
        for (Object object:objects)
        {
            Chore chore = new Chore();
            String[] alias = object.getAlias().split("#");
            chore.setName(alias[0]);
            chore.setPrice(Integer.parseInt(alias[1]));
            chores.add(chore);
        }
        choresAdapter = new ChoresAdapter(view.getContext(),chores);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        chore_RV_list.setLayoutManager(linearLayoutManager);
        chore_RV_list.setAdapter(choresAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}