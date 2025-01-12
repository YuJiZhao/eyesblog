package com.eyes.eyesspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyes.eyesspace.result.PageBind;
import com.eyes.eyesspace.model.dto.JokeListDTO;
import com.eyes.eyesspace.model.entity.Joke;

/**
 * @author eyesYeager
 * date 2023/9/25 8:50
 */
public interface IJokeService extends IService<Joke> {

	PageBind<JokeListDTO> getJokeList(Integer pageIndex);
}
