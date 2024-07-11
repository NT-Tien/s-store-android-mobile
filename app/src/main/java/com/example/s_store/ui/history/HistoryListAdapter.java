package com.example.s_store.ui.history;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_store.R;
import com.example.s_store.common.models.OrderModel;
import com.example.s_store.databinding.FragmentHistoryItemBinding;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private final List<OrderModel> list;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

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

        Instant instant = Instant.parse(current.getCreatedAt());

        // 2. Convert to LocalDateTime in Your Time Zone (if needed)
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Ho_Chi_Minh")); // Or ZoneId.systemDefault() for server timezone

        // 3. Create a ZonedDateTime (optional, if you want to retain the time zone information)
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("Asia/Ho_Chi_Minh")); // Or ZoneId.systemDefault()

        // 4. Format (optional)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDateTime = localDateTime.format(formatter);
        holder.binding.datePlaced.setText(formattedDateTime);
        holder.binding.orderId.setText(current.getStatus());
        if(current.getStatus().equals("PENDING")) {
            holder.binding.orderId.setTextColor(holder.binding.orderId.getContext().getResources().getColor(R.color.purple_200));
            holder.binding.orderId.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.binding.orderId.getContext());
                builder.setTitle("Proceed to payment");
                builder.setMessage("Order created successfully. Your payment link is " + current.getPayment().getPayment().getOrder_url());
                builder.setPositiveButton("Goto Payment", (dialog, which) -> {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getPayment().getPayment().getOrder_url()));
                    holder.binding.orderId.getContext().startActivity(i);
                });
                builder.show();
            });
        } else {
            holder.binding.orderId.setTextColor(holder.binding.orderId.getContext().getResources().getColor(R.color.black));
            holder.binding.orderId.setOnClickListener(v -> {});

        }

        holder.binding.totalAmount.setText(String.valueOf(current.getTotal()) + " VND");

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                holder.binding.orderItemList.getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        layoutManager
                .setInitialPrefetchItemCount(
                        current
                                .getItems()
                                .size());


        HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(current.getItems());
        holder.binding.orderItemList.setAdapter(historyItemAdapter);
        holder.binding.orderItemList.setLayoutManager(layoutManager);
        holder.binding.orderItemList.setRecycledViewPool(viewPool);
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
