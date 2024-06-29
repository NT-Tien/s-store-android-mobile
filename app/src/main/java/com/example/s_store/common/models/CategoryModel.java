package com.example.s_store.common.models;

import com.example.s_store.common.BaseModel;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class CategoryModel extends BaseModel implements Serializable {
    private String name;
    private String description;
    private String image;
}