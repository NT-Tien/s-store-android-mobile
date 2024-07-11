package com.example.s_store.ui.history;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_store.R;
import com.example.s_store.common.models.OrderModel;
import com.example.s_store.databinding.FragmentHistoryItemDetailsBinding;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.ViewHolder> {

    private final List<OrderModel.Item> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.fragment_history_item_details, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel.Item current = this.list.get(position);
        holder.binding.title.setText(current.getName());
        holder.binding.price.setText(String.valueOf(current.getPrice()) + " VND");
        holder.binding.quantity.setText("Qty: " + String.valueOf(current.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentHistoryItemDetailsBinding binding;

        public ViewHolder(View view) {
            super(view);
            this.binding = FragmentHistoryItemDetailsBinding.bind(view);
        }
    }
}
