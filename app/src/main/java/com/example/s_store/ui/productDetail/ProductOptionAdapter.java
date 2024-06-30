package com.example.s_store.ui.productDetail;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.s_store.R;
import com.example.s_store.common.models.ProductOptionalModel;

import java.util.ArrayList;
import java.util.List;

public class ProductOptionAdapter extends RecyclerView.Adapter<ProductOptionAdapter.ProductOptionViewHolder> {
    private List<ProductOptionalModel> productOpts;

    private SparseBooleanArray selectedItems; // To keep track of selected items

    public ProductOptionAdapter(List<ProductOptionalModel> productOpts) {
        this.productOpts = productOpts;
        selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public ProductOptionAdapter.ProductOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_option, parent, false);
        return new ProductOptionAdapter.ProductOptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOptionViewHolder holder, int position) {
        ProductOptionalModel productOpt = productOpts.get(position);
        holder.name.setText(productOpt.getName());
        holder.price.setText(String.valueOf(productOpt.getPrice())); // Ensure price is converted to String
        holder.quantity.setText(String.valueOf(productOpt.getQuantity())); // Ensure quantity is converted to String

        String imageUrl = holder.itemView.getContext().getString(R.string.image_base_url) + productOpt.getImage();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.image);

        holder.checkboxOpt.setChecked(selectedItems.get(position, false));
        holder.checkboxOpt.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.put(position, true);
            } else {
                selectedItems.delete(position);
            }
        });

    }

    // Method to get selected items
    public List<Integer> getSelectedItems() {
        List<Integer> selectedPositions = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            selectedPositions.add(selectedItems.keyAt(i));
        }
        return selectedPositions;
    }

    @Override
    public int getItemCount() { return productOpts.size();}
    public static class ProductOptionViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price, quantity;
        public ImageView image;
        public CheckBox checkboxOpt;
        public ProductOptionViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productOptName);
            price = itemView.findViewById(R.id.productOptPrice);
            quantity = itemView.findViewById(R.id.productOptQuantity);
            image = itemView.findViewById(R.id.productOptImage);
            checkboxOpt = itemView.findViewById(R.id.checkboxOpt);
        }
    }
}
