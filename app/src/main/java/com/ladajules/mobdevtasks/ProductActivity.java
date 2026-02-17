package com.ladajules.mobdevtasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    static class Product implements Serializable {
        String name;
        double price;
        int quantity;

        public Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
    }

    private final int[] plusButtonIds = {R.id.btnPlus1, R.id.btnPlus2, R.id.btnPlus3, R.id.btnPlus4, R.id.btnPlus5, R.id.btnPlus6};
    private final int[] minusButtonIds = {R.id.btnMinus1, R.id.btnMinus2, R.id.btnMinus3, R.id.btnMinus4, R.id.btnMinus5, R.id.btnMinus6};
    private final int[] numberViewIds = {R.id.tvNumber1, R.id.tvNumber2, R.id.tvNumber3, R.id.tvNumber4, R.id.tvNumber5, R.id.tvNumber6};
    private final int[] productNameViewIds = {R.id.tvProductName1, R.id.tvProductName2, R.id.tvProductName3, R.id.tvProductName4, R.id.tvProductName5, R.id.tvProductName6};
    private final int[] productPriceViewIds = {R.id.tvProductPrice1, R.id.tvProductPrice2, R.id.tvProductPrice3, R.id.tvProductPrice4, R.id.tvProductPrice5, R.id.tvProductPrice6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Snackbar.make(findViewById(android.R.id.content), "Login successful!", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(0xFF008000)
                .show();

        for (int i = 0; i < plusButtonIds.length; i++) {
            setupProductCard(plusButtonIds[i], minusButtonIds[i], numberViewIds[i]);
        }

        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(v -> {
            ArrayList<Product> purchasedProducts = new ArrayList<>();
            double totalPrice = 0;

            for (int i = 0; i < numberViewIds.length; i++) {
                TextView tvNumber = findViewById(numberViewIds[i]);
                int quantity = Integer.parseInt(tvNumber.getText().toString());

                if (quantity > 0) {
                    TextView tvProductName = findViewById(productNameViewIds[i]);
                    TextView tvProductPrice = findViewById(productPriceViewIds[i]);
                    String name = tvProductName.getText().toString();

                    double price = Double.parseDouble(tvProductPrice.getText().toString().replaceAll("[₱$]", ""));
                    purchasedProducts.add(new Product(name, price, quantity));
                    totalPrice += price * quantity;
                }
            }

            Intent profileIntent = new Intent(ProductActivity.this, ProfileActivity.class);
            profileIntent.putExtra("purchasedProducts", purchasedProducts);
            profileIntent.putExtra("totalPrice", totalPrice);
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
