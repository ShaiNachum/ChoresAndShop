package com.example.choresandshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.choresandshop.CurrentUserManager;
import com.example.choresandshop.Model.User;
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
    private MaterialTextView welcome_LBL_enter_email;
    private AppCompatEditText welcome_EDT_email;
    private MaterialButton welcome_BTN_login;
    private MaterialTextView welcome_MTV_signUp;
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
        String email = welcome_EDT_email.getText().toString();
        Call<User> call = apiService.findUser("MiniHeros", email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                if (response.isSuccessful()  && user != null) {
                    Toast.makeText(WelcomeActivity.this, "Hello " + user.getUsername().toString(), Toast.LENGTH_LONG).show();
                    SwitchToMainActivty(user);

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

    private void SwitchToMainActivty(User user) {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        CurrentUserManager manager = CurrentUserManager.getInstance();
        manager.setUser(user);
        startActivity(intent);
        this.finish();
    }

    private void SwitchToSignUpActivity(){
        Intent intent = new Intent(WelcomeActivity.this, ActivitySignUp.class);
        startActivity(intent);
        this.finish();
    }

    private void initViews() {
        welcome_BTN_login.setOnClickListener(View -> logInClicked());
        welcome_MTV_signUp.setOnClickListener(View -> SwitchToSignUpActivity());
    }

    private void findViews() {
        welcome_LBL_welcome = findViewById(R.id.welcome_LBL_welcome);
        welcome_BTN_login = findViewById(R.id.welcome_BTN_login);
        welcome_LBL_enter_email = findViewById(R.id.welcome_LBL_enter_email);
        welcome_EDT_email = findViewById(R.id.welcome_EDT_email);
        welcome_MTV_signUp = findViewById(R.id.welcome_MTV_signUp);
    }
}