package com.eyes.eyesspace.sync.service.impl;

import com.eyes.eyesAuth.constant.AuthConfigConstant;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.service.file.FileUtils;
import com.eyes.eyesTools.utils.PageBind;
import com.eyes.eyesspace.constant.CommentEnum;
import com.eyes.eyesspace.constant.FileMethodEnum;
import com.eyes.eyesspace.constant.FileOperationLogConstant;
import com.eyes.eyesspace.constant.HomeTypeEnum;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.persistent.dto.BlogCategoryDTO;
import com.eyes.eyesspace.persistent.dto.BlogInfoDTO;
import com.eyes.eyesspace.persistent.dto.BlogLabelDTO;
import com.eyes.eyesspace.persistent.dto.BlogListDTO;
import com.eyes.eyesspace.persistent.mapper.BlogMapper;
import com.eyes.eyesspace.persistent.mapper.HomeMapper;
import com.eyes.eyesspace.persistent.mapper.TrackMapper;
import com.eyes.eyesspace.persistent.po.BlogAddCategoryPO;
import com.eyes.eyesspace.persistent.po.BlogAddLabelPO;
import com.eyes.eyesspace.persistent.po.BlogDataPO;
import com.eyes.eyesspace.persistent.po.CommentDelInfoPO;
import com.eyes.eyesspace.sync.convert.BlogConvert;
import com.eyes.eyesspace.sync.model.bo.BlogAddBO;
import com.eyes.eyesspace.sync.model.request.BlogAddRequest;
import com.eyes.eyesspace.sync.model.request.CommentAddRequest;
import com.eyes.eyesspace.sync.model.vo.BlogAddVO;
import com.eyes.eyesspace.sync.model.vo.BlogListInfoVO;
import com.eyes.eyesspace.sync.model.vo.CommentListVO;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.service.BlogService;
import com.eyes.eyesspace.sync.service.CommentService;
import com.eyes.eyesspace.utils.AuthUtils;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RefreshScope
@Service
public class BlogServiceImpl implements BlogService {
    // 展示最大标签数
    private static final Integer LABEL_MAX_NUM = 20;

    @Value("${path.url.site}")
    private String siteUrl;

    @Value("${path.folder.blog}")
    private String blogPath;

    @Value("${path.url.blog-details}")
    private String blogDetailsPath;

    @Value("${business.notice-switch.blog:false}")
    private Boolean blogNoticeSwitch;

    private final BlogMapper blogMapper;

    private final TrackMapper trackMapper;

    private final HomeMapper homeMapper;

    private final CommentService commentService;

    public BlogServiceImpl(BlogMapper blogMapper, TrackMapper trackMapper, HomeMapper homeMapper, CommentService commentService) {
        this.blogMapper = blogMapper;
        this.trackMapper = trackMapper;
        this.homeMapper = homeMapper;
        this.commentService = commentService;
    }

    @Override
    @Transactional
    public BlogAddVO addBlog(BlogAddRequest blogAddRequest) throws CustomException {
        BlogAddBO blogAddBo = BlogConvert.INSTANCE.BlogAddVo2Bo(blogAddRequest);

        // 获得博客分类
        Integer categoryIndex = blogMapper.getCategoryIdByName(blogAddRequest.getCategory());
        if(Objects.nonNull(categoryIndex)) {
            blogAddBo.setCategory(categoryIndex);
        } else {
            BlogAddCategoryPO blogAddCategoryPO = new BlogAddCategoryPO();
            blogAddCategoryPO.setCategory(blogAddRequest.getCategory());
            if(!blogMapper.addCategory(blogAddCategoryPO)) {
                throw new CustomException("新增分类失败！");
            }
            blogAddBo.setCategory(blogAddCategoryPO.getId());
        }

        // 插入博客
        if(!blogMapper.addBlog(blogAddBo)) {
            throw new CustomException("新增博客失败！");
        }

        // 插入标签
        List<String> labels = blogAddBo.getLabels();
        if(!labels.isEmpty()) {
            for(String label: labels) {
                Integer labelIndex = blogMapper.getLabelIdByName(label);
                if(Objects.nonNull(labelIndex)) {
                    if(!blogMapper.addBlogLabelId(blogAddBo.getId(), labelIndex)) {
                        throw new CustomException("博客插入标签 '" + label + "' 失败！");
                    }
                } else {
                    BlogAddLabelPO blogAddLabelPO = new BlogAddLabelPO();
                    blogAddLabelPO.setLabel(label);
                    if(!blogMapper.addBlogLabel(blogAddLabelPO)) {
                        throw new CustomException("新增标签 '" + label + "' 失败！");
                    }
                    if(!blogMapper.addBlogLabelId(blogAddBo.getId(), blogAddLabelPO.getId())) {
                        throw new CustomException("博客插入标签 '" + label + "' 失败！");
                    }
                }
            }
        }

        // 插入home
        if (!homeMapper.insertHome(
            HomeTypeEnum.BLOG.getType(),
            blogAddBo.getId(),
            blogAddBo.getStatus()
        )) { throw new CustomException("插入home失败"); }

        return new BlogAddVO(blogAddBo.getId());
    }

    @Override
    public FileUploadVO addBlogPic(MultipartFile multipartFile) throws CustomException {
        String url = FileUtils.sUpload(multipartFile, blogPath);
        // 记录文件上传日志
        if (!trackMapper.addFileLog(
            FileOperationLogConstant.BOLG,
            UserInfoHolder.getUid(),
            FileMethodEnum.UPLOAD.getMethod(),
            url
        )) { log.error("Failed to record blog image upload log"); }
        return new FileUploadVO(url);
    }

    @Override
    public BlogListInfoVO getBlogListInfo() {
        String role = UserInfoHolder.getRole();
        BlogDataPO blogDataPo = blogMapper.getBlogData(role);

        if (AuthConfigConstant.ROLE_ADMIN.equals(role)) {
            return new BlogListInfoVO(
                blogMapper.getBlogListInfo(null),
                blogMapper.getBlogListInfo(StatusEnum.PUBLIC.getStatus()),
                blogMapper.getBlogListInfo(StatusEnum.PRIVATE.getStatus()),
                blogMapper.getBlogListInfo(StatusEnum.DELETE.getStatus()),
                blogDataPo,
                blogMapper.getBlogListWords(StatusEnum.DELETE.getStatus())
            );
        } else {
            return new BlogListInfoVO(
                blogMapper.getBlogListInfo(0),
                blogDataPo,
                blogMapper.getBlogListWords(null)
            );
        }
    }

    @Override
    public PageBind<BlogListDTO> getBlogList(Integer page, Integer pageSize, String category, String label) {
        String role = UserInfoHolder.getRole();
        String statusCondition = AuthUtils.statusSqlCondition(role);
        List<BlogListDTO> blogList = blogMapper.getBlogList((page - 1) * pageSize, pageSize, statusCondition, category, label);
        if (!AuthConfigConstant.ROLE_ADMIN.equals(role)) {
            blogList.forEach((v) -> v.setStatus(null));
        }
        return new PageBind<>(
            page,
            blogMapper.getBlogListNum(statusCondition, category, label),
            blogList
        );
    }

    @Override
    public List<BlogListDTO> getBlogListByIds(List<Integer> ids) {
        return blogMapper.getBlogListByIds(ids);
    }

    @Override
    public BlogInfoDTO getBlogInfo(Integer id) throws CustomException {
        String role = UserInfoHolder.getRole();
        String statusCondition = AuthUtils.statusSqlCondition(role);
        BlogInfoDTO blogInfoDto = blogMapper.getBlogInfo(id, statusCondition);
        if (Objects.isNull(blogInfoDto)) {
            throw new CustomException("暂无数据");
        }
        if (!AuthConfigConstant.ROLE_ADMIN.equals(role)) {
            blogInfoDto.setStatus(null);
        }
        blogInfoDto.setLabels(blogMapper.getLabelsById(id));
        if (!blogMapper.addView(id)) {
            log.error("博客阅读量更新失败");
        }
        return blogInfoDto;
    }

    @Override
    public List<BlogCategoryDTO> getBlogCategory() {
        String role = UserInfoHolder.getRole();
        String statusCondition = AuthUtils.statusSqlCondition(role);
        return blogMapper.getBlogCategory(statusCondition);
    }

    @Override
    public List<BlogLabelDTO> getBlogLabel() {
        String role = UserInfoHolder.getRole();
        String statusCondition = AuthUtils.statusSqlCondition(role);
        return blogMapper.getBlogLabel(LABEL_MAX_NUM, statusCondition);
    }

    @Override
    @Transactional
    public void doBlogComment(CommentAddRequest commentAddRequest) throws CustomException {
        // 校验可行性
        String role = UserInfoHolder.getRole();
        String statusCondition = AuthUtils.statusSqlCondition(role);
        BlogInfoDTO blogInfoDTO = blogMapper.getBlogInfo(commentAddRequest.getObjectId(), statusCondition);
        if (Objects.isNull(blogInfoDTO)) {
            throw new CustomException("博客不存在");
        }

        // 执行评论业务
        Long uid = UserInfoHolder.getUid();
        commentAddRequest.setUid(uid);
        commentAddRequest.setUrl(siteUrl + blogDetailsPath + commentAddRequest.getObjectId());
        commentService.publishComment(commentAddRequest, CommentEnum.BLOG.getType(), blogNoticeSwitch);
        if (!blogMapper.updateBlogComments(commentAddRequest.getObjectId(), 1)) {
            throw new CustomException("评论数据更新失败");
        }
    }

    @Override
    public List<CommentListVO> getBlogCommentList(Integer id, Integer page, Integer pageSize) throws CustomException {
        // 校验可行性
        String role = UserInfoHolder.getRole();
        String statusCondition = AuthUtils.statusSqlCondition(role);
        BlogInfoDTO blogInfoDTO = blogMapper.getBlogInfo(id, statusCondition);
        if (Objects.isNull(blogInfoDTO)) {
            throw new CustomException("博客不存在");
        }

        // 执行业务
        Long uid = UserInfoHolder.getUid();
        return commentService.getCommentList(id, CommentEnum.BLOG.getType(), uid, page, pageSize);
    }

    @Override
    public void delBlogComment(Integer id) throws CustomException {
        String role = UserInfoHolder.getRole();
        // 可行性检查
        CommentDelInfoPO commentDelInfoPo = blogMapper.getBlogCommentInfo(id);
        if (
            Objects.isNull(commentDelInfoPo) ||
            !CommentEnum.BLOG.getType().equals(commentDelInfoPo.getType()) ||
            (AuthConfigConstant.ROLE_USER.equals(role) && !StatusEnum.PUBLIC.getStatus().equals(commentDelInfoPo.getStatus())) ||
            (AuthConfigConstant.ROLE_ADMIN.equals(role) && StatusEnum.DELETE.getStatus().equals(commentDelInfoPo.getStatus()))
        ) { throw new CustomException("博客不存在"); }

        // 执行业务
        Long uid = UserInfoHolder.getUid();
        commentService.delComment(id, uid);
        if (!blogMapper.updateBlogComments(commentDelInfoPo.getObjectId(), -1)) {
            throw new CustomException("评论数据更新失败");
        }
    }
}