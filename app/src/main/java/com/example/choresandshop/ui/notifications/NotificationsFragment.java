package com.example.choresandshop.ui.notifications;

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

import com.example.choresandshop.Adapters.FamilyAdapter;
import com.example.choresandshop.CurrentUserManager;
import com.example.choresandshop.Model.User;
import com.example.choresandshop.R;
import com.example.choresandshop.UserApi.ApiController;
import com.example.choresandshop.UserApi.UserApi;
import com.example.choresandshop.databinding.FragmentNotificationsBinding;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment { //Family Manager
    private FragmentNotificationsBinding binding;
    private FamilyAdapter familyAdapter;
    private RecyclerView family_RV_list;
    private UserApi userApi;
    private CurrentUserManager currentUserManager;
    private ShapeableImageView family_SIV_left;
    private ShapeableImageView family_SIV_right;
    private static int CurrentPage = 0;
    private static int PageSize = 8;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUserManager = CurrentUserManager.getInstance();
        userApi = ApiController.getRetrofitInstance().create(UserApi.class);
        findViews(view);
        initViews(view);
    }

    private void findViews(View view) {
        family_RV_list = view.findViewById(R.id.family_RV_list);
        family_SIV_left = view.findViewById(R.id.family_SIV_left);
        family_SIV_right = view.findViewById(R.id.family_SIV_right);
    }

    private void initViews(View view) {
        User user = currentUserManager.getUser();
        PaginationSupport(view);
    }
    public void PaginationSupport(View view) {
        family_SIV_right.setOnClickListener(v -> {
            CurrentPage++;
            ReadDataFromDB(view);
        });

        family_SIV_left.setOnClickListener(v -> {
            CurrentPage--;
            ReadDataFromDB(view);
        });
        ReadDataFromDB(view);
    }

    public void ReadDataFromDB(View view){
        User user = currentUserManager.getUser();
        if (CurrentPage == 0)
            family_SIV_left.setVisibility(View.INVISIBLE);
        else
            family_SIV_left.setVisibility(View.VISIBLE);
        Call<User[]> call = userApi.getAllUsers("MiniHeros", user.getUserId().getEmail(),PageSize,CurrentPage);
        call.enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    User[] FamilyMembers = response.body();
                    if (FamilyMembers != null) {
                        FetchDataToAdapter(view, new ArrayList<>(Arrays.asList(FamilyMembers)));
                        checkNextPage(view);
                    }
                    else
                        FetchDataToAdapter(view, new ArrayList<>());
                } else
                    Toast.makeText(requireContext(), "Failed to fetch users", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<User[]> call, Throwable throwable) {
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkNextPage(View view){
        User user = currentUserManager.getUser();
        Call<User[]> call = userApi.getAllUsers("MiniHeros", user.getUserId().getEmail(),PageSize,CurrentPage+1);
        call.enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    User[] FamilyMembers = response.body();
                    if (FamilyMembers != null) {
                        if(FamilyMembers.length == 0)
                            family_SIV_right.setVisibility(View.INVISIBLE);
                        else
                            family_SIV_right.setVisibility(View.VISIBLE);
                    }
                    else
                        FetchDataToAdapter(view, new ArrayList<>());
                } else
                    Toast.makeText(requireContext(), "Failed to fetch users", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<User[]> call, Throwable throwable) {
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void FetchDataToAdapter(View view,ArrayList<User> FamilyMembers){
        familyAdapter = new FamilyAdapter(view.getContext(),FamilyMembers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        family_RV_list.setLayoutManager(linearLayoutManager);
        family_RV_list.setAdapter(familyAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}