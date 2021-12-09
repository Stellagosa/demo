package com.stellagosa.demo.curd.service.impl;

import com.stellagosa.demo.curd.dao.DepartmentDao;
import com.stellagosa.demo.curd.dao.EmployeeDao;
import com.stellagosa.demo.curd.entity.Employee;
import com.stellagosa.demo.curd.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;
    private final DepartmentDao departmentDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao, DepartmentDao departmentDao) {
        this.employeeDao = employeeDao;
        this.departmentDao = departmentDao;
    }

    @Override
    public List<Employee> queryAll() {
        return employeeDao.queryAll();
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeDao.addEmployee(employee);
    }

    @Override
    public Employee queryById(Integer id) {
        return employeeDao.queryById(id);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeDao.updateEmployee(employee);
    }

    @Override
    public void deleteById(Integer id) {
        employeeDao.deleteById(id);
    }

}
