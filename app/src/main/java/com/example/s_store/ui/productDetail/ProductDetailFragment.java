package com.example.s_store.ui.productDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.s_store.R;
import com.example.s_store.common.entities.CartItemEntity;
import com.example.s_store.common.models.ProductModel;
import com.example.s_store.common.models.ProductOptionalModel;
import com.example.s_store.databinding.FragmentProductDetailBinding;
import com.example.s_store.di.cart.CartService;
import com.example.s_store.products.controller.ProductController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetailFragment extends Fragment {
    @Inject
    CartService cartService;
    private FragmentProductDetailBinding binding;
    private ProductDetailViewModel productDetailViewModel;
    private String productId;
    private TextView productName;
    private TextView productDescription;
    private ImageView productImage;
    private Button addToCartBtn;

    private RecyclerView productOptsRecyclerView;

    private ProductModel productModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        if (getArguments() != null) {
            productId = ProductDetailFragmentArgs.fromBundle(getArguments()).getProductId();
            productDetailViewModel.fetchProduct(productId);
        }

        productName = binding.productName;
        productDescription = binding.productDescription;
        productImage = binding.productImage;
        addToCartBtn = binding.addToCartButton;

        productOptsRecyclerView = binding.optionsRecyclerView;

        productOptsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        List<ProductOptionalModel> productOpts = new ArrayList<>();
        ProductOptionAdapter productOptionAdapter = new ProductOptionAdapter(productOpts);
        productOptsRecyclerView.setAdapter(productOptionAdapter);

        productDetailViewModel.getProduct(productId).observe(getViewLifecycleOwner(), responseData -> {
            productModel = responseData;
            productName.setText(responseData.getName());
            productDescription.setText(responseData.getDescription());

            String imageUrl = getString(R.string.image_base_url) + responseData.getImage();
            Glide.with(this).load(imageUrl).into(productImage);

            productOpts.clear();
            productOpts.addAll(responseData.getProductOpts());
            productOptionAdapter.notifyDataSetChanged();
        });

        productDetailViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<Integer> selectedItems = productOptionAdapter.getSelectedItems();
                int selectedPosition = productOptionAdapter.getSelectedPosition();

                productDetailViewModel.addProduct(productModel, selectedPosition);
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
