package com.example.s_store.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.s_store.databinding.FragmentHistoryBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
