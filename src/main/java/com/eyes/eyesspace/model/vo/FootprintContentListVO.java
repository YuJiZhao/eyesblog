package com.eyes.eyesspace.model.vo;

import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.model.po.FootprintContentPO;
import lombok.Data;

import java.util.List;

/**
 * @author eyesYeager
 * data 2024/12/29 15:48
 */

@Data
public class FootprintContentListVO {
	private String country;

	private String province;

	private String city;

	private PageBind<FootprintContentPO> data;
}
