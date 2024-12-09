package com.eyes.eyesspace.model.request;

import com.eyes.eyesspace.constant.StatusEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BlogAddRequest {
	@NotNull(message = "博客标题不能为空")
	private String title;

	@NotNull(message = "博客内容不能为空")
	private String content;

	@NotNull(message = "博客摘要不能为空")
	@Length(max = 250, message = "摘要不能超过250字")
	private String summary;

	@NotNull(message = "字数不能为空")
	private Integer words;

	private Integer status = StatusEnum.PUBLIC.getStatus();

	@NotNull(message = "博客标签不能为空")
	private List<String> labels;

	@NotNull(message = "博客分类不能为空")
	private String category;
}
