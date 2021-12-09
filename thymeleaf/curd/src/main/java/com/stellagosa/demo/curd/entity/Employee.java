package com.stellagosa.demo.curd.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    // 1 male, 0 female
    private Integer gender;
    private Department department;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
