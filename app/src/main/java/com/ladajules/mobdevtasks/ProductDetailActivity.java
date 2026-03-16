package com.ladajules.mobdevtasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {
    private Product product;
    private int currentQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product = getIntent().getParcelableExtra("PRODUCT");

        if (product != null) {
            currentQty = product.getQuantity();
            initializeViews();
        }

        ImageButton btnBack = findViewById(R.id.btnBackDetail);
        btnBack.setOnClickListener(v -> finish());
    }

    private void initializeViews() {
        ImageView ivImage = findViewById(R.id.ivDetailImage);
        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvPrice = findViewById(R.id.tvDetailPrice);
        TextView tvDescription = findViewById(R.id.tvDetailDescription);
        TextView tvQty = findViewById(R.id.tvDetailQty);

        Button btnPlus = findViewById(R.id.btnDetailPlus);
        Button btnMinus = findViewById(R.id.btnDetailMinus);
        Button btnAddToCart = findViewById(R.id.btnConfirmAddToCart);

        ivImage.setImageResource(product.getImageId());
        tvName.setText(product.getName());
        tvPrice.setText(String.format(Locale.getDefault(), "₱%.2f", product.getPrice()));
        tvDescription.setText(product.getDescription());
        tvQty.setText(String.valueOf(currentQty));

        btnPlus.setOnClickListener(v -> {
            currentQty++;
            tvQty.setText(String.valueOf(currentQty));
        });

        btnMinus.setOnClickListener(v -> {
            if (currentQty > 0) {
                currentQty--;
                tvQty.setText(String.valueOf(currentQty));
            }
        });

        btnAddToCart.setOnClickListener(v -> {
            product.setQuantity(currentQty);
            
            Intent resultIntent = new Intent();
            resultIntent.putExtra("UPDATED_PRODUCT", (android.os.Parcelable) product);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
