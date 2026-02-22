package com.ladajules.mobdevtasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class PurchasedProductAdapter extends RecyclerView.Adapter<PurchasedProductAdapter.ViewHolder> {

    private final ArrayList<Product> purchasedProducts;

    public PurchasedProductAdapter(ArrayList<Product> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_purchased_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = purchasedProducts.get(position);
        holder.ivProductImage.setImageResource(product.imageId);
        holder.tvProductName.setText(product.name);
        holder.tvProductPrice.setText(String.format(Locale.getDefault(), "₱%.2f", product.price));
        holder.tvProductQuantity.setText(String.format(Locale.getDefault(), "Quantity: %d", product.quantity));
    }

    @Override
    public int getItemCount() {
        return purchasedProducts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName;
        TextView tvProductPrice;
        TextView tvProductQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
        }
    }
}
