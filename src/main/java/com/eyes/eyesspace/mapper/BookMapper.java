package com.eyes.eyesspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesspace.model.entity.Book;
import com.eyes.eyesspace.model.vo.BookInfoVO;
import com.eyes.eyesspace.model.vo.BookListInfoVO;
import com.eyes.eyesspace.model.vo.BookListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author eyesYeager
 * data 2024/12/9 20:53
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
	@Select("select COUNT(*) as totalNum, SUM(`view`) as viewNum from book where ${statusCondition}")
	BookListInfoVO getBookListInfo(String statusCondition);

	@Select("select id, title, author, type, period, introduce, `view`, create_time from book where ${statusCondition} order by create_time desc limit #{start}, #{pageSize}")
	List<BookListVO> getBookList(int start, int pageSize, String statusCondition);

	@Select("select COUNT(*) from book where ${statusCondition}")
	Integer getBookNum(String statusCondition);

	@Select("select title, author, type, period, introduce, word, `view`, create_time from book where id=#{id} and ${statusCondition}")
	BookInfoVO getBookInfo(Integer id, String statusCondition);

	@Update("update book set view=view+1 where id=#{id}")
	boolean addView(Integer id);
}
