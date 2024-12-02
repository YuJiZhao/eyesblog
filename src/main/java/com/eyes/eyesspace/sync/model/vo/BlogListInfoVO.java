package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.persistent.po.BlogDataPO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class BlogListInfoVO {
	private Integer totalNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer publicNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer privateNum;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer deleteNum;

	private Integer viewsNum;

	private Integer commentsNum;

	private Integer words;

	public BlogListInfoVO(Integer totalNum, BlogDataPO blogDataPo, Integer words) {
		this.totalNum = totalNum;
		this.viewsNum = blogDataPo.getViewsNum();
		this.commentsNum = blogDataPo.getCommentsNum();
		this.words = words;
	}

	public BlogListInfoVO(Integer totalNum, Integer publicNum, Integer privateNum, Integer deleteNum, BlogDataPO blogDataPo, Integer words) {
		this.totalNum = totalNum;
		this.publicNum = publicNum;
		this.privateNum = privateNum;
		this.deleteNum = deleteNum;
		this.viewsNum = blogDataPo.getViewsNum();
		this.commentsNum = blogDataPo.getCommentsNum();
		this.words = words;
	}
}
