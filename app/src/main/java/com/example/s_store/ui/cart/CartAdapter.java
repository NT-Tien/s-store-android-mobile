package com.example.s_store.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_store.R;
import com.example.s_store.common.entities.CartItemEntity;
import com.example.s_store.databinding.FragmentCartitemBinding;
import com.example.s_store.di.api.ApiModule;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final List<CartItemEntity> list;
    private final Function<CartItemEntity, Void> onClick;
    private final Function<CartItemEntity, Void> onQuantityChange;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cartitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItemEntity current = this.list.get(position);

        holder.binding.title.setText(current.getProductName());
        holder.binding.description.setText(current.getName());
        holder.binding.itemQuantityText.setText(String.valueOf(current.getQuantity()));
        holder.binding.btnDeleteCartitem.setOnClickListener(v -> this.onClick.apply(current));
        Picasso.get().load(ApiModule.BASE_URL + "/file/image/" + current.getImage()).into(holder.binding.cartItemImage);
        holder.binding.cartItemPrice.setText(String.valueOf(current.getPrice().intValue()) + " VND");

        holder.binding.btnAddQuanity.setOnClickListener(v -> {
            current.setQuantity(current.getQuantity() + 1);
            this.onQuantityChange.apply(current);
        });

        holder.binding.btnMinusQuanity.setOnClickListener(v -> {
            current.setQuantity(current.getQuantity() - 1);
            this.onQuantityChange.apply(current);
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected final FragmentCartitemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = FragmentCartitemBinding.bind(itemView);
        }
    }
}
