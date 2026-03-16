package com.ladajules.mobdevtasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {
    private Button btnLogout;
    private RecyclerView rvPurchasedProducts;
    private TextView tvTotalPrice, tvUsername, tvPassword;
    private ViewFlipper vfCartContent;
    private LinearLayout llTotal;
    private ShapeableImageView ivProfileImg;
    private static final int EMPTY_CART_VIEW_INDEX = 0;
    private static final int PRODUCT_LIST_VIEW_INDEX = 1;
    private ImageButton btnShop;
    private ArrayList<Product> currentCart = new ArrayList<>();
    private double currentTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogout = findViewById(R.id.btnLogout);
        rvPurchasedProducts = findViewById(R.id.rvPurchasedProducts);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        vfCartContent = findViewById(R.id.vfCartContent);
        llTotal = findViewById(R.id.llTotal);
        tvUsername = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);
        ivProfileImg = findViewById(R.id.ivProfileImg);
        btnShop = findViewById(R.id.btnShop);

        Intent intent = getIntent();
        if (intent != null) {
            boolean isFromLogin = intent.getBooleanExtra("isFromLogin", false);
            if (isFromLogin) {
                Snackbar.make(findViewById(android.R.id.content), "Login successful!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(0xFF008000)
                        .show();
            }

            tvUsername.setText(intent.getStringExtra("username"));
            tvPassword.setText(intent.getStringExtra("password"));
            ivProfileImg.setImageResource(R.drawable.default_avatar);

            if (intent.hasExtra("purchasedProducts")) {
                currentCart = intent.getParcelableArrayListExtra("purchasedProducts");
                currentTotal = intent.getDoubleExtra("totalPrice", 0.0);
            }

            if (currentCart != null && !currentCart.isEmpty()) {
                vfCartContent.setDisplayedChild(PRODUCT_LIST_VIEW_INDEX);
                llTotal.setVisibility(View.VISIBLE);

                rvPurchasedProducts.setLayoutManager(new LinearLayoutManager(this));
                PurchasedProductAdapter adapter = new PurchasedProductAdapter(currentCart);
                rvPurchasedProducts.setAdapter(adapter);

                tvTotalPrice.setText(String.format(Locale.getDefault(), "₱%.2f", currentTotal));
            } else {
                currentCart = new ArrayList<>();
                vfCartContent.setDisplayedChild(EMPTY_CART_VIEW_INDEX);
                llTotal.setVisibility(View.GONE);
            }
        }

        btnShop.setOnClickListener(v -> {
            Intent shopIntent = new Intent(this, ProductActivity.class);
            shopIntent.putExtra("username", tvUsername.getText().toString());
            shopIntent.putExtra("password", tvPassword.getText().toString());

            shopIntent.putParcelableArrayListExtra("purchasedProducts", currentCart);
            shopIntent.putExtra("totalPrice", currentTotal);
            startActivity(shopIntent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent newIntent = new Intent(this, LoginActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(newIntent);
            finish();
        });
    }
}
