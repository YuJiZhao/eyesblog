package com.eyes.eyesspace.persistent.mapper;

import com.eyes.eyesspace.persistent.entity.LogTrackEntity;
import com.eyes.eyesspace.persistent.entity.LogVisitEntity;
import com.eyes.eyesspace.sync.model.request.TrackPointAddRequest;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author eyesYeager
 * date 2023/2/9 15:55
 */

@Mapper
public interface TrackMapper {
	Long getTitleNum(String start, String end, String title);

	Long getFieldDistinctNum(String start, String end, String field);

	@Insert("insert into log_file_operation "
			+ "(type, uid, method, remarks, create_time) "
			+ "values "
			+ "(#{type}, #{uid}, #{method}, #{remarks}, now())")
	Boolean addFileLog(int type, long uid, int method, String remarks);

	@Insert("insert into log_track "
			+ "(uid, browser_id, session_id, title, content, path, ip, os, browser, create_time) "
			+ "values "
			+ "(#{uid}, #{r.browserId}, #{r.sessionId}, #{r.title}, #{r.content}, #{r.path}, #{ipAddr}, #{osName}, #{browserName}, now())")
	Boolean addTrackPoint(Long uid, String ipAddr, String osName, String browserName, TrackPointAddRequest r);

	@Select("select * from log_visit limit #{index}, #{size}")
	List<LogVisitEntity> getLogVisit(Integer index, Integer size);

	@Insert("<script> insert into log_track "
			+ "() "
			+ "values "
			+ "<foreach collection='list' separator=',' item='item'>"
			+ "()"
			+ "</foreach>"
			+ "</script>")
	Boolean addTrackLog(List<LogTrackEntity> entityList);
}
