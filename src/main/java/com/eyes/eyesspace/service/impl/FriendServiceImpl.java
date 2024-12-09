package com.eyes.eyesspace.service.impl;

import com.eyes.eyesspace.constant.FriendChainConstant;
import com.eyes.eyesspace.mapper.ContextMapper;
import com.eyes.eyesspace.mapper.FriendMapper;
import com.eyes.eyesspace.model.po.ContextPO;
import com.eyes.eyesspace.model.dto.FriendListDTO;
import com.eyes.eyesspace.model.vo.FriendListInfoVO;
import com.eyes.eyesspace.model.vo.FriendPreambleVO;
import com.eyes.eyesspace.service.FriendService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author eyesYeager
 * date 2023/6/1 9:20
 */

@RefreshScope
@Service
@Slf4j
public class FriendServiceImpl implements FriendService {

	private static final List<Integer> PREAMBLE_ID = Collections.singletonList(10);

	@Resource
	private FriendMapper friendMapper;

	@Resource
	private ContextMapper contextMapper;

	@Override
	public FriendListInfoVO getFriendListData() {
		List<Integer> statusList = friendMapper.getFriendStatusList();
		Map<Integer, Long> statusMap = statusList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		return new FriendListInfoVO(
				statusMap.getOrDefault(FriendChainConstant.NORMAL, 0L),
				statusMap.getOrDefault(FriendChainConstant.INVALID, 0L) + statusMap.getOrDefault(FriendChainConstant.STAGING, 0L),
				statusMap.getOrDefault(FriendChainConstant.VERIFYING, 0L)
		);
	}

	@Override
	public List<FriendListDTO> getFriendList() {
		List<Integer> statusList = Arrays.asList(FriendChainConstant.NORMAL, FriendChainConstant.INVALID, FriendChainConstant.STAGING);
		return friendMapper.getFriendList(statusList);
	}

	@Override
	public FriendPreambleVO getFriendPreamble() {
		List<ContextPO> context = contextMapper.getContext(PREAMBLE_ID);
		return new FriendPreambleVO(context.get(0).getValue());
	}
}
