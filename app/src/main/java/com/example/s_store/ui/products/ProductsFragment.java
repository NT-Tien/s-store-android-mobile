package com.example.s_store.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_store.R;
import com.example.s_store.common.models.CategoryModel;
import com.example.s_store.common.models.ProductModel;
import com.example.s_store.databinding.FragmentProductsBinding;
import com.example.s_store.products.controller.CategoryController;
import com.example.s_store.products.controller.ProductController;
import com.example.s_store.ui.productDetail.ProductDetailFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductsFragment extends Fragment implements ProductAdapter.OnProductClickListener {
    @Inject
    ProductController productController;

    @Inject
    CategoryController categoryController;

    private FragmentProductsBinding binding;
    private ProductsViewModel productsViewModel;
    private CategoriesViewModel categoriesViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView categoryRecyclerView = binding.categoryRecyclerView;
        RecyclerView productRecyclerView = binding.productRecyclerView;
        // Set layout managers
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Initialize ViewModel
        productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);

        List<CategoryModel> categories = new ArrayList<>();
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclerView.setAdapter(categoryAdapter);

        // Observe categories LiveData
        categoriesViewModel.getCategories().observe(getViewLifecycleOwner(), responseData -> {
            categories.clear();
            categories.addAll(responseData);
            categoryAdapter.notifyDataSetChanged();
        });

        // Observe error messages
        categoriesViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        List<ProductModel> products = new ArrayList<>();
        ProductAdapter productAdapter = new ProductAdapter(products, this);
        productRecyclerView.setAdapter(productAdapter);

        // Observe products LiveData
        productsViewModel.getProducts().observe(getViewLifecycleOwner(), responseData -> {
            products.clear();
            products.addAll(responseData);
            productAdapter.notifyDataSetChanged();
        });

        // Observe error messages
        productsViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
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

    @Override
    public void onProductClick(ProductModel product, int position) {
        // Navigate to product detail activity with product ID
//        Bundle bundle = new Bundle();
//        bundle.putString("productId", product.getId());
//        Navigation.findNavController(requireView()).navigate(R.id.action_productsFragment_to_productDetailFragment, bundle);
        NavDirections action = ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(product.getId());
        Navigation.findNavController(requireView()).navigate(action);
    }
}

