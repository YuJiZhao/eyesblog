package com.eyes.eyesspace.utils;

import cn.hutool.core.lang.UUID;
import com.aliyuncs.utils.StringUtils;
import com.eyes.eyesspace.constant.MediaConstant;
import com.eyes.eyesspace.exception.CustomException;
import io.github.eyesyeager.eyesStorageStarter.entity.ObjectUploadModel;
import io.github.eyesyeager.eyesStorageStarter.exception.EyesStorageException;
import io.github.eyesyeager.eyesStorageStarter.service.EyesOssStorage;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 * data 2024/12/1 14:26
 */

@Component
public class FileUtils {

	@Resource
	private EyesOssStorage eyesOssStorage;

	/**
	 * 上传文件
	 *
	 * @param multipartFile 文件
	 * @param path          路径
	 * @return 文件地址
	 */
	public String putObject(MultipartFile multipartFile, String path) throws CustomException {
		String originalFilename = multipartFile.getOriginalFilename();
		String fileName = UUID.fastUUID().toString();
		if (StringUtils.isEmpty(originalFilename)) {
			fileName += MediaConstant.DEFAULT_MEDIA_TYPE;
		} else {
			String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
			fileName += suffix;
		}
		try {
			ObjectUploadModel model = eyesOssStorage.putObject(multipartFile.getBytes(), fileName, path);
			return eyesOssStorage.getSimpleUrl(model.getObjectName(), path);
		} catch (Exception e) {
			throw new CustomException("文件上传失败！", e);
		}
	}

	/**
	 * 通过网络地址上传文件
	 *
	 * @param url  文件网络地址
	 * @param path 存储路径
	 * @return 文件地址
	 */
	public String putObjectByUrl(String url, String path) throws CustomException {
		String fileName = UUID.fastUUID() + MediaConstant.DEFAULT_MEDIA_TYPE;
		try {
			ObjectUploadModel model = eyesOssStorage.putObjectByNetUrl(url, fileName, path);
			return eyesOssStorage.getSimpleUrl(model.getObjectName(), path);
		} catch (EyesStorageException e) {
			throw new CustomException("网络文件上传失败！", e);
		}
	}
}
