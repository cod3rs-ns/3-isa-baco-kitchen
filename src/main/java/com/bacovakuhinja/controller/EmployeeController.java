package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.DailyScheduleService;
import com.bacovakuhinja.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DailyScheduleService dailyScheduleService;

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
    public ResponseEntity <Employee> createEmployee(@RequestBody Employee emp) {
        Employee created = employeeService.create(emp);
        return new ResponseEntity<Employee>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/employee",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Employee> updateEmployee(@RequestBody Employee emp) {
        Employee updatedEmp = employeeService.update(emp);
        if (updatedEmp == null) {
            return new ResponseEntity <Employee>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <Employee>(updatedEmp, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/employeeRegion/e={emp_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantRegion> getRegion(@PathVariable("emp_id") Integer empId) {
        DailySchedule sch = dailyScheduleService.findScheduleByEmployeeForNow(empId);
        if(sch == null)
            return new ResponseEntity <RestaurantRegion>(HttpStatus.NOT_FOUND);

        return new ResponseEntity <RestaurantRegion>(sch.getRegion(), HttpStatus.OK);
    }

    // TODO change after implementation of new TimeSchedule system
    @Authorization(value = "all")
    @RequestMapping(
            value = "/api/employee/schedule",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection<DailySchedule>> getSchedule(final HttpServletRequest request) {
        User user = (User) request.getAttribute("loggedUser");
        ArrayList<DailySchedule> emptyList = new ArrayList<DailySchedule>();

        if (user == null)
            return new ResponseEntity <Collection<DailySchedule>>(emptyList, HttpStatus.OK);
        else {
            Employee emp = employeeService.findOne(user.getUserId());
            if (emp == null)
                return new ResponseEntity <Collection<DailySchedule>>(emptyList, HttpStatus.OK);
            else {
                Collection<DailySchedule> schedules = dailyScheduleService.findByEmployee(emp.getUserId());
                return new ResponseEntity<Collection<DailySchedule>>(schedules, HttpStatus.OK);
            }
        }
    }
}
