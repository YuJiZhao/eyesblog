package com.eyes.eyesspace.sync.service;

import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.sync.model.vo.UserInfoVO;

/**
 * @author eyesYeager
 * date 2023/2/9 15:48
 */

public interface UserService {
	UserInfoVO getUserInfo() throws CustomException;
}
