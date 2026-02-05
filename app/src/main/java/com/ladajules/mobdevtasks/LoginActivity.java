package com.ladajules.mobdevtasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin, btnClear;
    TextView tvAttempts;
    AtomicInteger attempts = new AtomicInteger(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the EditText fields
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnClear = findViewById(R.id.btnClear);
        tvAttempts = findViewById(R.id.tvAttempts);

        // Set click listeners for the buttons
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (username.isEmpty()) {
                etUsername.setError("Field required!");
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Field required!");
                return;
            }

            if (username.equals("admin") && password.equals("admin")) {
                Intent newIntent = new Intent(this, HomeActivity.class);
                startActivity(newIntent);

                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            } else {
                etUsername.requestFocus();
                attempts.getAndDecrement();

                if (attempts.get() == 0) {
                    clearFields();
                    tvAttempts.setText("BOOOOOO Login disabled!");
                    Toast.makeText(this, "Maximum attempts reached!", Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(false);
                    etUsername.setEnabled(false);
                    etPassword.setEnabled(false);
                    etUsername.setError("Disabled!");
                    etPassword.setError("Disabled!");
                    return;
                }

                tvAttempts.setText("Attempts left: " + attempts);
                clearFields();

                Toast.makeText(this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
            }

        });

        btnClear.setOnClickListener(v -> {
            clearFields();
            Toast.makeText(this, "Fields cleared!", Toast.LENGTH_SHORT).show();
        });

    }

    private void clearFields() {
        etUsername.setText("");
        etPassword.setText("");
        etUsername.requestFocus();
    }
}