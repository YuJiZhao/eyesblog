package com.eyes.eyesspace.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author eyesYeager
 * @date 2024/9/22 14:26
 */

@Getter
@AllArgsConstructor
public enum TrackPointFieldEnum {
    ID("id"),

    UID("uid"),

    BROWSER_ID("browser_id"),

    SESSION_ID("session_id"),

    TITLE("title"),

    CONTENT("content"),

    PATH("path"),

    IP("ip"),

    OS("os"),

    BROWSER("browser");

    private final String field;
}
