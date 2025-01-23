package com.eyes.eyesspace.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesAuth.constant.AuthConfigConstant;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.exception.BizException;
import com.eyes.eyesspace.mapper.ShuoPicMapper;
import com.eyes.eyesspace.model.entity.ShuoshuoPic;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.constant.MediaConstant;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.mapper.ShuoMapper;
import com.eyes.eyesspace.model.entity.Shuoshuo;
import com.eyes.eyesspace.model.request.ShuoAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.ShuoListInfoVO;
import com.eyes.eyesspace.model.vo.ShuoListVO;
import com.eyes.eyesspace.service.IShuoService;
import com.eyes.eyesspace.utils.AuthUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.eyes.eyesspace.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
@RefreshScope
public class ShuoServiceImpl extends ServiceImpl<ShuoMapper, Shuoshuo> implements IShuoService {

	private static final int SHUO_PAGE_SIZE = 6;

	@Value("${path.folder.shuo}")
	private String shuoPath;

	@Resource
	private ShuoMapper shuoMapper;

	@Resource
	private ShuoPicMapper shuoPicMapper;

	@Resource
	private FileUtils fileUtils;

	@Override
	@Transactional
	public void addShuo(ShuoAddRequest shuoAddRequest) throws BizException {
		if (!shuoMapper.addShuo(shuoAddRequest)) {
			throw new BizException("插入说说失败！");
		}

		if (!CollectionUtils.isEmpty(shuoAddRequest.getPicList())) {
			Integer insertShuoshuoPics = shuoMapper.addShuoPics(shuoAddRequest.getPicList(), shuoAddRequest.getId());
			if (!insertShuoshuoPics.equals(shuoAddRequest.getPicList().size())) {
				throw new BizException("插入说说图片失败！");
			}
		}
	}

	@Override
	public FileUploadVO uploadShuoPic(MultipartFile multipartFile) throws BizException {
		String originalFilename = multipartFile.getOriginalFilename();
		if (Objects.isNull(originalFilename)) {
			throw new BizException("文件错误");
		}
		String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
		if (!MediaConstant.imgContain(fileType)) {
			throw new BizException("图片格式不支持");
		}

		String url = fileUtils.putObject(multipartFile, shuoPath);
		return new FileUploadVO(url);
	}

	@Override
	public ShuoListInfoVO getShuoListInfo() {
		String role = UserInfoHolder.getRole();
		Long shuoNum = shuoMapper.selectCount(Wrappers.<Shuoshuo>lambdaQuery()
				.ne(AuthConfigConstant.ROLE_ADMIN.equals(role), Shuoshuo::getStatus, StatusEnum.DELETE.getStatus())
				.eq(!AuthConfigConstant.ROLE_ADMIN.equals(role), Shuoshuo::getStatus, StatusEnum.PUBLIC.getStatus())
		);
		String statusCondition = AuthUtils.statusSqlCondition("s.status", role);
		Long shuoPicNum = shuoMapper.getShuoPicNum(statusCondition);
		return new ShuoListInfoVO(shuoNum, shuoPicNum);
	}

	@Override
	@Transactional
	public PageBind<ShuoListVO> getShuoList(Integer page) {
		String role = UserInfoHolder.getRole();
		// 获取说说信息
		Page<Shuoshuo> shuoPage = new Page<>(page, SHUO_PAGE_SIZE);
		IPage<Shuoshuo> shuoshuoIPage = shuoMapper.selectPage(shuoPage, Wrappers.<Shuoshuo>lambdaQuery()
				.ne(AuthConfigConstant.ROLE_ADMIN.equals(role), Shuoshuo::getStatus, StatusEnum.DELETE.getStatus())
				.eq(!AuthConfigConstant.ROLE_ADMIN.equals(role), Shuoshuo::getStatus, StatusEnum.PUBLIC.getStatus())
				.orderByDesc(Shuoshuo::getCreateTime)
		);
		// 获取说说图片信息
		List<Shuoshuo> records = shuoshuoIPage.getRecords();
		List<Integer> shuoIdList = records.stream().map(Shuoshuo::getId).collect(Collectors.toList());
		Map<Integer, List<ShuoshuoPic>> shuoPicListByIdMap = shuoPicMapper.selectList(Wrappers.<ShuoshuoPic>lambdaQuery()
				.select(ShuoshuoPic::getShuoshuoId, ShuoshuoPic::getUrl)
				.in(!CollectionUtils.isEmpty(shuoIdList), ShuoshuoPic::getShuoshuoId, shuoIdList)
		).stream().collect(Collectors.groupingBy(ShuoshuoPic::getShuoshuoId));
		// 组装说说数据
		List<ShuoListVO> shuoListVOs = new ArrayList<>();
		for (Shuoshuo item : records) {
			ShuoListVO shuoListVO = new ShuoListVO();
			shuoListVO.setContent(item.getContent());
			List<ShuoshuoPic> shuoPicList = shuoPicListByIdMap.getOrDefault(item.getId(), new ArrayList<>());
			shuoListVO.setPicList(shuoPicList.stream().map(ShuoshuoPic::getUrl).collect(Collectors.toList()));
			shuoListVO.setCreateTime(item.getCreateTime());
			shuoListVOs.add(shuoListVO);
		}
		return new PageBind<>(page, shuoshuoIPage.getTotal(), shuoListVOs);
	}
}
