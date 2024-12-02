package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesspace.sync.common.exception.CustomException;
import com.eyes.eyesspace.persistent.mapper.BookMapper;
import com.eyes.eyesspace.persistent.mapper.ContextMapper;
import com.eyes.eyesspace.persistent.po.ContextPO;
import com.eyes.eyesspace.sync.model.dto.BookListDTO;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.*;
import com.eyes.eyesspace.sync.service.BookService;
import com.eyes.eyesspace.sync.service.CommentService;
import com.eyes.eyesspace.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author artonyu
 * date 2024-08-01 09:38
 */

@Slf4j
@Service
@RefreshScope
public class BookServiceImpl implements BookService {

	private static final List<Integer> BOOK_NOTICE_ID = Collections.singletonList(10);

	@Value("${path.url.site}")
	private String siteUrl;

	@Value("${path.url.book-details}")
	private String bookDetailsPath;

	@Value("${business.notice-switch.book:false}")
	private Boolean bookNoticeSwitch;

	@Resource
	private ContextMapper contextMapper;

	@Resource
	private BookMapper bookMapper;

	@Resource
	private CommentService commentService;

	@Resource
	private FileUtils fileUtils;

	@Override
	public BookNoticeVO getBookNotice() {
		List<ContextPO> context = contextMapper.getContext(BOOK_NOTICE_ID);
		return new BookNoticeVO(context.get(0).getValue());
	}

	@Override
	public BookListInfoVO getBookListInfo() {
		return null;
	}

	@Override
	public BookListVO getBookList(Integer page, Integer pageSize) {
		return null;
	}

	@Override
	public BookInfoVO getBookInfo(Integer id) throws CustomException {
		return null;
	}

	@Override
	public List<BookListDTO> getBookListByIds(List<Integer> ids) {
		return null;
	}

	@Override
	public void doBookComment(CommentAddRequest commentAddRequest) throws CustomException {
//        // 校验可行性
//        String role = UserInfoHolder.getRole();
//        String statusCondition = AuthUtils.statusSqlCondition(role);
//        BookInfoVO bookInfoVO = bookMapper.getBookInfo(commentAddRequest.getObjectId(), statusCondition);
//        if (Objects.isNull(bookInfoVO)) {
//            throw new CustomException("小说不存在");
//        }
//
//        // 执行评论业务
//        Long uid = UserInfoHolder.getUid();
//        commentAddRequest.setUid(uid);
//        commentAddRequest.setUrl(siteUrl + bookDetailsPath + commentAddRequest.getObjectId());
//        commentService.publishComment(commentAddRequest, CommentEnum.BOOK.getType(), bookNoticeSwitch);
//        if (!bookMapper.updateBookComments(commentAddRequest.getObjectId(), 1)) {
//            throw new CustomException("评论数据更新失败");
//        }
	}

	@Override
	public List<CommentListVO> getBookCommentList(Integer id, Integer page, Integer pageSize) throws CustomException {
//        // 校验可行性
//        String role = UserInfoHolder.getRole();
//        String statusCondition = AuthUtils.statusSqlCondition(role);
//        BookInfoVO bookInfoVO = bookMapper.getBookInfo(id, statusCondition);
//        if (Objects.isNull(bookInfoVO)) {
//            throw new CustomException("小说不存在");
//        }
//
//        // 执行业务
//        Long uid = UserInfoHolder.getUid();
//        return commentService.getCommentList(id, CommentEnum.BOOK.getType(), uid, page, pageSize);
		return null;
	}

	@Override
	public void delBookComment(Integer id) throws CustomException {
//        String role = UserInfoHolder.getRole();
//        // 可行性检查
//        CommentDelInfoPO commentDelInfoPo = bookMapper.getBookCommentInfo(id);
//        if (
//                Objects.isNull(commentDelInfoPo) ||
//                        !CommentEnum.BOOK.getType().equals(commentDelInfoPo.getType()) ||
//                        (AuthConfigConstant.ROLE_USER.equals(role) && !StatusEnum.PUBLIC.getStatus().equals(commentDelInfoPo.getStatus())) ||
//                        (AuthConfigConstant.ROLE_ADMIN.equals(role) && StatusEnum.DELETE.getStatus().equals(commentDelInfoPo.getStatus()))
//        ) { throw new CustomException("动漫不存在"); }
//
//        // 执行业务
//        Long uid = UserInfoHolder.getUid();
//        commentService.delComment(id, uid);
//        if (!bookMapper.updateBookComments(commentDelInfoPo.getObjectId(), -1)) {
//            throw new CustomException("评论数据更新失败");
//        }
	}
}
