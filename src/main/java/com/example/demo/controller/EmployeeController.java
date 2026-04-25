package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class EmployeeController {
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        log.info("Creating new employee: {}", employee);
        return employeeRepository.save(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee get(@PathVariable String id) {
        log.info("Retrieving employee by id: {}", id);
        return employeeRepository.findById(id).orElse(null);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> update(@PathVariable String id, @RequestBody Employee employee) {
        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        employee.setId(id);
        return ResponseEntity.ok(employeeRepository.save(employee));
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        log.info("Deleting employee by id: {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Employee not found");
        }
    }
}
