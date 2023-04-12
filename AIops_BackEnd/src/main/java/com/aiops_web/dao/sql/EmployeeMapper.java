package com.aiops_web.dao.sql;

import com.aiops_web.entity.sql.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {
    public int save(Employee employee);
    public int deleteByID(Integer id);
    public int update(Employee employee);

    @Select("select * from employees where employee_id = #{id}")
    public Employee findByID(Integer id);

    @Select("select * from employees")
    public List<Employee> findAll();
}
