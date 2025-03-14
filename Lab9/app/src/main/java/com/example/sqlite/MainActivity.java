package com.example.sqlite;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        database = new Database(this, "ghichu.sqLite", null, 1);

        database.QueryData("Create table if not exist CongViec(id Integer primary key autoincrement," +
                " tenCV nvarchar(200))");

        //Insert data
        //database.QueryData("Insert into CongViec(tenCV) values('Lam bai tap')");
        //database.QueryData("Insert into CongViec(tenCV) values('Design App)");

        Cursor dataCongViec = database.GetData("Select * from CongViec");
        while (dataCongViec.moveToNext()){
            String ten = dataCongViec.getString(1);
            Toast.makeText(this, ten, Toast.LENGTH_SHORT).show();
        }

    }
}