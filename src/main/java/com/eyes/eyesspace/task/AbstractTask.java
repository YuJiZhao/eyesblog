package com.eyes.eyesspace.task;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractTask {

    protected static final String PARAM_SPLIT = "=";

    public abstract void execute();

    protected Map<String, Object> buildParamMap(String jobParam) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isBlank(jobParam)) {
            return new HashMap<>();
        }
        String[] paramArr = jobParam.split("\n");
        for (String param : paramArr) {
            String[] kyArr = param.split(PARAM_SPLIT);
            if (kyArr.length != 2) {
                continue;
            }
            paramMap.put(kyArr[0], kyArr[1]);
        }
        return paramMap;
    }

    protected Object getNotNullParam(Map<String, Object> paramMap, String key) {
        if (Objects.isNull(paramMap) || paramMap.isEmpty()) {
            throw new IllegalArgumentException("paramMap is null or empty");
        }
        Object value = paramMap.get(key);
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(key + " can not be null");
        }
        return value;
    }

    protected Object getParam(Map<String, Object> paramMap, String key) {
        if (Objects.isNull(paramMap) || paramMap.isEmpty()) {
            throw new IllegalArgumentException("paramMap is null or empty");
        }
        return paramMap.get(key);
    }

    protected Object getParam(Map<String, Object> paramMap, String key, Object defaultValue) {
        if (Objects.isNull(paramMap) || paramMap.isEmpty()) {
            throw new IllegalArgumentException("paramMap is null or empty");
        }
        return paramMap.getOrDefault(key, defaultValue);
    }
}
