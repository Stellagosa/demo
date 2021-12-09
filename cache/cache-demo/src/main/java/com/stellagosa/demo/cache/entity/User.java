package com.stellagosa.demo.cache.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/9 14:49
 * @Description: User 实体类
 */
@Setter
@Getter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String username;

    private String phone;

    private String description;

}
