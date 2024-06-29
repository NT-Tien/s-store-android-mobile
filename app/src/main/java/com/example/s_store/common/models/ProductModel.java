package com.example.s_store.common.models;

import com.example.s_store.common.BaseModel;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class ProductModel extends BaseModel implements Serializable {
    private String name;
    private String description;
    private String image;
    private ProductModel.Status status;
    private CategoryModel category;
    private List<ProductOptionalModel> productOpts;

    @RequiredArgsConstructor
    @Getter
    public enum Status {
        ACTIVE ("ACTIVE"),
        INACTIVE ("INACTIVE");

        private final String status;
    }
}
