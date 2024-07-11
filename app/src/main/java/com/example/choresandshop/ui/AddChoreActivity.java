package com.example.choresandshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.choresandshop.CurrentUserManager;
import com.example.choresandshop.Model.CreatedBy;
import com.example.choresandshop.Model.Object;
import com.example.choresandshop.Model.UserId;
import com.example.choresandshop.R;
import com.example.choresandshop.UserApi.ApiController;
import com.example.choresandshop.UserApi.ObjectApi;
import com.example.choresandshop.UserApi.UserApi;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChoreActivity extends AppCompatActivity {

    private MaterialTextView add_chore_LBL_enter_name;
    private AppCompatEditText add_chore_EDT_name;
    private MaterialTextView add_chore_LBL_enter_price;
    private AppCompatEditText add_chore_EDT_price;
    private CurrentUserManager currentUserManager;
    private MaterialButton add_chore_BTN_create;
    ObjectApi ObjectApiService = ApiController.getRetrofitInstance().create(ObjectApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);
        currentUserManager = CurrentUserManager.getInstance();
        findViews();
        initViews();
    }

    private void initViews() {
        add_chore_BTN_create.setOnClickListener(v -> {
            //TODO:Create Object
            Object object = new Object();
            object.setObjectDetails(new HashMap<>());
            object.setActive(true);
            object.setType(add_chore_EDT_name.getText().toString());
            object.setAlias("CHORE" + "#" + add_chore_EDT_price.getText().toString());
            UserId userId = new UserId();
            userId.setEmail(currentUserManager.getUser().getUserId().getEmail());
            userId.setSuperapp(currentUserManager.getUser().getUserId().getSuperapp());
            CreatedBy createdBy = new CreatedBy();
            createdBy.setUserId(userId);
            object.setCreatedBy(createdBy);
            // TODO: POST
            Call<Object> call = ObjectApiService.createObject(object);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Object o = response.body();
                    if (response.isSuccessful() && o != null) {
                        Toast.makeText(AddChoreActivity.this, "Chore created successfully", Toast.LENGTH_LONG).show(); //Gets here

                    } else {
                        Toast.makeText(AddChoreActivity.this, "Failed to create chore", Toast.LENGTH_LONG).show();
                        Log.e("Request failed: ", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable throwable) {
                    Toast.makeText(AddChoreActivity.this, "Failed to create chore 2", Toast.LENGTH_LONG).show();

                }
            });
            // TODO: Close activity
            Intent intent = new Intent(AddChoreActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        });
    }

    private void findViews() {
        add_chore_LBL_enter_name = findViewById(R.id.add_chore_LBL_enter_name);
        add_chore_EDT_name = findViewById(R.id.add_chore_EDT_name);
        add_chore_LBL_enter_price = findViewById(R.id.add_chore_LBL_enter_price);
        add_chore_EDT_price = findViewById(R.id.add_chore_EDT_price);
        add_chore_BTN_create = findViewById(R.id.add_chore_BTN_create);
    }
}