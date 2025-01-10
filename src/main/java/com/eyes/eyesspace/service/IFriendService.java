package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.model.dto.FriendListDTO;
import com.eyes.eyesspace.model.entity.Friend;
import com.eyes.eyesspace.model.vo.FriendListInfoVO;

import java.util.List;

/**
 * @author eyesYeager
 * date 2023/6/1 9:20
 */

public interface IFriendService extends IService<Friend> {
	FriendListInfoVO getFriendListData();

	List<FriendListDTO> getFriendList();
}
