package com.eyes.eyesspace.service;

import com.eyes.eyesspace.exception.CustomException;
import com.eyes.eyesspace.model.vo.UserInfoVO;

/**
 * @author eyesYeager
 * date 2023/2/9 15:48
 */

public interface UserService {
	UserInfoVO getUserInfo() throws CustomException;
}
