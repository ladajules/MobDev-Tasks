package com.ladajules.mobdevtasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    private final List<Product> productsList;
    private final OnProductClickListener listener;

    public ProductAdapter(List<Product> productsList, OnProductClickListener listener) {
        this.productsList = productsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = productsList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format(Locale.getDefault(), "₱%.2f", product.getPrice()));
        holder.productQty.setText(String.valueOf(product.getQuantity()));
        holder.productDescription.setText(product.getDescription());
        holder.productImage.setImageResource(product.getImageId());

        holder.itemView.setOnClickListener(v -> listener.onProductClick(product));
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productQty, productDescription;
        ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tvProductName);
            productPrice = itemView.findViewById(R.id.tvProductPrice);
            productQty = itemView.findViewById(R.id.tvNumber);
            productDescription = itemView.findViewById(R.id.tvProductDescription);
            productImage = itemView.findViewById(R.id.ivAvatar);
        }
    }
}
