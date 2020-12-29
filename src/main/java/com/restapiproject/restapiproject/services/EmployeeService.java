package com.restapiproject.restapiproject.services;


import com.restapiproject.restapiproject.entities.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> retrieveEmployees();

    public Employee getEmployee(Long employeeId);

    public void saveEmployee(Employee employee);

    public void deleteEmployee(Long employeeId);

    public void updateEmployee(Employee employee);
}