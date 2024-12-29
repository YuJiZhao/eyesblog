package com.eyes.eyesspace.service;

import com.eyes.eyesspace.model.vo.FootprintContentListVO;
import com.eyes.eyesspace.model.vo.FootprintInfoVO;
import com.eyes.eyesspace.model.vo.FootprintListInfoVO;

import java.util.List;

/**
 * @author eyesYeager
 * data 2024/12/28 17:46
 */

public interface FootprintService {
	List<FootprintInfoVO> getFootprintList();

	FootprintListInfoVO getFootprintListInfo();

	FootprintContentListVO getFootprintContentList(Integer id, Integer page);
}
