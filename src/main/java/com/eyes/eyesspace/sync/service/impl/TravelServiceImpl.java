package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.constant.FileMethodEnum;
import com.eyes.eyesspace.constant.FileOperationLogConstant;
import com.eyes.eyesspace.constant.MediaConstant;
import com.eyes.eyesspace.persistent.mapper.ContextMapper;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.persistent.mapper.TravelMapper;
import com.eyes.eyesspace.persistent.po.ContextPO;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.TravelNoticeVO;
import com.eyes.eyesspace.sync.service.CommentService;
import com.eyes.eyesspace.sync.service.TravelService;
import com.eyes.eyesspace.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author artonyu
 * date 2024-08-01 11:44
 */

@Slf4j
@RefreshScope
@Service
public class TravelServiceImpl implements TravelService {
	private static final List<Integer> TRAVEL_NOTICE_ID = Collections.singletonList(9);

	@Value("${path.url.site}")
	private String siteUrl;

	@Value("${path.folder.travel}")
	private String travelPath;

	@Value("${path.url.travel-details}")
	private String travelDetailsPath;

	@Value("${business.notice-switch.travel:false}")
	private Boolean travelNoticeSwitch;

	@Resource
	private TrackMapper trackMapper;

	@Resource
	private ContextMapper contextMapper;

	@Resource
	private TravelMapper travelMapper;

	@Resource
	private CommentService commentService;

	@Resource
	private FileUtils fileUtils;

	@Override
	public FileUploadVO uploadTravelPic(MultipartFile multipartFile) throws CustomException {
		String originalFilename = multipartFile.getOriginalFilename();
		if (Objects.isNull(originalFilename)) {
			throw new CustomException("文件错误");
		}
		String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
		if (!MediaConstant.imgContain(fileType)) {
			throw new CustomException("图片格式不支持");
		}

		String url = fileUtils.putObject(multipartFile, travelPath);
		// 记录文件上传日志
		if (!trackMapper.addFileLog(
				FileOperationLogConstant.TRAVEL,
				UserInfoHolder.getUid(),
				FileMethodEnum.UPLOAD.getMethod(),
				url)) {
			log.error("Failed to record travel pic upload log. "
					+ "type: " + FileOperationLogConstant.TRAVEL
					+ "url: " + url);
		}

		return new FileUploadVO(url);
	}

	@Override
	public TravelNoticeVO getTravelNotice() {
		List<ContextPO> context = contextMapper.getContext(TRAVEL_NOTICE_ID);
		return new TravelNoticeVO(context.get(0).getValue());
	}

	@Override
	public void doTravelComment(CommentAddRequest commentAddRequest) throws CustomException {
//        // 校验可行性
//        String role = UserInfoHolder.getRole();
//        String statusCondition = AuthUtils.statusSqlCondition(role);
//        TravelInfoVO travelInfoVO = travelMapper.getTravelInfo(commentAddRequest.getObjectId(), statusCondition);
//        if (Objects.isNull(travelInfoVO)) {
//            throw new CustomException("该旅行不存在");
//        }
//
//        // 执行评论业务
//        Long uid = UserInfoHolder.getUid();
//        commentAddRequest.setUid(uid);
//        commentAddRequest.setUrl(siteUrl + travelDetailsPath + commentAddRequest.getObjectId());
//        commentService.publishComment(commentAddRequest, CommentEnum.TRAVEL.getType(), travelNoticeSwitch);
//        if (!travelMapper.updateTravelComments(commentAddRequest.getObjectId(), 1)) {
//            throw new CustomException("评论数据更新失败");
//        }
	}

	@Override
	public List<CommentListVO> getTravelCommentList(Integer id, Integer page, Integer pageSize) throws CustomException {
//        // 校验可行性
//        String role = UserInfoHolder.getRole();
//        String statusCondition = AuthUtils.statusSqlCondition(role);
//        TravelInfoVO travelInfoVO = travelMapper.getTravelInfo(id, statusCondition);
//        if (Objects.isNull(travelInfoVO)) {
//            throw new CustomException("该旅行不存在");
//        }
//
//        // 执行业务
//        Long uid = UserInfoHolder.getUid();
//        return commentService.getCommentList(id, CommentEnum.TRAVEL.getType(), uid, page, pageSize);
		return null;
	}

	@Override
	public void delTravelComment(Integer id) throws CustomException {
//        String role = UserInfoHolder.getRole();
//        // 可行性检查
//        CommentDelInfoPO commentDelInfoPo = travelMapper.getTravelCommentInfo(id);
//        if (
//                Objects.isNull(commentDelInfoPo) ||
//                        !CommentEnum.ANIME.getType().equals(commentDelInfoPo.getType()) ||
//                        (AuthConfigConstant.ROLE_USER.equals(role) && !StatusEnum.PUBLIC.getStatus().equals(commentDelInfoPo.getStatus())) ||
//                        (AuthConfigConstant.ROLE_ADMIN.equals(role) && StatusEnum.DELETE.getStatus().equals(commentDelInfoPo.getStatus()))
//        ) { throw new CustomException("该旅行不存在"); }
//
//        // 执行业务
//        Long uid = UserInfoHolder.getUid();
//        commentService.delComment(id, uid);
//        if (!travelMapper.updateTravelComments(commentDelInfoPo.getObjectId(), -1)) {
//            throw new CustomException("评论数据更新失败");
//        }
	}
}
