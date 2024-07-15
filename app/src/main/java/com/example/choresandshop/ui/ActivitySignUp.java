package com.example.choresandshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.choresandshop.CurrentUserManager;
import com.example.choresandshop.Model.NewUser;
import com.example.choresandshop.Model.User;
import com.example.choresandshop.Model.UserRoleEnum;
import com.example.choresandshop.R;
import com.example.choresandshop.UserApi.ApiController;
import com.example.choresandshop.UserApi.UserApi;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySignUp extends AppCompatActivity {
    private MaterialTextView signup_LBL_signUp;
    private MaterialTextView signup_LBL_enter_familyName;
    private AppCompatEditText signup_EDT_familyName;
    private MaterialTextView signup_LBL_enter_email;
    private AppCompatEditText signup_EDT_email;
    private CheckBox signup_CB_parent;
    private CheckBox signup_CB_child;
    private MaterialButton signup_BTN_signup;
    private boolean isChild;
    UserApi apiService = ApiController.getRetrofitInstance().create(UserApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.isChild = false;

        findViews();
        initViews();
    }


    private void initViews() {
        signup_BTN_signup.setOnClickListener(View -> signUpClicked());
        signup_CB_parent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    signup_CB_child.setChecked(false);
                    isChild = false;
                }
            }
        });

        signup_CB_child.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    signup_CB_parent.setChecked(false);
                    isChild = true;
                }
            }
        });
    }

    private void signUpClicked() {
        String playerName = signup_EDT_familyName.getText().toString();

        String email = signup_EDT_email.getText().toString();

        NewUser newUser = new NewUser();
        newUser.setEmail(email);
        newUser.setRole(UserRoleEnum.SUPERAPP_USER);
        if(isChild) {
            newUser.setAvatar("child#0");
        }
        else {
            newUser.setAvatar("parent#0");
        }
        newUser.setUsername(playerName);

        Call<User> call = apiService.createUser(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User createdNewUser = response.body();
                if (response.isSuccessful()){
                    Toast.makeText(ActivitySignUp.this, "User created successfully!", Toast.LENGTH_LONG).show();
                    SwitchToMainActivty(createdNewUser);

                } else {
                    Toast.makeText(ActivitySignUp.this, "Failed to sign up user", Toast.LENGTH_LONG).show();
                    Log.e("Request failed: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ActivitySignUp.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isNotNullOrEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    private void findViews() {
        signup_LBL_signUp = findViewById(R.id.signup_LBL_signUp);
        signup_LBL_enter_familyName = findViewById(R.id.signup_LBL_enter_familyName);
        signup_EDT_familyName = findViewById(R.id.signup_EDT_familyName);
        signup_LBL_enter_email = findViewById(R.id.signup_LBL_enter_email);
        signup_EDT_email = findViewById(R.id.signup_EDT_email);
        signup_CB_parent = findViewById(R.id.signup_CB_parent);
        signup_CB_child = findViewById(R.id.signup_CB_child);
        signup_BTN_signup = findViewById(R.id.signup_BTN_signup);
    }

    private void SwitchToMainActivty(User user) {
        Intent intent = new Intent(ActivitySignUp.this, MainActivity.class);
        CurrentUserManager manager = CurrentUserManager.getInstance();
        manager.setUser(user);
        startActivity(intent);
        this.finish();
    }

}