package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.Employee;
import com.bacovakuhinja.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(
            value = "/api/employees",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Employee>> getEmployees() {
        Collection <Employee> employees = employeeService.findAll();
        return new ResponseEntity <Collection <Employee>>(employees, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/employees/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Employee> getEmployee(@PathVariable("id") Integer id) {
        Employee emp = employeeService.findOne(id);
        return new ResponseEntity <Employee>(emp, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/employee",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Employee> createCook(@RequestBody Employee emp) {
        Employee created = employeeService.create(emp);
        return new ResponseEntity<Employee>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/employee",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Employee> updateRestaurantManager(@RequestBody Employee emp) {
        Employee updatedEmp = employeeService.update(emp);
        if (updatedEmp == null) {
            return new ResponseEntity <Employee>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <Employee>(updatedEmp, HttpStatus.OK);
    }
}
