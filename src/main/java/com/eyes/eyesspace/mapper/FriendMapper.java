package com.eyes.eyesspace.mapper;

import com.eyes.eyesspace.model.dto.FriendListDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author eyesYeager
 * date 2023/6/1 9:25
 */

@Mapper
public interface FriendMapper {
	@Select("select status from friend")
	List<Integer> getFriendStatusList();

	@Select("<script>"
			+ "select id, name, introduce, avatar, address, status from friend where status in "
			+ "<foreach collection='statusList' item='item' index='index' open='(' separator=',' close=')'>"
			+ "#{item}"
			+ "</foreach> "
			+ "</script>")
	List<FriendListDTO> getFriendList(List<Integer> statusList);
}
