package com.eyes.eyesspace.task.music;

import com.eyes.eyesspace.constant.FileUrlTypeEnum;
import com.eyes.eyesspace.model.entity.Music;
import com.eyes.eyesspace.service.IMusicService;
import com.eyes.eyesspace.task.AbstractTask;
import com.eyes.eyesspace.utils.RandomUtils;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.github.eyesyeager.eyesStorageStarter.entity.ObjectUploadModel;
import io.github.eyesyeager.eyesStorageStarter.exception.EyesStorageException;
import io.github.eyesyeager.eyesStorageStarter.service.storage.MinioOssStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

@RefreshScope
@Component
public class UploadMusic extends AbstractTask {

    @Value("${path.folder.music}")
    private String musicPath;

    @Value("${path.folder.music-cover}")
    private String musicCoverPath;

    @Resource
    private MinioOssStorage minioOssStorage;

    @Resource
    private IMusicService musicService;

    @Override
    @XxlJob("uploadMusic")
    public void execute() {
        // 初始化参数
        Map<String, Object> paramMap = buildParamMap(XxlJobHelper.getJobParam());
        String title = (String) getNotNullParam(paramMap, "title");
        String author = (String) getNotNullParam(paramMap, "author");
        String musicUrl = (String) getNotNullParam(paramMap, "musicUrl");
        String musicUrlType = (String) getParam(paramMap, "musicUrlType", FileUrlTypeEnum.LOCAL.getValue());
        String musicExtension = (String) getParam(paramMap, "musicExtension", ".mp3");
        String coverUrl = (String) getNotNullParam(paramMap, "picUrl");
        String coverUrlType = (String) getParam(paramMap, "picUrlType", FileUrlTypeEnum.SIMPLE_NET.getValue());
        String lrc = (String) getParam(paramMap, "lrc");
        String ownerComment = (String) getParam(paramMap, "ownerComment", "暂无");
        Integer status = getParam(paramMap, "status", null) == null ? null : (Integer) getParam(paramMap, "status");

        // 构建实体类
        Music music = new Music();
        music.setTitle(title);
        music.setAuthor(author);
        music.setMusicUrl(musicUrl);
        music.setCoverUrl(coverUrl);
        music.setLrc(lrc);
        music.setComment(ownerComment);
        music.setStatus(status);
        LocalDateTime now = LocalDateTime.now();
        music.setCreateTime(now);
        music.setUpdateTime(now);

        // 上传文件，构建 music 实体
        XxlJobHelper.log("--------------- start processing audio: {} ---------------", music.getTitle());
        try {
            String newCoverUrl = uploadFile(music.getCoverUrl(), coverUrlType, musicCoverPath, ".png");
            music.setCoverUrl(newCoverUrl);
            XxlJobHelper.log("successfully uploaded audio cover: {}", newCoverUrl);
        } catch (Exception e) {
            XxlJobHelper.log("upload audio cover failed! error: {}", e.getMessage());
            XxlJobHelper.log("--------------- fail processing audio: {} ---------------", music.getTitle());
            return;
        }
        try {
            String newMusicUrl = uploadFile(music.getMusicUrl(), musicUrlType, musicPath, musicExtension);
            music.setMusicUrl(newMusicUrl);
            XxlJobHelper.log("successfully uploaded audio file: {}", newMusicUrl);
        } catch (Exception e) {
            XxlJobHelper.log("upload audio file failed! error: {}", e.getMessage());
            XxlJobHelper.log("--------------- fail processing audio: {} ---------------", music.getTitle());
            return;
        }

        // 插入数据库
        if (!musicService.save(music)) {
            XxlJobHelper.log("failed to insert database! entity: {}", music);
            XxlJobHelper.log("--------------- fail processing audio: {} ---------------", music.getTitle());
        }
        XxlJobHelper.log("--------------- processing audio successful: {} ---------------", music.getTitle());
    }

    private String uploadFile(String url, String urlType, String path, String extension) throws EyesStorageException {
        if (!FileUrlTypeEnum.contains(urlType)) {
            throw new IllegalArgumentException("illegal file url type: " + urlType);
        }
        if (FileUrlTypeEnum.LOCAL.getValue().equals(urlType)) {
            return uploadLocalMusic(url, path, extension);
        } else {
            return uploadNetFile(url, path, extension);
        }
    }

    private String uploadNetFile(String url, String path, String extension) throws EyesStorageException {
        String objectName = RandomUtils.getUUid() + extension;
        ObjectUploadModel model = minioOssStorage.putObjectByNetUrl(url, objectName, path);
        return minioOssStorage.getSimpleUrl(model.getObjectName(), path);
    }

    private String uploadLocalMusic(String url, String path, String extension) throws EyesStorageException {
        String objectName = RandomUtils.getUUid() + extension;
        ObjectUploadModel model = minioOssStorage.putObjectByNetUrl(url, objectName, path);
        return minioOssStorage.getSimpleUrl(model.getObjectName(), path);
    }
}
