package com.eyes.eyesspace.mapper;

import com.eyes.eyesspace.model.po.VideoInfoPO;
import com.eyes.eyesspace.model.request.VideoAddRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import org.apache.ibatis.annotations.Update;

@Mapper
public interface VideoMapper {
	@Insert(
			"insert into video " +
					"(video_url, picture_url, title, original_url, original_author, owner_comment, status, create_time) " +
					"values " +
					"(#{videoUrl}, #{pictureUrl}, #{title}, #{originalUrl}, #{originalAuthor}, #{ownerComment}, #{status}, now())")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	Boolean addVideo(VideoAddRequest videoAddRequest);

	Integer getVideoListInfo(Integer status);

	@Select("select user_video_key from video_user where uid=#{uid}")
	String getUserVideoKey(Long uid);

	VideoInfoPO getRandomVideo(String role, List<Integer> ids);

	@Insert("insert into video_user (uid, user_video_key) values (#{uid}, #{userVideoKey})")
	Boolean insertUserVideoKey(Long uid, String userVideoKey);

	@Update("update video_user set user_video_key=#{userVideoKey} where uid=#{uid}")
	Boolean updateUserVideoKey(Long uid, String userVideoKey);

	@Update("update video set views=views+1 where id=#{id}")
	Boolean updateViewsNum(Integer id);
}
