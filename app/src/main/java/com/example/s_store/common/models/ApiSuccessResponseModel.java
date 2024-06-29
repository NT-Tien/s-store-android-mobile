package com.example.s_store.common.models;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiSuccessResponseModel <Model> implements Serializable {
    private Model data;
    private String message;
    private Integer statusCode;
}
