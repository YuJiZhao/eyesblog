package com.eyes.eyesspace.sync.model.vo;

import com.eyes.eyesspace.sync.model.dto.BookListDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author artonyu
 * date 2024-08-01 10:03
 */

@Data
@AllArgsConstructor
public class BookListVO {
	private Integer total;

	private List<BookListDTO> data;
}
