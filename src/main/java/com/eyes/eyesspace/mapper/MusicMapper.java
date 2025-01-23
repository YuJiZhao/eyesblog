package com.eyes.eyesspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesspace.model.entity.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MusicMapper extends BaseMapper<Music> {
	@Select("select * from music where ${statusSqlCondition} order by rand() limit #{limit}")
	List<Music> getRandomMusicList(String statusSqlCondition, int limit);
}
