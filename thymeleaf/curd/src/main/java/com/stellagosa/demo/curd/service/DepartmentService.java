package com.stellagosa.demo.curd.service;

import com.stellagosa.demo.curd.entity.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> queryAll();

    Department queryById(Integer id);

}
