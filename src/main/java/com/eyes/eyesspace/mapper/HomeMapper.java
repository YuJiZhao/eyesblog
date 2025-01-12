package com.eyes.eyesspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesspace.model.entity.Home;
import com.eyes.eyesspace.model.po.HomeListPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

@Mapper
public interface HomeMapper extends BaseMapper<Home> {
	@Select("select type, cid from home where ${statusCondition} order by create_time desc, id desc limit #{start}, #{size}")
	List<HomeListPO> getHomeList(Integer start, Integer size, String statusCondition);

	@Select("select count(*) from home where ${statusCondition}")
	Long getHomeListTotal(String statusCondition);

	@Insert("insert into home (type, cid, create_time, status) values (#{type}, #{id}, now(), #{status})")
	Boolean insertHome(Integer type, Integer id, Integer status);
}
