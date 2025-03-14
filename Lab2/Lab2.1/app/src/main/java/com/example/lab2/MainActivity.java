package com.example.lab2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText minInput, maxInput;
    private TextView resultText;
    private Button generateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ques1);

        // Ánh xạ các View
        minInput = findViewById(R.id.minInput);
        maxInput = findViewById(R.id.maxInput);
        resultText = findViewById(R.id.resultText);
        generateButton = findViewById(R.id.generateButton);

        // Thiết lập sự kiện nhấn nút Generate
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRandomNumber();
            }
        });
    }

    private void generateRandomNumber() {
        String minStr = minInput.getText().toString();
        String maxStr = maxInput.getText().toString();

        // Kiểm tra nếu các trường input không bị trống
        if (!minStr.isEmpty() && !maxStr.isEmpty()) {
            int min = Integer.parseInt(minStr);
            int max = Integer.parseInt(maxStr);

            // Đảm bảo min nhỏ hơn max
            if (min < max) {
                Random random = new Random();
                int randomNumber = random.nextInt((max - min) + 1) + min; // Random số từ min đến max
                resultText.setText("Result: " + randomNumber);
            } else {
                resultText.setText("Error: Min must be less than Max!");
            }
        } else {
            resultText.setText("Error: Please enter both Min and Max!");
        }
    }
}
