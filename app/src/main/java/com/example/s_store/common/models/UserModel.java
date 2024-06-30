package com.example.s_store.common.models;

import com.example.s_store.common.BaseModel;

import java.io.Serializable;

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
public class UserModel extends BaseModel implements Serializable {
    private String username;
    private String email;
    private String phone;
    private UserModel.Role role;
    @RequiredArgsConstructor
    @Getter
    public enum Role {
        USER ("user"),
        ADMIN ("admin");

        private final String role;
    }

}
