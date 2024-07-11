package com.example.choresandshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPrizeActivity extends AppCompatActivity {
    private MaterialTextView add_prize_LBL_enter_name;
    private AppCompatEditText add_prize_EDT_name;
    private MaterialTextView add_prize_LBL_enter_price;
    private AppCompatEditText add_prize_EDT_price;
    private MaterialButton add_prize_BTN_create;
    private CurrentUserManager currentUserManager;
    ObjectApi ObjectApiService = ApiController.getRetrofitInstance().create(ObjectApi.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prize);
        currentUserManager = CurrentUserManager.getInstance();
        findViews();
        initViews();
    }

    private void findViews() {
        add_prize_LBL_enter_name = findViewById(R.id.add_prize_LBL_enter_name);
        add_prize_EDT_name = findViewById(R.id.add_prize_EDT_name);
        add_prize_LBL_enter_price = findViewById(R.id.add_prize_LBL_enter_price);
        add_prize_EDT_price = findViewById(R.id.add_prize_EDT_price);
        add_prize_BTN_create = findViewById(R.id.add_prize_BTN_create);
    }
    private void initViews() {
        add_prize_BTN_create.setOnClickListener(v -> {
            Object object = new Object();
            object.setObjectDetails(new HashMap<>());
            object.setActive(true);
            object.setType(add_prize_EDT_name.getText().toString());
            object.setAlias("PRIZE" + "#" + add_prize_EDT_price.getText().toString());
            UserId userId = new UserId();
            userId.setEmail(currentUserManager.getUser().getUserId().getEmail());
            userId.setSuperapp(currentUserManager.getUser().getUserId().getSuperapp());
            CreatedBy createdBy = new CreatedBy();
            createdBy.setUserId(userId);
            object.setCreatedBy(createdBy);
            Call<Object> call = ObjectApiService.createObject(object);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Object o = response.body();
                    if (response.isSuccessful() && o != null) {
                        Toast.makeText(AddPrizeActivity.this, "Chore created successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddPrizeActivity.this, "Failed to create chore", Toast.LENGTH_LONG).show();
                        Log.e("Request failed: ", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable throwable) {

                }
            });
            // TODO: Close activity
            Intent intent = new Intent(AddPrizeActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        });
    }


}