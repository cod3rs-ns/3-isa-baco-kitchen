package com.bacovakuhinja.service.impl;


import com.bacovakuhinja.model.Employee;
import com.bacovakuhinja.repository.EmployeeRepository;
import com.bacovakuhinja.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Collection <Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findOne(Integer eId) {
        return employeeRepository.findOne(eId);
    }

    @Override
    public Employee create(Employee e) {
        return employeeRepository.save(e);
    }

    @Override
    public Employee update(Employee e) {
        Employee empPersistent = employeeRepository.findOne(e.getUserId());
        if (empPersistent == null) return null;
        empPersistent.update(e);
        return employeeRepository.save(empPersistent);
    }

    @Override
    public void delete(Integer eId) {
        employeeRepository.delete(eId);
    }


    // TODO @Keky probably :) Switch to query
    @Override
    public Collection <Employee> findByRestaurant(Integer restaurantId) {
        Collection <Employee> allEmployees = employeeRepository.findAll();
        Collection <Employee> restaurantEmployees = new ArrayList <Employee>();
        for (Employee employee : allEmployees) {
            if (employee.getRestaurantID() == restaurantId) {
                restaurantEmployees.add(employee);
            }
        }
        return restaurantEmployees;
    }
}
