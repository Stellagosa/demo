package com.stellagosa.demo.curd.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Department {
    private Integer id;
    private String departmentName;
}
