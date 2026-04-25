package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.model.WorkLog;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.WorkLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class WorkLogController {

    private final WorkLogRepository workLogRepository;
    private final EmployeeRepository employeeRepository;

    public WorkLogController(WorkLogRepository workLogRepository, EmployeeRepository employeeRepository) {
        this.workLogRepository = workLogRepository;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/employee/{employeeId}/clock-in")
    public ResponseEntity<?> clockIn(@PathVariable String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        if (workLogRepository.findByEmployeeAndClockOutTimeIsNull(employee).isPresent()) {
            return ResponseEntity.badRequest().body("Employee is already clocked in");
        }

        WorkLog workLog = new WorkLog(employee, LocalDateTime.now());
        return ResponseEntity.ok(workLogRepository.save(workLog));
    }

    @PutMapping("/employee/{employeeId}/clock-out")
    public ResponseEntity<?> clockOut(@PathVariable String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        WorkLog openLog = workLogRepository.findByEmployeeAndClockOutTimeIsNull(employee).orElse(null);

        if (openLog == null) {
            return ResponseEntity.badRequest().body("Employee is not currently clocked in");
        }

        openLog.setClockOutTime(LocalDateTime.now());
        return ResponseEntity.ok(workLogRepository.save(openLog));
    }

    @GetMapping("/employee/{employeeId}/work-logs")
    public ResponseEntity<List<WorkLog>> getWorkLogs(@PathVariable String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(workLogRepository.findByEmployee(employee));
    }
}
