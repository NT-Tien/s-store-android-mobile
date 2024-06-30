package com.example.s_store.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.s_store.databinding.FragmentHistoryBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private HistoryViewModel historyViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentHistoryBinding.inflate(inflater, container, false);
        this.historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        this.historyViewModel.getList().observe(this.getViewLifecycleOwner(), entry -> {
            HistoryListAdapter historyListAdapter = new HistoryListAdapter(entry);
            this.binding.ordersList.setAdapter(historyListAdapter);
            this.binding.ordersList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        });

        return this.binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.historyViewModel.loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
