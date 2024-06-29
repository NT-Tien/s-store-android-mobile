package com.example.s_store.products.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.s_store.R;
import com.example.s_store.products.model.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products;
    private Context context;


    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.name.setText(product.getName());
        holder.description.setText(product.getDescription());

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        String formattedPrice = numberFormat.format(product.getProductOpts().get(0).getPrice()) + " VND";
        holder.price.setText(formattedPrice);
        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .into(holder.image);

        // Set OnClickListener for productName to open ProductDetailActivity
//        holder.name.setOnClickListener(v -> {
//            Intent intent = new Intent(context, ProductDetailActivity.class);
//            intent.putExtra("name", product.getName());
//            intent.putExtra("description", product.getDescription());
//            intent.putExtra("price", product.getPrice());
//            intent.putExtra("status", product.getStatus());
//            intent.putExtra("imageUrl", product.getImageUrl());
//            context.startActivity(intent);
//        });
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

