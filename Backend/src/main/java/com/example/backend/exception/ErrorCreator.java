package com.example.backend.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorCreator {
    public static Map<String, String> createError(String message) {
        Map<String, String> error = new HashMap<>();
       // error.put("error", "Problem with adding space");
        error.put("error", message);
        return error;
    }
}
