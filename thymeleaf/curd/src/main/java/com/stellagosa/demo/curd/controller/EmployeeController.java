package com.stellagosa.demo.curd.controller;

import com.stellagosa.demo.curd.entity.Employee;
import com.stellagosa.demo.curd.service.DepartmentService;
import com.stellagosa.demo.curd.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/emps")
    public String list(Model model) {
        List<Employee> employees = employeeService.queryAll();
        model.addAttribute("emps", employees);
        return "emp/list";
    }

    // 返回员工添加页面
    @GetMapping("/emp")
    public String toAddPage(Model model) {
        model.addAttribute("depts", departmentService.queryAll());
        return "emp/add";
    }

    // 员工添加 添加完成返回员工列表页面
    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        System.out.println(employee);
        employeeService.addEmployee(employee);
        return "redirect:/emps";
        //return "forward:/emps";
    }

    //返回员工修改页面
    @GetMapping("/emp/{id}")
    public String toEditEmp(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("emp", employeeService.queryById(id));
        model.addAttribute("depts", departmentService.queryAll());
        return "emp/edit";
    }

    @PutMapping("/emp")
    public String updateEmployee(Employee employee) {
        employeeService.updateEmployee(employee);
        return "redirect:/emps";
    }

    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.deleteById(id);
        return "redirect:/emps";
    }
}
