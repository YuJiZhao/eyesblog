package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.sync.model.dto.VersionListDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/5/20 19:20
 */

@Data
@AllArgsConstructor
public class VersionListVO {
	private Integer total;

	private List<VersionListDTO> data;
}
