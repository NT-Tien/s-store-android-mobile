package com.example.s_store.common.util;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DecodeJwt {
    public String decode(String jwt, String name) {
        String[] parts = jwt.split("\\.");
        if(parts.length != 3) {
            return null;
        }

//        JSONObject header = new JSONObject(parse(parts[0]));
        try {
            JSONObject payload = new JSONObject(parse(parts[1]));
            return payload.getString(name);
        } catch (JSONException e) {
            return null;
        }
    }

    private String parse(String jwt) {
        return new String(java.util.Base64.getDecoder().decode(jwt));
    }


}
