package com.eyes.eyesspace.task.video;

import com.alibaba.fastjson.JSONArray;
import com.eyes.eyesspace.model.entity.Video;
import com.eyes.eyesspace.service.IVideoService;
import com.eyes.eyesspace.task.AbstractTask;
import com.eyes.eyesspace.utils.DateUtils;
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
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class UploadBiliVideo extends AbstractTask {

    private static final String VIDEO_PATH = "video";

    private static final String VIDEO_COVER_PATH = "videoCover";

    @Resource
    private MinioOssStorage minioOssStorage;

    @Resource
    private IVideoService videoService;

    @Override
    @XxlJob("uploadBiliVideo")
    public void execute() {
        // 初始化参数
        Map<String, Object> paramMap = buildParamMap(XxlJobHelper.getJobParam());
        String videoLocalPath = (String) getNotNullParam(paramMap, "videoLocalPath");
        String infoLocalPath = (String) getNotNullParam(paramMap, "infoLocalPath");

        // 读取 info json 文件
        FileReader fileReader = null;
        Reader reader = null;
        StringBuilder jsonInfoBuilder = new StringBuilder();
        try {
            File file = new File(infoLocalPath);
            fileReader = new FileReader(file);
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            int ch = 0;
            while ((ch = reader.read()) != -1) {
                jsonInfoBuilder.append((char) ch);
            }
        } catch (Exception e) {
            XxlJobHelper.handleFail("failed to read info json file! error: " + e.getMessage());
            return;
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                XxlJobHelper.log("failed to close stream! error: {}", e);
            }
        }
        List<BiliEntity> biliEntityList = JSONArray.parseArray(jsonInfoBuilder.toString(), BiliEntity.class);

        // 读取 video 文件夹
        File file = new File(videoLocalPath);
        File[] files = file.listFiles();
        if (Objects.isNull(files)) {
            XxlJobHelper.handleFail("videoLocalPath " + videoLocalPath + " doesn't exist");
            return;
        }
        for (File item : files) {
            String fileName = item.getName();
            BiliEntity entity = null;
            for (BiliEntity biliEntity : biliEntityList) {
                if (!fileName.startsWith(biliEntity.getTitle())) {
                    continue;
                }
                entity = biliEntity;
            }
            if (entity == null) {
                XxlJobHelper.log(fileName + " does not exist in json.");
                continue;
            }
            handleVideo(entity, item);
        }
    }

    private void handleVideo(BiliEntity entity, File item) {
        // 上传封面
        String coverUrl;
        try {
            String coverName = RandomUtils.getUUid() + ".png";
            ObjectUploadModel model = minioOssStorage.putObjectByNetUrl(entity.getCover(), coverName, VIDEO_COVER_PATH);
            coverUrl = minioOssStorage.getSimpleUrl(model.getObjectName(), VIDEO_COVER_PATH);
        } catch (Exception e) {
            XxlJobHelper.log("fail to upload video cover! error: {}", e);
            return;
        }
        // 上传视频源文件
        String videoUrl;
        try {
            FileInputStream fis = new FileInputStream(item);
            byte[] data = IOUtils.inputStreamToBytes(fis);
            String videoName = RandomUtils.getUUid() + ".mp4";
            ObjectUploadModel model = minioOssStorage.putObject(data, videoName, VIDEO_PATH);
            videoUrl = minioOssStorage.getSimpleUrl(model.getObjectName(), VIDEO_PATH);
        } catch (Exception e) {
            XxlJobHelper.log("fail to read file! error: {}", e);
            return;
        }
        // 写入数据库
        Video video = new Video();
        video.setPictureUrl(coverUrl);
        video.setVideoUrl(videoUrl);
        video.setOriginalAuthor(entity.getUpper().getName());
        video.setTitle(entity.title);
        video.setOriginalUrl("https://www.bilibili.com/video/" + entity.getBvid());
        video.setOwnerComment("暂无");
        LocalDateTime localDateTime = DateUtils.timestampToDatetime(entity.fav_time * 1000);
        video.setCreateTime(localDateTime);
        video.setUpdateTime(localDateTime);
        if (videoService.save(video)) {
            XxlJobHelper.log( "video " + entity.getTitle() + " upload success!");
        } else {
            XxlJobHelper.log("failed to save db: {}", entity);
        }
    }

    @Data
    static class BiliEntity {
        private String title;

        private String cover;

        private String bvid;

        private UpperEntity upper;

        private Long fav_time;
    }

    @Data
    static class UpperEntity {
        private String name;
    }
}
