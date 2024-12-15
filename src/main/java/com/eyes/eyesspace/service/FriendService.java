package com.eyes.eyesspace.service;

import com.eyes.eyesspace.model.dto.FriendListDTO;
import com.eyes.eyesspace.model.vo.FriendListInfoVO;

import java.util.List;

/**
 * @author eyesYeager
 * date 2023/6/1 9:20
 */

public interface FriendService {
	FriendListInfoVO getFriendListData();

	List<FriendListDTO> getFriendList();
}
