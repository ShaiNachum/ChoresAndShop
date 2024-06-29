package com.example.choresandshop.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choresandshop.Chore;
import com.example.choresandshop.R;
import com.example.choresandshop.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment { //Chores

    private FragmentHomeBinding binding;

    private RecyclerView chore_RV_list;

    private MaterialButton chore_MB_NewChore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        ArrayList<Chore> chores = new ArrayList<>();
        chores.add(new Chore().setName("lolo").setPrice(10));
        chores.add(new Chore().setName("colo").setPrice(20));
        chores.add(new Chore().setName("polo").setPrice(30));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        chore_RV_list.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}