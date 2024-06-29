package com.example.s_store.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_store.R;
import com.example.s_store.databinding.FragmentProductsBinding;
import com.example.s_store.products.controller.CategoryController;
import com.example.s_store.products.controller.ProductController;
import com.example.s_store.products.model.Product;
import com.example.s_store.products.model.Category;
import com.example.s_store.products.network.ApiService;
import com.example.s_store.products.network.RetrofitClient;
import com.example.s_store.products.ui.ProductAdapter;
import com.example.s_store.products.ui.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

    private FragmentProductsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initiate Retrofit service
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        RecyclerView categoryRecyclerView = binding.categoryRecyclerView;
        RecyclerView productRecyclerView = binding.productRecyclerView;
        // Set layout managers
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        ArrayList<Category> categories = new ArrayList<>();
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclerView.setAdapter(categoryAdapter);

        // Fetch categories using CategoryFetcher
        CategoryController.getAllCategories(new CategoryController.CategoryFetchListener() {
            @Override
            public void onCategoriesFetched(List<Category> responseData) {
                // Update UI with categories
                categories.clear();
                categories.addAll(responseData);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFetchFailed(String errorMessage) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        List<Product> products = new ArrayList<>();
        ProductAdapter productAdapter = new ProductAdapter(products);
        productRecyclerView.setAdapter(productAdapter);

        ProductController.getAllProducts(new ProductController.ProductFetchListener() {
            @Override
            public void onProductsFetched(List<Product> responseData) {
                products.clear();
                products.addAll(responseData);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFetchFailed(String errorMessage) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

