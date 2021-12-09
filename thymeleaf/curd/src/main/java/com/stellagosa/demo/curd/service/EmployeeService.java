package com.stellagosa.demo.curd.service;

import com.stellagosa.demo.curd.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> queryAll();

    void addEmployee(Employee employee);

    Employee queryById(Integer id);

    void updateEmployee(Employee employee);

    void deleteById(Integer id);
}
