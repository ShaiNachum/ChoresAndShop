package com.example.choresandshop.ui.dashboard;

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

import com.example.choresandshop.MainActivity;
import com.example.choresandshop.R;
import com.example.choresandshop.WelcomeActivity;
import com.example.choresandshop.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment { //Shop

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    //    View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Retrieve arguments


        // Now you can use superapp, email, avatar in your fragment




   //     DashboardViewModel dashboardViewModel =
    //            new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String superapp = getArguments().getString("superapp");
        String email = getArguments().getString("email");
        String avatar = getArguments().getString("avatar");

    //    final TextView textView = binding.textDashboard;
    //    dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        TextView textView = binding.textDashboard;
        textView.setText("Superapp: " + superapp + "\nEmail: " + email + "\nAvatar: " + avatar);


        return root;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}