package com.eyes.eyesspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyes.eyesspace.model.entity.Joke;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author eyesYeager
 * date 2023/9/25 9:47
 */

@Mapper
public interface JokeMapper extends BaseMapper<Joke> {
}
