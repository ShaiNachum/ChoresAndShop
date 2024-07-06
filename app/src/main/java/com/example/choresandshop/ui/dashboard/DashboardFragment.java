package com.example.choresandshop.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choresandshop.CurrentUserManager;
import com.example.choresandshop.R;
import com.example.choresandshop.databinding.FragmentDashboardBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class DashboardFragment extends Fragment { //Shop
    private FragmentDashboardBinding binding;
    private CurrentUserManager currentUserManager;
    private MaterialTextView chores_MTV_choresText;
    private RecyclerView shop_RV_list;
    private MaterialButton shop_MB_NewPrize;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    private void findVies(View view) {
        chores_MTV_choresText = view.findViewById(R.id.chores_MTV_choresText);
        shop_RV_list = view.findViewById(R.id.shop_RV_list);
        shop_MB_NewPrize = view.findViewById(R.id.shop_MB_NewPrize);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findVies(view);
    }
}