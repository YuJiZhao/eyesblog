package com.eyes.eyesspace.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileUrlTypeEnum {

    LOCAL("local"),

    SIMPLE_NET("simple-net"),

    SINGED_NET("singed-net"),
    ;

    private final String value;

    public static boolean contains(String value) {
        return LOCAL.value.equals(value) || SIMPLE_NET.value.equals(value) || SINGED_NET.value.equals(value);
    }
}
