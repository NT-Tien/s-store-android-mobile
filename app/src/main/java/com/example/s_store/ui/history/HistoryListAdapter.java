package com.example.s_store.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_store.R;
import com.example.s_store.common.models.OrderModel;
import com.example.s_store.databinding.FragmentHistoryItemBinding;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private final List<OrderModel> list;

    public HistoryListAdapter(List<OrderModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel current = this.list.get(position);

        holder.binding.title.setText(current.getId());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected final FragmentHistoryItemBinding binding;

        public ViewHolder(View view) {
            super(view);
            this.binding = FragmentHistoryItemBinding.bind(view);
        }
    }
}
