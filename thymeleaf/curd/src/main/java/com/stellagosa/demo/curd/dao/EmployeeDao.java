package com.stellagosa.demo.curd.dao;

import com.stellagosa.demo.curd.entity.Employee;

import java.util.List;

public interface EmployeeDao {

    List<Employee> queryAll();

    void addEmployee(Employee employee);

    Employee queryById(Integer id);

    void updateEmployee(Employee employee);

    void deleteById(Integer id);
}
