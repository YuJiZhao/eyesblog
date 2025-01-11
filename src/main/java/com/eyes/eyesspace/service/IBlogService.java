package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.exception.CustomException;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.model.dto.BlogCategoryDTO;
import com.eyes.eyesspace.model.dto.BlogInfoDTO;
import com.eyes.eyesspace.model.dto.BlogLabelDTO;
import com.eyes.eyesspace.model.dto.BlogListDTO;
import com.eyes.eyesspace.model.entity.Blog;
import com.eyes.eyesspace.model.request.BlogAddRequest;
import com.eyes.eyesspace.model.vo.BlogAddVO;
import com.eyes.eyesspace.model.vo.BlogListInfoVO;
import com.eyes.eyesspace.model.vo.FileUploadVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBlogService extends IService<Blog>  {
	BlogAddVO addBlog(BlogAddRequest blogAddRequest) throws CustomException;

	FileUploadVO addBlogPic(MultipartFile multipartFile) throws CustomException;

	BlogListInfoVO getBlogListInfo() throws CustomException;

	PageBind<BlogListDTO> getBlogList(Integer page, String category, String label) throws CustomException;

	List<BlogListDTO> getBlogListByIds(List<Integer> ids);

	BlogInfoDTO getBlogInfo(Integer id) throws CustomException;

	List<BlogCategoryDTO> getBlogCategory() throws CustomException;

	List<BlogLabelDTO> getBlogLabel() throws CustomException;
}