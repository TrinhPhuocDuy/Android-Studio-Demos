package com.example.lab10.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.lab10.AppExecutors;
import com.example.lab10.R;
import com.example.lab10.constants.Constants;
import com.example.lab10.db.AppDatabase;
import com.example.lab10.model.Person;

public class EditPersonActivity extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private Button btnSave;
    private int mPersonId;

    private Intent intent;
    private AppDatabase mDb;

    @Override
    protected void onCreate(final Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_person_edit);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Đảm bảo ActionBar không null trước khi sử dụng
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            Log.e("EditPersonActivity", "ActionBar is null!");
        }

        initViews();
        mDb = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app-database").build();

        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            btnSave.setText("Update");
            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, -1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Person person = mDb.personDAO().loadPersonById(mPersonId);
                    runOnUiThread(() -> populateUI(person));
                }
            });
        }
    }

    private void populateUI(Person person) {
        if (person == null) return;
        etFirstName.setText(person.getFirstName());
        etLastName.setText(person.getLastName());
    }

    private void initViews() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> onSaveButtonClick());
    }

    public void onSaveButtonClick() {
        final Person person = new Person(
                etFirstName.getText().toString(),
                etLastName.getText().toString());

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (!intent.hasExtra(Constants.UPDATE_Person_Id)) {
                mDb.personDAO().insert(person);
            } else {
                person.setUid(mPersonId);
                mDb.personDAO().update(person);
            }
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
