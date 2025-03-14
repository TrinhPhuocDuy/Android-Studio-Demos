package com.example.lab15payment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentNotification extends AppCompatActivity {

TextView txtNotification;

@Override
    protected void OnCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_notification);

        txtNotification = findViewById(R.id.textViewNotify);

        Intent intent = getIntent();
        txtNotification.setText(intent.getStringExtra("result"));
    }
}
