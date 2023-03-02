package com.peerislands.controller;

import com.peerislands.exception.RecordNotFoundException;
import com.peerislands.model.Employee;
import com.peerislands.request.SearchQuery;
import com.peerislands.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees/search")
public class EmployeeSearchController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<List<Employee>> searchEmployees(@RequestBody SearchQuery searchQuery) {

        List<Employee> employeeList = employeeService.searchEmployee(searchQuery);

        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @PostMapping("/query")
    public ResponseEntity<String> searchEmployeesQuery(@RequestBody SearchQuery searchQuery) {

        String sqlQuery = employeeService.searchEmployeeQuery(searchQuery);

        return new ResponseEntity<>(sqlQuery, HttpStatus.OK);
    }

}