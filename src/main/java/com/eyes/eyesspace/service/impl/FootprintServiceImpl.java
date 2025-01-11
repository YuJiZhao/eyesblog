package com.eyes.eyesspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.mapper.FootprintMapper;
import com.eyes.eyesspace.model.entity.Footprint;
import com.eyes.eyesspace.model.po.FootprintContentPO;
import com.eyes.eyesspace.model.vo.FootprintContentListVO;
import com.eyes.eyesspace.model.vo.FootprintInfoVO;
import com.eyes.eyesspace.model.vo.FootprintListInfoVO;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.service.IFootprintService;
import com.eyes.eyesspace.utils.AuthUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author eyesYeager
 * data 2024/12/28 17:48
 */

@Service
public class FootprintServiceImpl extends ServiceImpl<FootprintMapper, Footprint> implements IFootprintService {

	private static final int FOOTPRINT_CONTENT_PAGE_SIZE = 6;

	@Resource
	private FootprintMapper footprintMapper;

	@Override
	public List<FootprintInfoVO> getFootprintList() {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		return footprintMapper.getFootprintList(statusCondition);
	}

	@Override
	public FootprintListInfoVO getFootprintListInfo() {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		return footprintMapper.getFootprintListInfo(statusCondition);
	}

	@Override
	public FootprintContentListVO getFootprintContentList(Integer id, Integer page) {
		FootprintContentListVO result = new FootprintContentListVO();
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		FootprintInfoVO footprintInfo = footprintMapper.getFootprintInfo(id, statusCondition);
		result.setCountry(footprintInfo.getCountry());
		result.setProvince(footprintInfo.getProvince());
		result.setCity(footprintInfo.getCity());
		List<FootprintContentPO> data =  footprintMapper.getFootprintContentList(id, (page - 1) * FOOTPRINT_CONTENT_PAGE_SIZE, FOOTPRINT_CONTENT_PAGE_SIZE, statusCondition);
		PageBind<FootprintContentPO> bind = new PageBind<>(page, footprintMapper.getFootprintContentNum(id, statusCondition), data);
		result.setData(bind);
		return result;
	}
}
