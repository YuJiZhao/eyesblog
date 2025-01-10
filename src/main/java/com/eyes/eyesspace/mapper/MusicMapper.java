package com.eyes.eyesspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesspace.model.entity.Music;
import com.eyes.eyesspace.model.po.MusicInfoPO;
import com.eyes.eyesspace.model.request.MusicAddRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import org.apache.ibatis.annotations.Update;

@Mapper
public interface MusicMapper extends BaseMapper<Music> {
	@Insert("insert into music " +
			"(url, pic, title, author, lrc, owner_comment, status, create_time) " +
			"values " +
			"(#{url}, #{pic}, #{title}, #{author}, #{lrc}, #{ownerComment}, #{status}, now())")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	Boolean addMusic(MusicAddRequest musicAddRequest);

	Integer getMusicListInfo(Integer status);

	@Select("select lrc from music where id=#{id}")
	String getMusicLrc(Integer id);

	@Select("select user_music_key from music_user where uid=#{uid}")
	String getUserMusicKey(Long uid);

	MusicInfoPO getRandomMusicList(String role, List<Integer> ids);

	@Insert("insert into music_user (uid, user_music_key) values (#{uid}, #{userMusicKey})")
	Boolean insertUserMusicKey(Long uid, String userMusicKey);

	@Update("update music_user set user_music_key=#{userMusicKey} where uid=#{uid}")
	Boolean updateUserMusicKey(Long uid, String userMusicKey);

	@Update("update music set views=views+1 where id=#{id}")
	Boolean updateViewsNum(Integer id);
}
