package com.eyes.eyesspace.script.blogPicReplace;

import com.eyes.eyesspace.mapper.BlogMapper;
import com.eyes.eyesspace.utils.RandomUtils;
import io.github.eyesyeager.eyesStorageStarter.entity.ObjectUploadModel;
import io.github.eyesyeager.eyesStorageStarter.exception.EyesStorageException;
import io.github.eyesyeager.eyesStorageStarter.service.storage.QiniuOssStorage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 博客图片链接更换脚本（CSDN -> 七牛云）
 * @author eyesYeager
 * date 2023/11/4 13:47
 */

@SpringBootTest
public class BlogPicReplaceScript {
    private static final String BLOG_PATH = "blog";

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private QiniuOssStorage qiniuOssStorage;

    @Test
    public void doScript() throws EyesStorageException {
        // 参数定义
        int totalBlog = 0;
        int totalPic = 0;
        // 开始任务
        for (int id = 17; id < 310; id++) {

            if (id % 50 == 0) {
                System.out.println("\n");
            }

            int currentPicNum = 0;
            System.out.printf("---------- 任务开始 id:%s%n", id);

            // 获取博客信息
            String content = blogMapper.getBlogContent(id);
            if (Objects.isNull(content)) {
                System.out.printf("---------- 任务结束 id:%s, 这个id不存在 %n%n", id);
                continue;
            }

            // 正则获取所有 csdn 图片链接
            List<String> csdnPic = getCSDNPicByContent(content);
            if (CollectionUtils.isEmpty(csdnPic)) {
                System.out.printf("---------- 任务结束 id:%s, 这个博客不包含 csdn 图片 %n%n", id);
                continue;
            }

            // 上传七牛云得到替换链接
            Map<String, String> qiniuBycsdnMap = new HashMap<>();
            for (String item : csdnPic) {
                if (qiniuBycsdnMap.containsKey(item)) {
                    continue;
                }
                qiniuBycsdnMap.put(item, putQiniuPic(item));
                currentPicNum++;
            }

            // 使用新链接替换原博客
            updateBlog(id, qiniuBycsdnMap, content);

            totalBlog++;
            totalPic += currentPicNum;
            System.out.printf("---------- 任务结束 id:%s, currentPicNum:%s %n%n", id, currentPicNum);
        }
        System.out.printf("---------- %n 脚本执行完成！ totalBlog:%s, totalPic:%s%n%n", totalBlog, totalPic);
    }

    private List<String> getCSDNPicByContent(String content) {
        String host = "https://img-blog.csdnimg.cn";
        Pattern pattern = Pattern.compile("!\\[.*?\\]\\((.*?)\\)");
        Matcher matcher = pattern.matcher(content);
        List<String> extractedList = new ArrayList<>();
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            if (imageUrl.startsWith(host)) {
                extractedList.add(imageUrl);
            }
            System.out.printf("得到 csdn 图片：%s %n", imageUrl);
        }
        return extractedList;
    }

    private String putQiniuPic(String url) throws EyesStorageException {
        // 组装名字
        String objectName = RandomUtils.getUUid() + ".png";
        ObjectUploadModel model = qiniuOssStorage.putObjectByNetUrl(url, objectName, BLOG_PATH);
        String simpleUrl = qiniuOssStorage.getSimpleUrl(model.getObjectName(), BLOG_PATH);
        System.out.printf("上传七牛云，csdn -> qiniu = %s ：%s %n", url, simpleUrl);
        return simpleUrl;
    }

    private void updateBlog(int id, Map<String, String> qiniuBycsdnMap, String content) {
        for (Map.Entry<String, String> entry : qiniuBycsdnMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            content = content.replace(key, value);
        }
        blogMapper.updateBlogContent(id, content);
    }
}