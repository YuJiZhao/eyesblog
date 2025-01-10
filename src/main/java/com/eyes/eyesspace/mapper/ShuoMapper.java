package com.eyes.eyesspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesspace.model.dto.ShuoInfoDTO;
import com.eyes.eyesspace.model.entity.Shuoshuo;
import com.eyes.eyesspace.model.request.ShuoAddRequest;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ShuoMapper extends BaseMapper<Shuoshuo> {
	@Insert("insert into shuoshuo (content, status, create_time) values (#{content}, #{status}, now())")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	Boolean addShuo(ShuoAddRequest shuoAddRequest);

	Integer addShuoPics(List<String> picList, Integer id);

	Integer getShuoListInfo(Integer status);

	@Select("select count(*) from shuoshuo where ${statusCondition}")
	Integer getShuoTotalNum(String statusCondition);

	@Select("select id, content, status, create_time from shuoshuo where ${statusCondition} order by id desc limit #{start}, #{pageSize}")
	List<ShuoInfoDTO> getShuoList(Integer start, Integer pageSize, String statusCondition);

	@Select("select url from shuoshuo_pic where shuoshuo_id=#{id}")
	List<String> getShuoPics(Integer id);

	@Select("<script>"
			+ "select id, content, status, create_time "
			+ "from shuoshuo "
			+ "where id in "
			+ "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ "</script>")
	List<ShuoInfoDTO> getShuoListByIds(List<Integer> ids);
}
