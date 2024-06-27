package com.example.s_store.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_store.R;
import com.example.s_store.databinding.FragmentProductsBinding;
import com.example.s_store.products.model.Card;
import com.example.s_store.products.ui.CardAdapter;
import com.example.s_store.products.ui.CategoryAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsFragment extends Fragment {

    private FragmentProductsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView categoryRecyclerView = binding.categoryRecyclerView;
        RecyclerView cardRecyclerView = binding.cardRecyclerView;
// Set layout managers
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        cardRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        List<String> categories = Arrays.asList("Electronics", "Fashion", "Home & Garden", "Sports", "Toys");

        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclerView.setAdapter(categoryAdapter);

        List<Card> cards = new ArrayList<>();
        cards.add(new Card("Laptop", "High performance laptop", 999.99, true, R.drawable.laptop));
        cards.add(new Card("Smartphone", "Latest model smartphone", 799.99, false, R.drawable.smartphone));
        // Add more cards as needed

        CardAdapter cardAdapter = new CardAdapter(cards);
        cardRecyclerView.setAdapter(cardAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

