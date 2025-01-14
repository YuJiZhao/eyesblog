package com.eyes.eyesspace.script;

import com.alibaba.fastjson.JSON;
import com.eyes.eyesspace.model.entity.Shuoshuo;
import com.eyes.eyesspace.service.IShuoService;
import com.eyes.eyesspace.utils.DateUtils;
import com.eyes.eyesspace.utils.RandomUtils;
import io.github.eyesyeager.eyesStorageStarter.entity.ObjectUploadModel;
import io.github.eyesyeager.eyesStorageStarter.exception.EyesStorageException;
import io.github.eyesyeager.eyesStorageStarter.service.storage.MinioOssStorage;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 同步 hexo 框架时的说说数据
 */

@SpringBootTest
public class SyncOldShuoData {

    private static final String SHUO_PATH = "shuoshuo";

    @Resource
    private IShuoService shuoService;

    @Resource
    private MinioOssStorage minioOssStorage;

    @Test
    public void doScript() throws EyesStorageException {
        // 解析数据文件
        String filePath = "H:\\temp\\KmOfkuNuFQB6hYxL6yQnuYbf-MdYXbMMI-exporttppNyPu917949275708300561\\shuoshuo.0.jsonl";
        List<OldShuoEntity> oldShuoEntityList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                OldShuoEntity oldShuoEntity = JSON.parseObject(line, OldShuoEntity.class);
                oldShuoEntityList.add(oldShuoEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 替换图片链接
        Map<String, String> minioPicMap = new HashMap<>();
        for (OldShuoEntity oldShuoEntity : oldShuoEntityList) {
            String content = oldShuoEntity.getAtContentMd();
            List<String> picList = getPicByContent(content);
            if (CollectionUtils.isEmpty(picList)) {
                continue;
            }
            // 上传 minio 得到替换链接
            for (String item : picList) {
                if (minioPicMap.containsKey(item)) {
                    continue;
                }
                minioPicMap.put(item, putMinioPic(item));
            }
        }

        // 构建实体数据
        List<Shuoshuo> entityList = new ArrayList<>();
        for (OldShuoEntity oldShuoEntity : oldShuoEntityList) {
            Shuoshuo shuoshuo = new Shuoshuo();
            shuoshuo.setCreateTime(DateUtils.dateToLocalDateTime(oldShuoEntity.getCreatedAt()));
            shuoshuo.setUpdateTime(DateUtils.dateToLocalDateTime(oldShuoEntity.getUpdatedAt()));
            String content = oldShuoEntity.getAtContentMd();
            for (Map.Entry<String, String> entry : minioPicMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                content = content.replace(key, value);
            }
            shuoshuo.setContent(content);
            entityList.add(shuoshuo);
        }

        // 插入数据库
        shuoService.saveBatch(entityList);
    }

    private List<String> getPicByContent(String content) {
        Pattern pattern = Pattern.compile("!\\[.*?\\]\\((.*?)\\)");
        Matcher matcher = pattern.matcher(content);
        List<String> extractedList = new ArrayList<>();
        while (matcher.find()) {
            extractedList.add(matcher.group(1));
        }
        return extractedList;
    }

    private String putMinioPic(String url) throws EyesStorageException {
        String objectName = RandomUtils.getUUid() + ".png";
        url = url.replace("https://cdn.jsdelivr.net/gh/YuJiZhao/picbed/blog/shuoshuo", "http://192.168.118.128:9000/eyesspace/shuo");
        ObjectUploadModel model = minioOssStorage.putObjectByNetUrl(url, objectName, SHUO_PATH);
        return minioOssStorage.getSimpleUrl(model.getObjectName(), SHUO_PATH);
    }

    @Data
    static class OldShuoEntity {
        private Date updatedAt;

        private Date createdAt;

        private String atContentMd;
    }
}
