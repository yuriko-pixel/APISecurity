package com.restapiproject.restapiproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapiproject.restapiproject.entities.AppUserDetails;
import com.restapiproject.restapiproject.entities.Employee;
import com.restapiproject.restapiproject.services.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL')")
    public List<Employee> getEmployees(@AuthenticationPrincipal AppUserDetails user) {
        List<Employee> employees = employeeService.retrieveEmployees();

        log.info(user.toString());

        return employees;
    }

    @GetMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL')")
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId,@AuthenticationPrincipal AppUserDetails user) {
    	log.info(user.toString());

        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveEmployee(@RequestBody Employee employee,@AuthenticationPrincipal AppUserDetails user){
        employeeService.saveEmployee(employee);
        System.out.println("Employee Saved Successfully");
    }

    @DeleteMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId,@AuthenticationPrincipal AppUserDetails user){
        employeeService.deleteEmployee(employeeId);
        System.out.println("Employee Deleted Successfully");
    }

    @PutMapping("/employees/{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId,
                               @AuthenticationPrincipal AppUserDetails user){
        Employee emp = employeeService.getEmployee(employeeId);
        if(emp != null){
            employeeService.updateEmployee(employee);
        }

    }

}
