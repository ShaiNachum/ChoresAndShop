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
import com.example.choresandshop.UserApi.UserApi;
import com.example.choresandshop.UserApi.ApiController;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WelcomeActivity extends AppCompatActivity {
    private MaterialTextView welcome_LBL_welcome;
    private MaterialTextView welcome_LBL_enter_name;
    private AppCompatEditText welcome_EDT_name;
    private MaterialTextView welcome_LBL_enter_email;
    private AppCompatEditText welcome_EDT_email;
    private MaterialButton welcome_BTN_signup;
    private MaterialButton welcome_BTN_login;
    private CheckBox welcome_CB_parent;
    private CheckBox welcome_CB_child;
    private boolean isNameEntered;
    private boolean isChild;
    UserApi apiService = ApiController.getRetrofitInstance().create(UserApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        this.isChild = false;

        findViews();
        initViews();
    }


    private void logInClicked() {
        String playerName = welcome_EDT_name.getText().toString();
        if (isNotNullOrEmpty(playerName))
            this.isNameEntered = true;
        String email = welcome_EDT_email.getText().toString();
        Call<User> call = apiService.findUser("MiniHeros", email);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                if (response.isSuccessful()  && user != null) {
                    Toast.makeText(WelcomeActivity.this, "Hello " + user.getUsername().toString(), Toast.LENGTH_LONG).show();
                    SwitchToGameIntent(user);

                } else {
                    //Log.e("Retrofit", "Failed to retrieve user. Error code: " + response.code());
                    Toast.makeText(WelcomeActivity.this, "Failed to log in ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Retrofit", "Network error or failure: " + t.getMessage());
                Toast.makeText(WelcomeActivity.this, "SHIT HAPPENS ", Toast.LENGTH_LONG).show();

            }
        });
    }


    private void signUpClicked() {
        String playerName = welcome_EDT_name.getText().toString();
        if (isNotNullOrEmpty(playerName))
            this.isNameEntered = true;

        String email = welcome_EDT_email.getText().toString();

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

        Call<NewUser> call = apiService.createUser(newUser);
        call.enqueue(new Callback<NewUser>() {
            @Override
            public void onResponse(Call<NewUser> call, Response<NewUser> response) {
                NewUser createdNewUser = response.body();
                if (response.isSuccessful() && createdNewUser.getEmail() != newUser.getEmail()) {
                    Toast.makeText(WelcomeActivity.this, "User created successfully - now sign in", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(WelcomeActivity.this, "Failed to sign up user", Toast.LENGTH_LONG).show();
                    Log.e("Request failed: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<NewUser> call, Throwable t) {
                Toast.makeText(WelcomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public boolean isNotNullOrEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    private void SwitchToGameIntent(User user) {
        //       if(isNameEntered) {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//            intent.putExtra("superapp", superapp);
//            intent.putExtra("email", email);
//            intent.putExtra("avatar", avatar);
        CurrentUserManager manager = CurrentUserManager.getInstance();
        manager.setUser(user);
        startActivity(intent);
        this.finish();

        //      }
    }

    private void initViews() {
        welcome_BTN_signup.setOnClickListener(View -> signUpClicked());
        welcome_BTN_login.setOnClickListener(View -> logInClicked());

        welcome_CB_parent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    welcome_CB_child.setChecked(false);
                    isChild = false;
                }
            }
        });

        welcome_CB_child.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    welcome_CB_parent.setChecked(false);
                    isChild = true;
                }
            }
        });



    }

    private void findViews() {
        welcome_LBL_welcome = findViewById(R.id.welcome_LBL_welcome);
        welcome_LBL_enter_name = findViewById(R.id.welcome_LBL_enter_name);
        welcome_EDT_name = findViewById(R.id.welcome_EDT_name);
        welcome_BTN_signup = findViewById(R.id.welcome_BTN_signup);
        welcome_BTN_login = findViewById(R.id.welcome_BTN_login);
        welcome_LBL_enter_email = findViewById(R.id.welcome_LBL_enter_email);
        welcome_EDT_email = findViewById(R.id.welcome_EDT_email);
        welcome_CB_parent = findViewById(R.id.welcome_CB_parent);
        welcome_CB_child = findViewById(R.id.welcome_CB_child);
    }
}