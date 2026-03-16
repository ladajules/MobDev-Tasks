package com.ladajules.mobdevtasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private String username;
    private String password;
    private List<Product> productsList;
    private RecyclerView rvProducts;

    private final ActivityResultLauncher<Intent> detailLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Product updatedProduct = result.getData().getParcelableExtra("UPDATED_PRODUCT");

                    if (updatedProduct != null) {
                        for (int i = 0; i < productsList.size(); i++) {
                            if (productsList.get(i).getName().equals(updatedProduct.getName())) {
                                productsList.set(i, updatedProduct);
                                if (rvProducts.getAdapter() != null) {
                                    rvProducts.getAdapter().notifyItemChanged(i);
                                }
                                break;
                            }
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));

        productsList = getProducts();

        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("username");
            password = intent.getStringExtra("password");

            ArrayList<Product> existingCart = intent.getParcelableArrayListExtra("purchasedProducts");
            if (existingCart != null && productsList != null) {
                for (Product cartItem : existingCart) {
                    for (Product product : productsList) {
                        if (product.getName().equals(cartItem.getName())) {
                            product.setQuantity(cartItem.getQuantity());
                            break;
                        }
                    }
                }
            }
        }

        ProductAdapter adapter = new ProductAdapter(productsList, product -> {
            Intent productDetailIntent = new Intent(ProductActivity.this, ProductDetailActivity.class);
            productDetailIntent.putExtra("PRODUCT", (android.os.Parcelable) product);
            detailLauncher.launch(productDetailIntent);
        });
        rvProducts.setAdapter(adapter);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        Button btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> {
            ArrayList<Product> updatedCart = new ArrayList<>();
            double updatedTotalPrice = 0.0;

            for (Product p : productsList) {
                if (p.getQuantity() > 0) {
                    updatedCart.add(p);
                    updatedTotalPrice += p.getPrice() * p.getQuantity();
                }
            }

            Intent profileIntent = new Intent(ProductActivity.this, ProfileActivity.class);
            profileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            profileIntent.putExtra("username", username);
            profileIntent.putExtra("password", password);

            profileIntent.putParcelableArrayListExtra("purchasedProducts", updatedCart);
            profileIntent.putExtra("totalPrice", updatedTotalPrice);
            startActivity(profileIntent);
        });
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Fitbit", 199.99, 0, "Iz a cool fitness watch to track your fitness", R.drawable.fitbit));
        products.add(new Product("Candle", 14.99, 0, "So you could see in the dark", R.drawable.candle));
        products.add(new Product("Stanley", 49.99, 0, "You can hide beer in here to go inside the school and get drunk :)",R.drawable.stanley));
        products.add(new Product("Sony", 299.99, 0, "You can listen to music with this", R.drawable.sony));
        products.add(new Product("Sanitizer", 9.99, 0, "To keep you safe from germs", R.drawable.sanitizer));
        products.add(new Product("Iphone", 999.99, 0, "The not so best phone you could ever have", R.drawable.iphone));
        return products;
    }
}
