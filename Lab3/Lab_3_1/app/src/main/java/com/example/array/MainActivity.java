package com.example.array;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arraycourse;
    Button addBtn, editBtn, delBtn;
    EditText textField;
    CustomAdapter adapter;
    int selectedItemIndex = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.ListViewLab3_1);
        addBtn = findViewById(R.id.addBtn);
        editBtn = findViewById(R.id.editBtn);
        delBtn = findViewById(R.id.delBtn);
        textField = findViewById(R.id.textField);

        arraycourse = new ArrayList<>();
        arraycourse.add("Android");
        arraycourse.add("PHP");
        arraycourse.add("iOS");
        arraycourse.add("Unity");
        arraycourse.add("ASP.net");

        adapter = new CustomAdapter(MainActivity.this, arraycourse);
        listView.setAdapter(adapter);

        // Initially disable edit and delete buttons
        editBtn.setEnabled(false);
        delBtn.setEnabled(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedItemIndex == position) {
                    // If the same item is tapped again, deselect it
                    selectedItemIndex = -1;
                    textField.setText("");  // Clear the text field

                    // Reset the adapter to reflect no selection
                    adapter.setSelectedPosition(-1);

                    // Disable edit and delete buttons when no item is selected
                    editBtn.setEnabled(false);
                    delBtn.setEnabled(false);

                    // Enable the add button
                    addBtn.setEnabled(true);
                } else {
                    // Set the selected index to the new item
                    selectedItemIndex = position;
                    String getItem = arraycourse.get(position);
                    textField.setText(getItem);

                    // Update the adapter to highlight the selected item
                    adapter.setSelectedPosition(position);

                    // Enable edit and delete buttons when an item is selected
                    editBtn.setEnabled(true);
                    delBtn.setEnabled(true);

                    // Disable the add button when an item is selected
                    addBtn.setEnabled(false);
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newContent = textField.getText().toString();

                if (newContent.isEmpty()) {
                    return;
                }

                if (arraycourse.contains(newContent)) {
                    Toast.makeText(MainActivity.this, "This content is duplicated!", Toast.LENGTH_LONG).show();
                    return;
                }

                arraycourse.add(newContent);
                adapter.notifyDataSetChanged();
                textField.setText("");
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newContent = textField.getText().toString();
                if (!newContent.isEmpty() && selectedItemIndex != -1) {
                    arraycourse.set(selectedItemIndex, newContent);
                    adapter.notifyDataSetChanged();
                    textField.setText("");

                    // Reset selection and disable buttons after editing
                    selectedItemIndex = -1;
                    adapter.setSelectedPosition(-1);
                    editBtn.setEnabled(false);
                    delBtn.setEnabled(false);

                    addBtn.setEnabled(true);
                }
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItemIndex != -1) {
                    arraycourse.remove(selectedItemIndex);
                    adapter.notifyDataSetChanged();
                    textField.setText("");

                    // Reset selection and disable buttons after deletion
                    selectedItemIndex = -1;
                    adapter.setSelectedPosition(-1);
                    editBtn.setEnabled(false);
                    delBtn.setEnabled(false);

                    addBtn.setEnabled(true);
                }
            }
        });
    }
}

