package com.example.array;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;

    ListView lvTraiCay;
    ArrayList<TraiCay> arrayTraiCay;
    TraiCayAdapter adapter;
    Button btnAdd, btnEdit, btnDelete;
    int selectedIndex = -1;
    ImageView selectedImageView;
    Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        // Initialize adapter and set it to ListView
        adapter = new TraiCayAdapter(this, R.layout.trai_cay_layout, arrayTraiCay);
        lvTraiCay.setAdapter(adapter);

        // Handle item click to track the selected item
        lvTraiCay.setOnItemClickListener((parent, view, position, id) -> {
            if (selectedIndex == position) {
                // If the same item is tapped again, deselect it
                selectedIndex = -1;
            } else {
                // Set the selected index to the new item
                selectedIndex = position;
            }

            adapter.setSelectedIndex(selectedIndex); // Notify the adapter of the new selection
            adapter.notifyDataSetChanged(); // Update the ListView
            updateButtonStates(); // Update the button states after selection change
        });

        // Handle Add button click
        btnAdd.setOnClickListener(v -> openDialog(null, "Thêm", (name, description, bitmapImage) -> {
            // Check if a new image is selected (bitmapImage != null), and set the image accordingly
            if (bitmapImage != null) {
                // If a custom image was selected, create a new TraiCay
                arrayTraiCay.add(new TraiCay(name, description, bitmapImage));
            } else {
                // If no custom image was selected, use the default image resource
                arrayTraiCay.add(new TraiCay(name, description, R.drawable.trai_cay));
            }
            adapter.notifyDataSetChanged(); // Notify adapter of changes
            updateButtonStates(); // Update the button states after adding a new item

            selectedBitmap = null; // Reset selectedBitmap after adding
        }));


        // Handle Edit button click
        btnEdit.setOnClickListener(v -> {
            if (selectedIndex != -1) {
                TraiCay selectedTraiCay = arrayTraiCay.get(selectedIndex);
                openDialog(selectedTraiCay, "Sửa", (name, description, bitmapImage) -> {
                    selectedTraiCay.setTen(name);
                    selectedTraiCay.setMota(description);

                    // If a new Bitmap is selected, update the image.
                    // Otherwise, keep the existing image of the selected item.
                    if (bitmapImage != null) {
                        selectedTraiCay.setBitmapImage(bitmapImage);
                    }
                    // Do nothing if bitmapImage is null, retaining the current image (Bitmap or Drawable)

                    adapter.notifyDataSetChanged(); // Notify adapter of changes
                    updateButtonStates(); // Update the button states after editing

                    selectedBitmap = null; // Reset selectedBitmap after editing
                });
            }
        });


        // Handle Delete button click
        btnDelete.setOnClickListener(v -> {
            if (selectedIndex != -1) {
                showDeleteConfirmationDialog(); // Show confirmation dialog before deleting
            }
        });

        updateButtonStates(); // Initialize button states when the activity is created
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete TraiCay");
        builder.setMessage("Are you sure you want to delete this item?");

        // Set up the "Yes" button
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // Proceed with deletion
            arrayTraiCay.remove(selectedIndex); // Remove the selected item
            adapter.notifyDataSetChanged(); // Notify adapter of changes
            selectedIndex = -1; // Reset selectedIndex
            updateButtonStates(); // Update the button states after deletion
            dialog.dismiss(); // Close the dialog
        });

        // Set up the "No" button
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.cancel(); // Close the dialog without deleting
        });

        // Show the dialog
        builder.show();
    }

    // Function to update the state of buttons based on selection
    private void updateButtonStates() {
        if (selectedIndex == -1) {
            // If no item is selected, disable Edit and Delete, enable Add
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
            btnAdd.setEnabled(true);
        } else {
            // If an item is selected, enable Edit and Delete, disable Add
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
            btnAdd.setEnabled(false);
        }
    }


    private void AnhXa() {
        lvTraiCay = findViewById(R.id.listviewTraiCay);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        arrayTraiCay = new ArrayList<>();
        arrayTraiCay.add(new TraiCay("Chuối cau", "Chuối này ngon lắm", R.drawable.chuoi_cau));
        arrayTraiCay.add(new TraiCay("Xoài chín", "Xoài ngọt", R.drawable.xoai_chin));
        arrayTraiCay.add(new TraiCay("Dưa hấu", "Dưa hấu mát lành", R.drawable.dua_hau));
        arrayTraiCay.add(new TraiCay("Táo đỏ", "Táo giòn ngon", R.drawable.tao_do));
    }

    // Function to open dialog for adding or editing TraiCay
    private void openDialog(TraiCay traiCay, String action, OnTraiCayUpdateListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(action + " Trái Cây");

        // Inflate a custom layout for the dialog
        View viewInflated = getLayoutInflater().inflate(R.layout.dialog_add_edit, null);
        EditText inputName = viewInflated.findViewById(R.id.inputName);
        EditText inputDescription = viewInflated.findViewById(R.id.inputDescription);
        selectedImageView = viewInflated.findViewById(R.id.imgSelected);
        Button btnSelectImage = viewInflated.findViewById(R.id.btnSelectImage);

        // If editing, populate the fields with existing data
        if (traiCay != null) {
            inputName.setText(traiCay.getTen());
            inputDescription.setText(traiCay.getMota());
            if (traiCay.getBitmapImage() != null) {
                selectedImageView.setImageBitmap(traiCay.getBitmapImage()); // Show custom image
            } else {
                selectedImageView.setImageResource(traiCay.getHinh()); // Show default drawable
            }
        }

        btnSelectImage.setOnClickListener(v -> {
            // Open the gallery to pick an image
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        builder.setView(viewInflated);

        // Set up dialog buttons
        builder.setPositiveButton("OK", null); // Set null initially to prevent automatic closing

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Override the positive button to perform validation
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String name = inputName.getText().toString().trim();
            String description = inputDescription.getText().toString().trim();

            // Validate that both name and description are filled
            if (name.isEmpty()) {
                inputName.setError("Name is required");
            } else if (description.isEmpty()) {
                inputDescription.setError("Description is required");
            } else {
                // If validation passes, pass the data back
                if (selectedBitmap != null) {
                    listener.onUpdate(name, description, selectedBitmap); // Pass the Bitmap
                } else {
                    // If no custom image was selected, keep the drawable resource
                    if (traiCay != null && traiCay.getBitmapImage() == null) {
                        listener.onUpdate(name, description, null); // No new image, keep existing
                    } else {
                        // Use a default image if no custom image was selected
                        listener.onUpdate(name, description, null);
                    }
                }
                dialog.dismiss(); // Close the dialog after a successful update
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                selectedImageView.setImageBitmap(selectedBitmap); // Show the selected image in the dialog
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    interface OnTraiCayUpdateListener {
        void onUpdate(String name, String description, Bitmap image);
    }

}
