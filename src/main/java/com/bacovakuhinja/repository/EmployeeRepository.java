package com.bacovakuhinja.repository;


import com.bacovakuhinja.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
