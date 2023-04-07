package com.aiops_web.entity;

import lombok.Data;

@Data
public class Employee {
    private Integer employee_id;
    private String first_name;
    private String last_name;
    private String job_title;
    private Double salary;
    private Integer reports_to;
    private Integer office_id;
}
