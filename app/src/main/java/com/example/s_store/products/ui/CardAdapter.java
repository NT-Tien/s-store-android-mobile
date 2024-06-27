package com.example.s_store.products.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_store.R;
import com.example.s_store.products.model.Card;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<Card> cards;

    public CardAdapter(List<Card> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.name.setText(card.getName());
        holder.description.setText(card.getDescription());
        holder.price.setText(String.format("$%.2f", card.getPrice()));
        holder.status.setText(card.isInStock() ? "In Stock" : "Out of Stock");
        holder.image.setImageResource(card.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price, status;
        public ImageView image;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cardName);
            description = itemView.findViewById(R.id.cardDescription);
            price = itemView.findViewById(R.id.cardPrice);
            status = itemView.findViewById(R.id.cardStatus);
            image = itemView.findViewById(R.id.cardImage);
        }
    }
}

