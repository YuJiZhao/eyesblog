package com.eyes.eyesspace;

import cn.hutool.core.collection.CollectionUtil;
import com.eyes.eyesspace.persistent.entity.LogTrackEntity;
import com.eyes.eyesspace.persistent.entity.LogVisitEntity;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 合并log_visit表与log_joke表数据至log_track
 *
 * @author artonyu
 * date 2024-08-06 17:00
 */

@SpringBootTest
public class MergeLogTrackScript {
	@Resource
	private TrackMapper trackMapper;

	/**
	 * log_visit表迁移
	 */
	@Test
	public void doVisitScript() {
		int index = 1;
		final int size = 100;
		int total = 0;
		while (true) {
			System.out.printf("---------- 开始迁移 log_visit 数据，index: %d, size: %d-----------%n", index, size);

			// 获取待迁移数据
			List<LogVisitEntity> logVisitList = trackMapper.getLogVisit((index - 1) * size, size);
			if (CollectionUtil.isEmpty(logVisitList)) {
				break;
			}
			// 构建新数据
			List<LogTrackEntity> logTrackEntityList = logVisitList.stream().map(v -> {
				// 待定
				return new LogTrackEntity();
			}).collect(Collectors.toList());
			// 插入
			if (!trackMapper.addTrackLog(logTrackEntityList)) {
				System.out.printf("---------- 迁移失败，index：%d-----------%n", index);
				return;
			}

			index++;
			total += logVisitList.size();
			System.out.printf("---------- 阶段性迁移成功！-----------%n");
		}
		System.out.printf("---------- log_visit 迁移完成，共迁移 %d 行数据-----------", total);
	}

	/**
	 * log_joke表迁移
	 */
	@Test
	public void doJokeScript() {
	}
}
