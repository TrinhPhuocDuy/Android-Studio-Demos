package com.example.lab15payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderPayment extends AppCompatActivity {
    Button btnConfirm;
    EditText edtSoluong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConfirm = findViewById(R.id.buttonConfirm);
        edtSoluong = findViewById(R.id.editTextSoluong);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSoluong.getText() == null ||
                        edtSoluong.getText().toString().isEmpty()) {
                    Toast.makeText(OrderPayment.this, "Nhập số lượng muốn mua",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String s = edtSoluong.getText().toString();
                double total = Double.parseDouble(s) * (double) 1000000;
                Intent intent = new Intent(OrderPayment.this, PrintOrder.class);
                intent.putExtra("soluong", edtSoluong.getText().toString());
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });
        }
    }
