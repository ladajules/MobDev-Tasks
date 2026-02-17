package com.ladajules.mobdevtasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

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

        btnLogout.setOnClickListener(v -> {
            Intent newIntent = new Intent(this, LoginActivity.class);
            startActivity(newIntent);
        });

        if (intent != null) {
            ArrayList<ProductActivity.Product> purchasedProducts = (ArrayList<ProductActivity.Product>) intent.getSerializableExtra("purchasedProducts");
            double totalPrice = intent.getDoubleExtra("totalPrice", 0.0);

            if (purchasedProducts != null && !purchasedProducts.isEmpty()) {
                StringBuilder purchaseDetails = new StringBuilder("Purchased Products:\n\n");
                for (ProductActivity.Product product : purchasedProducts) {
                    purchaseDetails.append(String.format(Locale.getDefault(),
                            "- %s (x%d) - ₱%.2f each\n",
                            product.name, product.quantity, product.price));
                }
                purchaseDetails.append(String.format(Locale.getDefault(),
                        "\nTotal Price: ₱%.2f", totalPrice));

                tvProfile.setText(purchaseDetails.toString());
            } else {
                tvProfile.setText("No products purchased.");
            }
        }
    }
}
