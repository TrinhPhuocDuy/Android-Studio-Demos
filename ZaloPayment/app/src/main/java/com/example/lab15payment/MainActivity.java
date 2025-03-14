package com.example.lab15payment;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lab15payment.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnConfirm;
    EditText edtSoluong;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConfirm = findViewById(R.id.buttonConfirm);
        edtSoluong = findViewById(R.id.editTextSoluong);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSoluong.getText() == null || edtSoluong.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nhập số lượng muốn mua", Toast.LENGTH_SHORT).show();
                    return;
                }
                String s = edtSoluong.getText().toString();
                double total = Double.parseDouble(s) * (double)1000000;
                Intent intent = new Intent(MainActivity.this, PrintOrder.class);
                intent.putExtra("soluong",edtSoluong.getText().toString());
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });
    }

}