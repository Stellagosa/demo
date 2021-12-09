package com.stellagosa.demo.cache.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stellagosa.demo.cache.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/9 14:59
 * @Description: User Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

