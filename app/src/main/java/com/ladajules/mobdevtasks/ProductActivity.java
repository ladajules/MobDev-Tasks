package com.ladajules.mobdevtasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    private String username;
    private String password;

    private final int[] plusButtonIds = {R.id.btnPlus1, R.id.btnPlus2, R.id.btnPlus3, R.id.btnPlus4, R.id.btnPlus5, R.id.btnPlus6};
    private final int[] minusButtonIds = {R.id.btnMinus1, R.id.btnMinus2, R.id.btnMinus3, R.id.btnMinus4, R.id.btnMinus5, R.id.btnMinus6};
    private final int[] numberViewIds = {R.id.tvNumber1, R.id.tvNumber2, R.id.tvNumber3, R.id.tvNumber4, R.id.tvNumber5, R.id.tvNumber6};
    private final int[] productNameViewIds = {R.id.tvProductName1, R.id.tvProductName2, R.id.tvProductName3, R.id.tvProductName4, R.id.tvProductName5, R.id.tvProductName6};
    private final int[] productPriceViewIds = {R.id.tvProductPrice1, R.id.tvProductPrice2, R.id.tvProductPrice3, R.id.tvProductPrice4, R.id.tvProductPrice5, R.id.tvProductPrice6};
    private final int[] imageResourceIds = {R.drawable.fitbit, R.drawable.candle, R.drawable.stanley, R.drawable.sony, R.drawable.sanitizer, R.drawable.iphone};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

        ArrayList<Product> existingCart = (ArrayList<Product>) intent.getSerializableExtra("purchasedProducts");
        if (existingCart == null) {
            existingCart = new ArrayList<>();
        }

        for (int i = 0; i < plusButtonIds.length; i++) {
            setupProductCard(plusButtonIds[i], minusButtonIds[i], numberViewIds[i]);

            TextView tvProductName = findViewById(productNameViewIds[i]);
            TextView tvNumber = findViewById(numberViewIds[i]);
            String currentProductName = tvProductName.getText().toString();

            for (Product p : existingCart) {
                if (p.getName().equals(currentProductName)) {
                    tvNumber.setText(String.valueOf(p.getQuantity()));
                    break;
                }
            }
        }

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(v -> {
            ArrayList<Product> updatedCart = new ArrayList<>();
            double updatedTotalPrice = 0.0;

            for (int i = 0; i < numberViewIds.length; i++) {
                TextView tvNumber = findViewById(numberViewIds[i]);
                int quantity = Integer.parseInt(tvNumber.getText().toString());

                if (quantity > 0) {
                    TextView tvProductName = findViewById(productNameViewIds[i]);
                    TextView tvProductPrice = findViewById(productPriceViewIds[i]);
                    String name = tvProductName.getText().toString();
                    double price = Double.parseDouble(tvProductPrice.getText().toString().replaceAll("[₱$]", ""));
                    int imageId = imageResourceIds[i];

                    updatedCart.add(new Product(name, price, quantity, imageId));
                    updatedTotalPrice += price * quantity;
                }
            }

            Intent profileIntent = new Intent(ProductActivity.this, ProfileActivity.class);
            profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            profileIntent.putExtra("username", username);
            profileIntent.putExtra("password", password);
            profileIntent.putExtra("purchasedProducts", updatedCart);
            profileIntent.putExtra("totalPrice", updatedTotalPrice);
            startActivity(profileIntent);
        });
    }

    private void setupProductCard(int plusButtonId, int minusButtonId, int numberViewId) {
        Button btnPlus = findViewById(plusButtonId);
        Button btnMinus = findViewById(minusButtonId);
        TextView tvNumber = findViewById(numberViewId);

        btnPlus.setOnClickListener(v -> {
            int quantity = Integer.parseInt(tvNumber.getText().toString());
            quantity++;
            tvNumber.setText(String.valueOf(quantity));
        });

        btnMinus.setOnClickListener(v -> {
            int quantity = Integer.parseInt(tvNumber.getText().toString());
            if (quantity > 0) {
                quantity--;
                tvNumber.setText(String.valueOf(quantity));
            }
        });
    }
}
