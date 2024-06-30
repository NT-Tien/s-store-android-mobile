package com.example.s_store.ui.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.s_store.R;
import com.example.s_store.common.models.ProductModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    // Define interface for handling click events
    public interface OnProductClickListener {
        void onProductClick(ProductModel product, int position);
    }
    private List<ProductModel> products;
    private OnProductClickListener onProductClickListener;
    public ProductAdapter(List<ProductModel> products, OnProductClickListener onProductClickListener) {
        this.products = products;
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel product = products.get(position);
        holder.name.setText(product.getName());
        holder.description.setText(product.getDescription());

        if (!product.getProductOpts().isEmpty()) {
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            String formattedPrice = numberFormat.format(product.getProductOpts().get(0).getPrice()) + " VND";
            holder.price.setText(formattedPrice);
        } else {
            holder.price.setText("N/A");
        }

        String baseUrl = "https://s-api.caucalamdev.io.vn/file/image/";
        Glide.with(holder.itemView.getContext())
                .load(baseUrl + product.getImage())
                .into(holder.image);

        holder.itemView.setOnClickListener(view -> {
            if (onProductClickListener != null) {
                onProductClickListener.onProductClick(product, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price, status;
        public ImageView image;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            description = itemView.findViewById(R.id.productDescription);
            price = itemView.findViewById(R.id.productPrice);
            status = itemView.findViewById(R.id.productStatus);
            image = itemView.findViewById(R.id.productImage);
        }
    }
}