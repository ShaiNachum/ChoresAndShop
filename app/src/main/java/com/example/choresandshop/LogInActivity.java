package com.example.choresandshop;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LogInActivity extends AppCompatActivity {
    private TextInputEditText Login_TIET_UserNameInput;
    private MaterialButton Login_MB_SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        findViews();

        Login_MB_SignIn.setOnClickListener(v -> {
            String UserName = Login_TIET_UserNameInput.getText().toString();
            //TODO - check user in dataBase


        });
    }





    private void findViews() {
        Login_TIET_UserNameInput = findViewById(R.id.Login_TIET_UserNameInput);
        Login_MB_SignIn = findViewById(R.id.Login_MB_SignIn);
    }
}