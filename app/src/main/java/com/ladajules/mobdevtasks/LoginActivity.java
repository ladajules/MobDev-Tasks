package com.ladajules.mobdevtasks;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

import kotlin.contracts.SimpleEffect;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText etUsername, etPassword;
    private Button btnLogin, btnClear;
    private TextView tvAttempts;
    int attempts = 0;
    final int MAX_ATTEMPTS = 3;
    long LOCKOUT_DURATION = 30000;
    final int MIN_PASSWORD_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the EditText fields
        tilUsername = findViewById(R.id.tilUsername);
        tilPassword = findViewById(R.id.tilPassword);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnClear = findViewById(R.id.btnClear);
        tvAttempts = findViewById(R.id.tvAttempts);

        // Set click listeners for the buttons
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (areFieldsEmpty()) {
                Snackbar.make(btnLogin, "Please fill in all fields!", Snackbar.LENGTH_SHORT).show();
                return;
            }

            checkPasswordLength();

            performLogin();
        });

        btnClear.setOnClickListener(v -> {
            clearFields();
            Toast.makeText(this, "Fields cleared!", Toast.LENGTH_SHORT).show();
        });

    }

    private void performLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.equals("admin") && password.equals("admin")) {
            Intent newIntent = new Intent(this, ProfileActivity.class);
            newIntent.putExtra("username", username);
            newIntent.putExtra("password", password);
            newIntent.putExtra("isFromLogin", true);
            startActivity(newIntent);
            attempts = 0;

        } else {
            tilUsername.requestFocus();
            attempts++;
            int attemptsLeft = MAX_ATTEMPTS - attempts;

            if (attempts >= MAX_ATTEMPTS) {
                clearFields();
                tvAttempts.setText("BOOOOOO Login disabled!");
                Snackbar.make(btnLogin, "Maximum attempts reached!", Toast.LENGTH_SHORT).show();
                lockout();
                return;
            }

            tvAttempts.setText("Attempts left: " + attemptsLeft);
            clearFields();

            Snackbar.make(btnLogin, "Invalid credentials!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean areFieldsEmpty() {
        return etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty();
    }

    private void clearFields() {
        etUsername.setText("");
        etPassword.setText("");
        tilUsername.requestFocus();
    }

    private void lockout() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(System.currentTimeMillis());
        String logMessage = "Last login attempt at " + timestamp;

        disableFields();

        new CountDownTimer(LOCKOUT_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                tvAttempts.setText(logMessage + "\nLogin disabled for " + secondsLeft + " seconds");
            }

            @Override
            public void onFinish() {
                tvAttempts.setText("");
                enableFields();
            }
        }.start();

    }

    private void disableFields() {
        tilUsername.setEnabled(false);
        tilPassword.setEnabled(false);
        btnLogin.setEnabled(false);
        btnClear.setEnabled(false);
        tilUsername.setError("Disabled!");
        tilPassword.setError("Disabled!");
        tilPassword.setEndIconMode(TextInputLayout.END_ICON_NONE);
        btnLogin.setError("Disabled!");
        btnClear.setError("Disabled!");
    }

    private void enableFields() {
        int attempts = 0;
        tilUsername.setEnabled(true);
        tilPassword.setEnabled(true);
        btnLogin.setEnabled(true);
        btnClear.setEnabled(true);
        tilUsername.setError(null);
        tilPassword.setError(null);
        tilPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        btnLogin.setError(null);
        btnClear.setError(null);
    }

    private void checkPasswordLength() {
        String password = etPassword.getText().toString();
        if (password.equals("admin")) return;

        if (password.length() < MIN_PASSWORD_LENGTH) {
            tilPassword.setError("Password must be at least " + MIN_PASSWORD_LENGTH + " characters long");
        } else {
            tilPassword.setError(null);
        }
    }
}