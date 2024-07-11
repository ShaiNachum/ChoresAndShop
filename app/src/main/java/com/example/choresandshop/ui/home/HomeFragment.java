package com.example.choresandshop.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choresandshop.Callbacks.ChoreCheckedCallback;
import com.example.choresandshop.CurrentUserManager;
import com.example.choresandshop.Model.Chore;
import com.example.choresandshop.Adapters.ChoresAdapter;
import com.example.choresandshop.Model.CreatedBy;
import com.example.choresandshop.Model.Object;
import com.example.choresandshop.Model.User;
import com.example.choresandshop.Model.UserId;
import com.example.choresandshop.R;
import com.example.choresandshop.UserApi.ApiController;
import com.example.choresandshop.UserApi.ObjectApi;
import com.example.choresandshop.UserApi.UserApi;
import com.example.choresandshop.databinding.FragmentHomeBinding;
import com.example.choresandshop.ui.AddChoreActivity;
import com.example.choresandshop.ui.MainActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ChoreCheckedCallback { //Chores
    private FragmentHomeBinding binding;
    private ChoresAdapter choresAdapter;
    private RecyclerView chore_RV_list;
    private MaterialButton chore_MB_NewChore;
    private ObjectApi objectApi;
    private UserApi userApi;
    private CurrentUserManager currentUserManager;
    private ArrayList<Chore> chores;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUserManager = CurrentUserManager.getInstance();
        objectApi =  ApiController.getRetrofitInstance().create(ObjectApi.class);
        userApi = ApiController.getRetrofitInstance().create(UserApi.class);
        findViews(view);
        initViews(view);
    }

    private void findViews(View view) {
        chore_RV_list = view.findViewById(R.id.chore_RV_list);
        chore_MB_NewChore = view.findViewById(R.id.chore_MB_NewChore);

    }

    private void initViews(View view) {
        User user = currentUserManager.getUser();
        if(currentUserManager.getUser().getAvatar().split("#")[0].equals("parent"))
            chore_MB_NewChore.setVisibility(View.VISIBLE);
        else
            chore_MB_NewChore.setVisibility(View.INVISIBLE);
        chore_MB_NewChore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddChoreActivity.class);
            startActivity(intent);
        });
//        ArrayList<Chore> chores = new ArrayList<>();
        Call<Object[]> call = objectApi.getChores("CHORE%", "MiniHeros", user.getUserId().getEmail(), 10, 0);
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
        chores = new ArrayList<>();
        for (Object object:objects)
        {
            Chore chore = new Chore();
            chore.setId(object.getObjectId().getId());
            String[] alias = object.getAlias().split("#");
            chore.setName(object.getType());
            chore.setPrice(Integer.parseInt(alias[1]));
            chore.setDone(!object.getActive());
            //TODO: chore.setDoneBy
            chores.add(chore);
        }
        choresAdapter = new ChoresAdapter(view.getContext(),chores,this);
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
    @Override
    public void onChoreChecked(int position, boolean isChecked) {
        Chore chore = chores.get(position);
        chore.setDone(isChecked);

        //TODO add coins to user
        String[] NewAvatar = currentUserManager.getUser().getAvatar().split("#");
        NewAvatar[0] = (Integer.valueOf(NewAvatar[1]) +chore.getPrice()) +"";
        currentUserManager.getUser().setAvatar(NewAvatar[0] +"#" + NewAvatar[1]);
        Call<Void> call = userApi.updateUser(
                "MiniHeros"
                ,currentUserManager.getUser().getUserId().getEmail()
                ,currentUserManager.getUser());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(), "User updated succesfully " , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(getContext(), "Failed update user ", Toast.LENGTH_SHORT).show();

            }
        });

        Toast.makeText(getContext(), "Chore " + chore.getName() + " checked: " + isChecked, Toast.LENGTH_SHORT).show();

        //TODO get object from DB

        Call<Object> ObjectFromDBcall = objectApi.getObject("MiniHeros",
                chore.getId(),
                "MiniHeros",
                currentUserManager.getUser().getUserId().getEmail());
        ObjectFromDBcall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    Object objectFromDB = response.body();
                    if (objectFromDB != null) {
                        objectFromDB.setActive(false);
                        objectFromDB.setCreationTimestamp(null);
                        Map<String, java.lang.Object> NewObjectDetails = objectFromDB.getObjectDetails();
                        NewObjectDetails.put("Done by",currentUserManager.getUser().getUserId().getEmail());
                        objectFromDB.setObjectDetails(NewObjectDetails);

                        updateObjectInDB(objectFromDB);
                        Toast.makeText(getContext(), "Success reading object " + chore.getName(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Failed " + chore.getName(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed2 " + chore.getName(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable throwable) {
                Toast.makeText(getContext(), "Failed3 " + chore.getName(), Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void updateObjectInDB(Object o){
        Call<Void> call = objectApi.updateObject(
                o.getObjectId().getSuperapp()
                ,o.getObjectId().getId()
                ,o.getObjectId().getSuperapp()
                ,currentUserManager.getUser().getUserId().getEmail()
                ,o);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Updated Successfully " , Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Update Failed ", Toast.LENGTH_SHORT).show();
                    DoResponse(response); //Gets Here
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(getContext(), "Update Failed 2 ", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void DoResponse(Response<Void> response){
        Log.e("Response",response.toString());
    }
}