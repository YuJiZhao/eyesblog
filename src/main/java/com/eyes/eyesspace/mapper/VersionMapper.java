package com.eyes.eyesspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesspace.model.entity.Version;
import com.eyes.eyesspace.model.po.VersionInfoPO;
import com.eyes.eyesspace.model.po.VersionPicPO;
import com.eyes.eyesspace.model.dto.VersionListDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author eyesYeager
 * date 2023/5/20 16:43
 */

@Mapper
public interface VersionMapper extends BaseMapper<Version> {
	@Select("select version, type from version where (type, create_time) in (select type, MAX(create_time) from version where status=0 group by type)")
	List<VersionInfoPO> getVersionInfo();

	@Select("select COUNT(*) from version where status=0")
	Long getVersionNum();

	@Select("select id from version where status=0 order by create_time desc, id desc limit #{start}, #{pageSize}")
	List<Integer> getVersionIdList(Integer start, Integer pageSize);

	@Select("<script>"
			+ "select version_id, url from version_pic "
			+ "where version_id in "
			+ "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ "</script>")
	List<VersionPicPO> getBatchVersionPics(List<Integer> ids);

	@Select("<script>"
			+ "select id, version, type, description, create_time from version "
			+ "where id in "
			+ "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ "</script>")
	List<VersionListDTO> getVersionListByIds(List<Integer> ids);
}
