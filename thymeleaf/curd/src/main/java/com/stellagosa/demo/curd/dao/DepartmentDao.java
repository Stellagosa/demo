package com.stellagosa.demo.curd.dao;

import com.stellagosa.demo.curd.entity.Department;

import java.util.List;

public interface DepartmentDao {

    List<Department> queryAll();

    Department queryById(Integer id);
}
