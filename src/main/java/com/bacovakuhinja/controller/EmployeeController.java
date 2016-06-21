package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.annotations.SendEmail;
import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.DailyScheduleService;
import com.bacovakuhinja.service.EmployeeService;
import com.bacovakuhinja.utility.Constants;
import com.bacovakuhinja.utility.PasswordHelper;
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

    @SendEmail
    @RequestMapping(value = "/api/employee",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Employee> createEmployee(@RequestBody Employee emp) {
        //generating random password
        String pass = PasswordHelper.randomPassword();
        emp.setPassword(pass);
        emp.setLogged(false);
        emp.setVerified(Constants.Registration.STATUS_NOT_VERIFIED);

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
            return new ResponseEntity <RestaurantRegion>(HttpStatus.NO_CONTENT);

        return new ResponseEntity <RestaurantRegion>(sch.getRegion(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/employees/r={rst_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection<Employee>> getEmployeesByRestaurant(@PathVariable("rst_id") Integer rstID) {
        Collection<Employee> employees = employeeService.findByRestaurant(rstID);

        return new ResponseEntity <Collection<Employee>>(employees, HttpStatus.OK);
    }

    @Authorization()
    @RequestMapping(
            value = "/api/employee/schedule",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection<DailySchedule>> getSchedule(final HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.Authorization.LOGGED_USER);
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
