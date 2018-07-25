package com.example.data.model;

/**
 * Created by Sundararaghavan on 7/24/2018.
 */

public enum ImageSize {

    MEDIUM,
    LARGE;

    public String getValue() {
        if (this == MEDIUM) return "z";
        return "h";
    }

}