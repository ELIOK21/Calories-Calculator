package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText weightEditText, heightEditText;
    RadioButton maleRadioButton,femaleRadioButton;
    Spinner ageSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightEditText = findViewById(R.id.editText1);
        heightEditText = findViewById(R.id.editText2);
        maleRadioButton = findViewById(R.id.radioButton);
        femaleRadioButton = findViewById(R.id.radioButton2);
        ageSpinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Age, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    calculateBMI();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter weight and height", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    if (maleRadioButton.isChecked() || femaleRadioButton.isChecked()) {
                        calculateCalories();
                    } else {
                        Toast.makeText(MainActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter all data needed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput() {
        String weight = weightEditText.getText().toString().trim();
        String height = heightEditText.getText().toString().trim();
        return !weight.isEmpty() && !height.isEmpty();
    }


    private void calculateBMI() {
        double weightInKg = Double.parseDouble(weightEditText.getText().toString());
        double heightInCm = Double.parseDouble(heightEditText.getText().toString());

        double weightInPounds = toPounds(weightInKg);
        double heightInInches = toInches(heightInCm);

        double bmi = weightInPounds / (heightInInches * heightInInches) * 703;

        String result;
        if (bmi < 16) {
            result = "Severely underweight";
        } else if (bmi >= 16 && bmi < 18.5) {
            result = "Underweight";
        } else if (bmi >= 18.5 && bmi < 25) {
            result = "Normal";
        } else if (bmi >= 25 && bmi < 30) {
            result = "Overweight";
        } else {
            result = "Obese";
        }

        Toast.makeText(this, "Your BMI is: " + bmi + "\n" + result, Toast.LENGTH_SHORT).show();
    }

    private void calculateCalories() {
        double weightInPounds = toPounds(Double.parseDouble(weightEditText.getText().toString()));
        double heightInInches = toInches(Double.parseDouble(heightEditText.getText().toString()));
        int ageCategory = ageSpinner.getSelectedItemPosition();
        int age;

        if (ageCategory == 0) {
            age = Integer.parseInt(getResources().getStringArray(R.array.age_below_24)[ageCategory]);
        } else {
            age = Integer.parseInt(getResources().getStringArray(R.array.age_above_24)[ageCategory - 1]);
        }

        double calories;
        if (maleRadioButton.isChecked()) {
            calories = 665 + (6.3 * weightInPounds) + (12.9 * heightInInches) - (6.8 * age);
        } else {
            if (ageCategory == 0) {
                calories = 665 + (4.3 * weightInPounds) + (4.7 * heightInInches) - (4.7 * age);
            } else {
                calories = 455 + (4.3 * weightInPounds) + (4.7 * heightInInches) - (4.7 * age);
            }
        }

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("calories", calories);
        startActivity(intent);
    }


    private double toPounds(double weightInKg) {
        return weightInKg * 2.20462;
    }

    private double toInches(double heightInCm) {
        return heightInCm * 0.393701;
    }
}
