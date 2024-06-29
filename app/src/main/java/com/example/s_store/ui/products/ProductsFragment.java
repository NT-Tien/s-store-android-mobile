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
import com.example.s_store.common.models.CategoryModel;
import com.example.s_store.common.models.ProductModel;
import com.example.s_store.databinding.FragmentProductsBinding;
import com.example.s_store.products.controller.CategoryController;
import com.example.s_store.products.controller.ProductController;
import com.example.s_store.products.ui.ProductAdapter;
import com.example.s_store.products.ui.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductsFragment extends Fragment {
    @Inject
    ProductController productController;

    @Inject
    CategoryController categoryController;

    private FragmentProductsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView categoryRecyclerView = binding.categoryRecyclerView;
        RecyclerView productRecyclerView = binding.productRecyclerView;
        // Set layout managers
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<CategoryModel> categories = new ArrayList<>();
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclerView.setAdapter(categoryAdapter);

        // Fetch categories using CategoryFetcher
        categoryController.getAllCategories(new CategoryController.CategoryFetchListener() {
            @Override
            public void onCategoriesFetched(List<CategoryModel> responseData) {
                categories.clear();
                categories.addAll(responseData);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFetchFailed(String errorMessage) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        List<ProductModel> products = new ArrayList<>();
        ProductAdapter productAdapter = new ProductAdapter(products);
        productRecyclerView.setAdapter(productAdapter);

        // Fetch products
        productController.getAllProducts(new ProductController.ProductFetchListener() {
            @Override
            public void onProductsFetched(List<ProductModel> responseData) {
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

