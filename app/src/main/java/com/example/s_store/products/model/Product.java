package com.example.s_store.products.model;

import java.util.List;

public class Product {
    private String name;
    private String description;
    private Category category;
    private String image;
    private List<ProductOpt> productOpts;

    public Product(String name, String description, Category category, String image, List<ProductOpt> productOpts) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.image = image;
        this.productOpts = productOpts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImage() {
        return "https://s-api.caucalamdev.io.vn/file/image/" + image;
    }

    public List<ProductOpt> getProductOpts() {
        return productOpts;
    }

    public void setProductOptions(List<ProductOpt> productOpts) {
        this.productOpts = productOpts;
    }
}
