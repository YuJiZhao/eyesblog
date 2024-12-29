package com.eyes.eyesspace.script.blogPicReplace;

import com.eyes.eyesspace.mapper.BlogMapper;
import com.eyes.eyesspace.utils.RandomUtils;
import io.github.eyesyeager.eyesStorageStarter.entity.ObjectUploadModel;
import io.github.eyesyeager.eyesStorageStarter.exception.EyesStorageException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class QiniuToMinioPicScript {
    @Resource
    private BlogMapper blogMapper;

    private Map<Integer, List<String>> notQiniuPicByIdMap = new HashMap<>();

    @Test
    public void doScript() throws EyesStorageException {
        // 参数定义
        int totalBlog = 0;
        int totalPic = 0;
        // 开始任务
        for (int id = 17; id < 310; id++) {
            int currentPicNum = 0;
            System.out.printf("---------- 任务开始 id:%s%n", id);

            // 获取博客信息
            String content = blogMapper.getBlogContent(id);
            if (Objects.isNull(content)) {
                System.out.printf("---------- 任务结束 id:%s, 这个id不存在 %n%n", id);
                continue;
            }

            // 正则获取七牛云图片链接
            List<String> qiniuPic = getQiniuPicByContent(id, content);
            if (CollectionUtils.isEmpty(qiniuPic)) {
                System.out.printf("---------- 任务结束 id:%s, 这个博客不包含 qiniu 图片 %n%n", id);
                continue;
            }

            // 计算替换链接
            Map<String, String> minioByQiniuMap = new HashMap<>();
            for (String item : qiniuPic) {
                if (minioByQiniuMap.containsKey(item)) {
                    continue;
                }
                minioByQiniuMap.put(item, putMinioPic(item));
                currentPicNum++;
            }

            // 使用新链接替换原博客
            updateBlog(id, minioByQiniuMap, content);

            totalBlog++;
            totalPic += currentPicNum;
            System.out.printf("---------- 任务结束 id:%s, currentPicNum:%s %n%n", id, currentPicNum);
        }

        System.out.printf("---------- %n 脚本执行完成！ totalBlog:%s, totalPic:%s%n%n", totalBlog, totalPic);
        for (Map.Entry<Integer, List<String>> entry : notQiniuPicByIdMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }


    private List<String> getQiniuPicByContent(int id, String content) {
        String host = "http://space-cdn.eyescode.top";
        Pattern pattern = Pattern.compile("!\\[.*?\\]\\((.*?)\\)");
        Matcher matcher = pattern.matcher(content);
        List<String> extractedList = new ArrayList<>();
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            if (imageUrl.startsWith(host)) {
                extractedList.add(imageUrl);
            } else {
                System.out.printf("获取到非 qiniu 图片 %s %n%n", imageUrl);
                List<String> list = notQiniuPicByIdMap.get(id);
                if (CollectionUtils.isEmpty(list)) {
                    notQiniuPicByIdMap.put(id, List.of(imageUrl));
                } else {
                    list.add(imageUrl);
                }
            }
        }
        return extractedList;
    }

    private String putMinioPic(String url) throws EyesStorageException {
        return url.replace("http://space-cdn.eyescode.top", "http://oss.eyescode.top/eyesspace");
    }

    private void updateBlog(int id, Map<String, String> map, String content) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            content = content.replace(key, value);
        }
        blogMapper.updateBlogContent(id, content);
    }
}
