package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Employee;

import java.util.Collection;

public interface EmployeeService {

    Collection <Employee> findAll();

    Employee findOne(Integer eId);

    Employee create(Employee e);

    Employee update(Employee e);

    void delete(Integer eId);

    Collection <Employee> findByRestaurant(Integer restaurantId);

}
