package com.eyes.eyesspace.task.joke;

import com.eyes.eyesspace.task.AbstractTask;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UploadJokePic extends AbstractTask {

    @Override
    @XxlJob("uploadJokePic")
    public void execute() {
        // 初始化请求参数
        Map<String, Object> paramMap = buildParamMap(XxlJobHelper.getJobParam());
        String localPath = (String) paramMap.get("localPath");
        if (StringUtils.isBlank(localPath)) {
            XxlJobHelper.handleFail("localPath can not be null");
            return;
        }

        // 读取文件夹，得到文件列表
    }
}
