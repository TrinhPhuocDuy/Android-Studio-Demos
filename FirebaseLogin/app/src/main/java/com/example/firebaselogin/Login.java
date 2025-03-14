package com.example.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    TextInputEditText txtedemail, txtedpassword;
    Button loginbtn, GoogleBtn, PhoneOTPbtn;
    ProgressBar progressBar;
    TextView toSignup;
    FirebaseAuth mAuth;

    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 20;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // Current user is signed in when app starts
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Set up the insets for Firebase Authenticate
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Set up the insets for the layout
        txtedemail=findViewById(R.id.email);
        txtedpassword =findViewById(R.id.password);
        loginbtn=findViewById(R.id.loginbtn);
        progressBar=findViewById(R.id.progressBar2);
        toSignup=findViewById(R.id.toSignunbtn);
        GoogleBtn = findViewById(R.id.GoogleBtn);

        //Google Login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });

        toSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    String email = String.valueOf(txtedemail.getText());
                    String password = String.valueOf(txtedpassword.getText());
                    if (email.isEmpty() || password.isEmpty()) {
                        if (TextUtils.isEmpty(email)) {
                            Toast.makeText(Login.this, "Email is required", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(password)) {
                            Toast.makeText(Login.this, "Password is required", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                                        // Sign in success, update UI with the signed-in user into main activity
//                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                        startActivity(intent);
//                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
    }

    private void googleSignIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task =  GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(com.google.android.gms.common.api.ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("uid",user.getUid());
                    map.put("email",user.getEmail());
                    map.put("name",user.getDisplayName());
                    map.put("profile",user.getPhotoUrl().toString());

                    database.getReference().child("users").child(user.getUid()).setValue(map);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Login.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}