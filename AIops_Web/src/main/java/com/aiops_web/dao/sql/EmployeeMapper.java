package com.aiops_web.dao.sql;

import com.aiops_web.entity.sql.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper {
    public int save(Employee employee);
    public int deleteByID(Integer id);
    public int update(Employee employee);
    public Employee findByID(Integer id);
    public List<Employee> findAll();
}
