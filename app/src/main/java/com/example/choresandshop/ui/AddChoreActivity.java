package com.example.choresandshop.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.choresandshop.R;
import com.google.android.material.textview.MaterialTextView;

public class AddChoreActivity extends AppCompatActivity {

    private MaterialTextView add_chore_LBL_enter_name;
    private AppCompatEditText add_chore_EDT_name;
    private MaterialTextView add_chore_LBL_enter_price;
    private AppCompatEditText add_chore_EDT_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);

        findViews();

    }

    private void findViews() {
        add_chore_LBL_enter_name = findViewById(R.id.add_chore_LBL_enter_name);
        add_chore_EDT_name = findViewById(R.id.add_chore_EDT_name);
        add_chore_LBL_enter_price = findViewById(R.id.add_chore_LBL_enter_price);
        add_chore_EDT_price = findViewById(R.id.add_chore_EDT_price);
    }
}