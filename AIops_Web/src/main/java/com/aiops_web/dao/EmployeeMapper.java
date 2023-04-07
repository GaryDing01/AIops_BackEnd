package com.aiops_web.dao;

import com.aiops_web.entity.Employee ;

import java.util.List;

public interface EmployeeMapper {
    public int save(Employee employee);
    public int deleteByID(Integer id);
    public int update(Employee employee);
    public Employee findByID(Integer id);
    public List<Employee> findAll();
}
