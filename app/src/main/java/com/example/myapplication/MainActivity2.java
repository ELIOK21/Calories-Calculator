package com.example.myapplication;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView calorieTextView = findViewById(R.id.calorieTextView);
        double calories = getIntent().getDoubleExtra("calories", 0.0);
        calorieTextView.setText("You should eat " + calories + " calories per day");
    }
}
