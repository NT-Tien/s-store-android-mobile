package com.example.s_store.common;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@SuperBuilder
public class BaseModel implements Serializable {
    private String id;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
