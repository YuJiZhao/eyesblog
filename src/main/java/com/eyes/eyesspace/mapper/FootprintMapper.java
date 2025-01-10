package com.eyes.eyesspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesspace.model.entity.Footprint;
import com.eyes.eyesspace.model.po.FootprintContentPO;
import com.eyes.eyesspace.model.vo.FootprintInfoVO;
import com.eyes.eyesspace.model.vo.FootprintListInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author eyesYeager
 * data 2024/12/28 17:49
 */

@Mapper
public interface FootprintMapper extends BaseMapper<Footprint> {
	@Select("select id, country, province, city, latitude, longitude from footprint where ${statusCondition}")
	List<FootprintInfoVO> getFootprintList(String statusCondition);

	@Select("select id, country, province, city, latitude, longitude from footprint where id = #{id} and ${statusCondition}")
	FootprintInfoVO getFootprintInfo(Integer id, String statusCondition);

	@Select("select count(distinct country) county, count(distinct province) province, count(distinct city) city " +
			"from footprint where ${statusCondition}")
	FootprintListInfoVO getFootprintListInfo(String statusCondition);

	@Select("select count(*) from footprint_content where footprint_id = #{id} and ${statusCondition}")
	Integer getFootprintContentNum(Integer id, String statusCondition);

	@Select("select id, `type`, content, time_from, time_to from footprint_content " +
			"where footprint_id = #{id} and ${statusCondition} " +
			"order by create_time desc " +
			"limit #{start}, #{pageSize}")
	List<FootprintContentPO> getFootprintContentList(Integer id, int start, int pageSize, String statusCondition);
}
