package com.eyes.eyesspace.task;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

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
}
