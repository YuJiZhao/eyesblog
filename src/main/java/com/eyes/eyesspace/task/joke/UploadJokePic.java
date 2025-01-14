package com.eyes.eyesspace.task.joke;

import com.alibaba.fastjson.JSON;
import com.eyes.eyesspace.model.entity.Joke;
import com.eyes.eyesspace.service.IJokeService;
import com.eyes.eyesspace.task.AbstractTask;
import com.eyes.eyesspace.utils.IOUtils;
import com.eyes.eyesspace.utils.RandomUtils;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.github.eyesyeager.eyesStorageStarter.entity.ObjectUploadModel;
import io.github.eyesyeager.eyesStorageStarter.service.storage.MinioOssStorage;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Component
public class UploadJokePic extends AbstractTask {

    private static final String JOKE_PATH = "joke";

    private static final String JOKE_PIC_NAME_SPLIT = "-";

    @Resource
    private MinioOssStorage minioOssStorage;

    @Resource
    private IJokeService jokeService;

    @Override
    @XxlJob("uploadJokePic")
    public void execute() {
        // 初始化参数
        Map<String, Object> paramMap = buildParamMap(XxlJobHelper.getJobParam());
        String localPath = (String) paramMap.get("localPath");
        if (StringUtils.isBlank(localPath)) {
            XxlJobHelper.handleFail("localPath can not be null");
            return;
        }

        // 读取文件夹，得到文件列表
        File file = new File(localPath);
        File[] files = file.listFiles();
        if (Objects.isNull(files)) {
            XxlJobHelper.handleFail("localPath " + localPath + " doesn't exist");
            return;
        }
        List<String> invalidPicNameList = new ArrayList<>();
        Map<Integer, JokePicEntity> jokePicEntityMap = new Hashtable<>();
        for (File f : files) {
            // 读取图片转换为流
            byte[] data;
            try {
                FileInputStream fis = new FileInputStream(f);
                data = IOUtils.inputStreamToBytes(fis);
            } catch (Exception e) {
                XxlJobHelper.log("读取文件失败: {}", e.getMessage());
                continue;
            }

            // 处理文件名
            String fileName = f.getName();
            String name = fileName.substring(0, fileName.lastIndexOf("."));
            String[] split = name.split(JOKE_PIC_NAME_SPLIT);
            if (split.length != 2 && split.length != 3) {
                invalidPicNameList.add(fileName);
                continue;
            }

            // 上传文件
            String url;
            try {
                String objectName = RandomUtils.getUUid() + fileName.substring(fileName.lastIndexOf("."));
                ObjectUploadModel model = minioOssStorage.putObject(data, objectName, JOKE_PATH);
                url = minioOssStorage.getSimpleUrl(model.getObjectName(), JOKE_PATH);
            } catch (Exception e) {
                XxlJobHelper.log("上传文件到 minio 失败! fileName: {}, err: {}", fileName, e.getMessage());
                continue;
            }

            // 拼接数据
            Integer id = Integer.parseInt(split[0]);
            String category = split[1];
            if (!jokePicEntityMap.containsKey(id)) {
                JokePicEntity entity = new JokePicEntity();
                entity.setId(id);
                entity.setCategory(category);
                jokePicEntityMap.put(id, entity);
            }
            jokePicEntityMap.get(id).getUrlList().add(url);
        }

        // 打印不规则命名文件
        if (!invalidPicNameList.isEmpty()) {
            StringBuilder builder = new StringBuilder("存在非法命名文件：");
            for (String invalidPicName : invalidPicNameList) {
                builder.append(invalidPicName).append(";");
            }
            XxlJobHelper.log(builder.toString());
        }

        // 批量写入数据库
        List<Joke> jokeList = new ArrayList<>();
        for (Map.Entry<Integer, JokePicEntity> entry : jokePicEntityMap.entrySet()) {
            JokePicEntity entity = entry.getValue();
            Joke joke = new Joke();
            joke.setCategory(entity.getCategory());
            joke.setUrlList(JSON.toJSONString(entity.getUrlList()));
            jokeList.add(joke);
            XxlJobHelper.log("即将插入数据库！文件id:{}，文件链接:{}", entry.getKey(), joke.getUrlList());
        }
        if (jokeService.saveBatch(jokeList)) {
            XxlJobHelper.log("任务执行成功！共同步梗图记录数：{}", jokeList.size());
        } else {
            XxlJobHelper.handleFail("文件插入数据库失败！");
        }
    }

    @Data
    static class JokePicEntity {
        private Integer id;

        private String category;

        private List<String> urlList = new ArrayList<>();
    }
}
