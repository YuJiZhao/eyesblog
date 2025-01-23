package com.eyes.eyesspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesspace.model.entity.Shuoshuo;
import com.eyes.eyesspace.model.request.ShuoAddRequest;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ShuoMapper extends BaseMapper<Shuoshuo> {
	@Insert("insert into shuoshuo (content, status, create_time) values (#{content}, #{status}, now())")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	Boolean addShuo(ShuoAddRequest shuoAddRequest);

	Integer addShuoPics(List<String> picList, Integer id);

	Long getShuoPicNum(String statusCondition);
}
