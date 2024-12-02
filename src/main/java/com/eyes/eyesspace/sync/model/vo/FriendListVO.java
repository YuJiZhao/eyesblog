package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.sync.model.dto.FriendListDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eyesYeager
 * date 2023/6/1 11:52
 */

@Data
@AllArgsConstructor
public class FriendListVO {
	private Integer total;

	private List<FriendListDTO> data;
}
