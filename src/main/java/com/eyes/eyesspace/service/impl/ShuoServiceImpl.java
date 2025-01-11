package com.eyes.eyesspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyes.eyesAuth.constant.AuthConfigConstant;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.exception.CustomException;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.constant.HomeTypeEnum;
import com.eyes.eyesspace.constant.MediaConstant;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.mapper.HomeMapper;
import com.eyes.eyesspace.mapper.ShuoMapper;
import com.eyes.eyesspace.model.dto.ShuoInfoDTO;
import com.eyes.eyesspace.model.dto.ShuoListDTO;
import com.eyes.eyesspace.model.entity.Shuoshuo;
import com.eyes.eyesspace.model.request.ShuoAddRequest;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.model.vo.ShuoListInfoVO;
import com.eyes.eyesspace.model.vo.ShuoListVO;
import com.eyes.eyesspace.service.IShuoService;
import com.eyes.eyesspace.utils.AuthUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.eyes.eyesspace.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
	private HomeMapper homeMapper;

	@Resource
	private FileUtils fileUtils;

	@Override
	@Transactional
	public void addShuo(ShuoAddRequest shuoAddRequest) throws CustomException {
		if (!shuoMapper.addShuo(shuoAddRequest)) {
			throw new CustomException("插入说说失败！");
		}

		if (!CollectionUtils.isEmpty(shuoAddRequest.getPicList())) {
			Integer insertShuoshuoPics = shuoMapper.addShuoPics(shuoAddRequest.getPicList(), shuoAddRequest.getId());
			if (!insertShuoshuoPics.equals(shuoAddRequest.getPicList().size())) {
				throw new CustomException("插入说说图片失败！");
			}
		}

		if (!homeMapper.insertHome(
				HomeTypeEnum.SHUOSHUO.getType(),
				shuoAddRequest.getId(),
				shuoAddRequest.getStatus())
		) {
			throw new CustomException("说说插入home失败！");
		}
	}

	@Override
	public FileUploadVO uploadShuoPic(MultipartFile multipartFile) throws CustomException {
		String originalFilename = multipartFile.getOriginalFilename();
		if (Objects.isNull(originalFilename)) {
			throw new CustomException("文件错误");
		}
		String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
		if (!MediaConstant.imgContain(fileType)) {
			throw new CustomException("图片格式不支持");
		}

		String url = fileUtils.putObject(multipartFile, shuoPath);
		return new FileUploadVO(url);
	}

	@Override
	public ShuoListInfoVO getShuoListInfo() {
		String role = UserInfoHolder.getRole();
		if (AuthConfigConstant.ROLE_ADMIN.equals(role)) {
			return new ShuoListInfoVO(
					shuoMapper.getShuoListInfo(null),
					shuoMapper.getShuoListInfo(StatusEnum.PUBLIC.getStatus()),
					shuoMapper.getShuoListInfo(StatusEnum.PRIVATE.getStatus()),
					shuoMapper.getShuoListInfo(StatusEnum.DELETE.getStatus())
			);
		}
		return new ShuoListInfoVO(shuoMapper.getShuoListInfo(StatusEnum.PUBLIC.getStatus()));
	}

	@Override
	@Transactional
	public PageBind<ShuoListVO> getShuoList(Integer page) throws CustomException {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		List<ShuoInfoDTO> shuoInfoDTOs = shuoMapper.getShuoList((page - 1) * SHUO_PAGE_SIZE, SHUO_PAGE_SIZE, statusCondition);
		List<ShuoListVO> shuoListVOs = new ArrayList<>();
		for (ShuoInfoDTO item : shuoInfoDTOs) {
			item.setPicList(shuoMapper.getShuoPics(item.getId()));
			ShuoListVO shuoListVO = new ShuoListVO();
			BeanUtils.copyProperties(item, shuoListVO);
			shuoListVOs.add(shuoListVO);
		}
		return new PageBind<>(page, shuoMapper.getShuoTotalNum(statusCondition), shuoListVOs);
	}

	@Override
	public List<ShuoListDTO> getShuoListByIds(List<Integer> ids) {
		List<ShuoInfoDTO> shuoList = shuoMapper.getShuoListByIds(ids);
		return shuoList.stream().map(item -> {
			ShuoListDTO shuoListDTO = new ShuoListDTO();
			BeanUtils.copyProperties(item, shuoListDTO);
			shuoListDTO.setPicList(shuoMapper.getShuoPics(item.getId()));
			return shuoListDTO;
		}).collect(Collectors.toList());
	}
}
