package com.eyes.eyesspace.service.impl;

import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesAuth.thrift.TTClientPool;
import com.eyes.eyesAuth.thrift.config.TTSocket;
import com.eyes.eyesAuth.thrift.generate.common.TTCustomException;
import com.eyes.eyesAuth.thrift.generate.user.UserInfoReturnee;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.result.ResultCode;
import com.eyes.eyesspace.model.vo.UserInfoVO;
import com.eyes.eyesspace.service.UserService;
import com.eyes.eyesspace.utils.SensitiveInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 * date 2023/2/9 15:48
 */
@Slf4j
@RefreshScope
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private TTClientPool ttClientPool;

	@Override
	public UserInfoVO getUserInfo() throws BizException {
		Long uid = UserInfoHolder.getUid();
		TTSocket ttSocket = null;
		try {
			ttSocket = ttClientPool.getConnect();
			UserInfoReturnee userInfo = ttSocket.getUserClient().getUserInfo(uid);
			ttClientPool.returnConnection(ttSocket);
			UserInfoVO userInfoVO = new UserInfoVO();
			BeanUtils.copyProperties(userInfo, userInfoVO);
			userInfoVO.setEmail(SensitiveInfoUtils.email(userInfoVO.getEmail()));
			return userInfoVO;
		} catch (TTCustomException e) {
			throw new BizException(e.getMsg());
		} catch (Exception e) {
			ttClientPool.invalidateObject(ttSocket);
			log.error("unknown error:", e);
			throw new BizException(ResultCode.FAILURE);
		}
	}
}
