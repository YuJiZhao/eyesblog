package com.eyes.eyesspace.controller;

import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesspace.common.result.Result;
import com.eyes.eyesspace.model.vo.FootprintContentListVO;
import com.eyes.eyesspace.model.vo.FootprintInfoVO;
import com.eyes.eyesspace.model.vo.FootprintListInfoVO;
import com.eyes.eyesspace.service.IFootprintService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author eyesYeager
 * data 2024/12/28 17:43
 */

@Validated
@RestController
@RequestMapping("/footprint")
public class FootprintController {

	@Resource
	private IFootprintService footprintService;

	@Permission
	@GetMapping("/getFootprintList")
	public Result<List<FootprintInfoVO>> getFootprintList() {
		return Result.success(footprintService.getFootprintList());
	}

	@Permission
	@GetMapping("/getFootprintListInfo")
	public Result<FootprintListInfoVO> getFootprintListInfo() {
		return Result.success(footprintService.getFootprintListInfo());
	}

	@Permission
	@GetMapping("/getFootprintContentList")
	public Result<FootprintContentListVO> getFootprintContentList(Integer id, Integer page) {
		return Result.success(footprintService.getFootprintContentList(id, page));
	}
}
