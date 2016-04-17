package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Employee;

import java.util.Collection;

/**
 * Created by Bojan on 17-Apr-16.
 */
public interface EmployeeService {

    public Collection<Employee> findAll();

    public Employee findOne(Integer eId);

    public Employee create(Employee e);

    public Employee update(Employee e);

    public void delete(Integer eId);

}
