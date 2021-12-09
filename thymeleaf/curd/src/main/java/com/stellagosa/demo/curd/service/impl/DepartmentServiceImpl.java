package com.stellagosa.demo.curd.service.impl;

import com.stellagosa.demo.curd.dao.DepartmentDao;
import com.stellagosa.demo.curd.entity.Department;
import com.stellagosa.demo.curd.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDao departmentDao;

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public List<Department> queryAll() {
        return departmentDao.queryAll();
    }

    @Override
    public Department queryById(Integer id) {
        return departmentDao.queryById(id);
    }
}
