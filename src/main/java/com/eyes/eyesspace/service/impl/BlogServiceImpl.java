package com.eyes.eyesspace.service.impl;

import com.eyes.eyesAuth.constant.AuthConfigConstant;
import com.eyes.eyesAuth.context.UserInfoHolder;
import com.eyes.eyesspace.common.exception.CustomException;
import com.eyes.eyesspace.constant.HomeTypeEnum;
import com.eyes.eyesspace.constant.StatusEnum;
import com.eyes.eyesspace.mapper.BlogMapper;
import com.eyes.eyesspace.mapper.HomeMapper;
import com.eyes.eyesspace.model.dto.BlogCategoryDTO;
import com.eyes.eyesspace.model.dto.BlogInfoDTO;
import com.eyes.eyesspace.model.dto.BlogLabelDTO;
import com.eyes.eyesspace.model.dto.BlogListDTO;
import com.eyes.eyesspace.model.po.BlogAddCategoryPO;
import com.eyes.eyesspace.model.po.BlogAddLabelPO;
import com.eyes.eyesspace.model.po.BlogDataPO;
import com.eyes.eyesspace.common.result.PageBind;
import com.eyes.eyesspace.model.bo.BlogAddBO;
import com.eyes.eyesspace.model.request.BlogAddRequest;
import com.eyes.eyesspace.model.vo.BlogAddVO;
import com.eyes.eyesspace.model.vo.BlogListInfoVO;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import com.eyes.eyesspace.service.BlogService;
import com.eyes.eyesspace.utils.AuthUtils;

import java.util.List;
import java.util.Objects;

import com.eyes.eyesspace.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RefreshScope
@Service
public class BlogServiceImpl implements BlogService {
	// 展示最大标签数
	private static final Integer LABEL_MAX_NUM = 20;

	private static final Integer BLOG_PAGE_SIZE = 10;

	@Value("${path.folder.blog}")
	private String blogPath;

	@Resource
	private BlogMapper blogMapper;

	@Resource
	private HomeMapper homeMapper;

	@Resource
	private FileUtils fileUtils;

	@Override
	@Transactional
	public BlogAddVO addBlog(BlogAddRequest blogAddRequest) throws CustomException {
		BlogAddBO blogAddBo = new BlogAddBO();
		BeanUtils.copyProperties(blogAddRequest, blogAddBo);

		// 获得博客分类
		Integer categoryIndex = blogMapper.getCategoryIdByName(blogAddRequest.getCategory());
		if (Objects.nonNull(categoryIndex)) {
			blogAddBo.setCategory(categoryIndex);
		} else {
			BlogAddCategoryPO blogAddCategoryPO = new BlogAddCategoryPO();
			blogAddCategoryPO.setCategory(blogAddRequest.getCategory());
			if (!blogMapper.addCategory(blogAddCategoryPO)) {
				throw new CustomException("新增分类失败！");
			}
			blogAddBo.setCategory(blogAddCategoryPO.getId());
		}

		// 插入博客
		if (!blogMapper.addBlog(blogAddBo)) {
			throw new CustomException("新增博客失败！");
		}

		// 插入标签
		List<String> labels = blogAddBo.getLabels();
		if (!labels.isEmpty()) {
			for (String label : labels) {
				Integer labelIndex = blogMapper.getLabelIdByName(label);
				if (Objects.nonNull(labelIndex)) {
					if (!blogMapper.addBlogLabelId(blogAddBo.getId(), labelIndex)) {
						throw new CustomException("博客插入标签 '" + label + "' 失败！");
					}
				} else {
					BlogAddLabelPO blogAddLabelPO = new BlogAddLabelPO();
					blogAddLabelPO.setLabel(label);
					if (!blogMapper.addBlogLabel(blogAddLabelPO)) {
						throw new CustomException("新增标签 '" + label + "' 失败！");
					}
					if (!blogMapper.addBlogLabelId(blogAddBo.getId(), blogAddLabelPO.getId())) {
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
		)) {
			throw new CustomException("插入home失败");
		}

		return new BlogAddVO(blogAddBo.getId());
	}

	@Override
	public FileUploadVO addBlogPic(MultipartFile multipartFile) throws CustomException {
		String url = fileUtils.putObject(multipartFile, blogPath);
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
	public PageBind<BlogListDTO> getBlogList(Integer page, String category, String label) {
		String role = UserInfoHolder.getRole();
		String statusCondition = AuthUtils.statusSqlCondition(role);
		List<BlogListDTO> blogList = blogMapper.getBlogList((page - 1) * BLOG_PAGE_SIZE, BLOG_PAGE_SIZE, statusCondition, category, label);
		if (!AuthConfigConstant.ROLE_ADMIN.equals(role)) {
			blogList.forEach((v) -> v.setStatus(null));
		}
		return new PageBind<>(page, blogMapper.getBlogListNum(statusCondition, category, label), blogList);
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
}