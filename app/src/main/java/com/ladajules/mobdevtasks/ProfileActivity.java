package com.ladajules.mobdevtasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class ProfileActivity extends AppCompatActivity {
    private Button btnLogout;
    private TextView tvProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        btnLogout = findViewById(R.id.btnLogout);
        tvProfile = findViewById(R.id.tvProfile);

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Login successful!", Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(0xFF008000);
        snackbar.show();

        btnLogout.setOnClickListener(v -> {
            Intent newIntent = new Intent(this, LoginActivity.class);
            startActivity(newIntent);
        });

        if (intent != null) {
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");

            tvProfile.setText("Username: " + username + "\nPassword: " + password);
        }
    }
}