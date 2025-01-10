package com.eyes.eyesspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesspace.model.entity.Anime;
import com.eyes.eyesspace.model.vo.AnimeListVO;
import com.eyes.eyesspace.model.vo.AnimeInfoVO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author eyesYeager
 * date 2023/5/22 14:51
 */
@Mapper
public interface AnimeMapper extends BaseMapper<Anime> {
	@Select("select status from anime")
	List<Integer> getAnimeStatusList();

	@Select("select SUM(view) from anime where ${statusCondition}")
	Integer getAnimeViewNum(String statusCondition);

	@Select("select COUNT(*) from anime where ${statusCondition}")
	Integer getAnimeNum(String statusCondition);

	@Select("select id, title, type, period, introduce, cover, view, create_time from anime where ${statusCondition} order by create_time desc limit #{start}, #{pageSize}")
	List<AnimeListVO> getAnimeList(int start, int pageSize, String statusCondition);

	@Select("select title, type, period, introduce, word, cover, view, create_time from anime where id=#{id} and ${statusCondition}")
	AnimeInfoVO getAnimeInfo(Integer id, String statusCondition);

	@Update("update anime set view=view+1 where id=#{id}")
	Boolean addView(Integer id);

	@Select("<script>"
			+ "select id, title, type, period, introduce, cover, view, create_time from anime "
			+ "where id in "
			+ "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ "</script>")
	List<AnimeListVO> getAnimeListByIds(List<Integer> ids);
}
