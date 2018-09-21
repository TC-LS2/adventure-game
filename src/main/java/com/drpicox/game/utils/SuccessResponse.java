package com.drpicox.game.utils;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.LinkedHashMap;
import java.util.Map;

public class SuccessResponse {

    private String status;

    public SuccessResponse(String status) {
        this.status = status;
    }


    @JsonValue
    public Map getJsonObject() {
        Map result = new LinkedHashMap();
        result.put("status", status);
        return result;
    }
}
