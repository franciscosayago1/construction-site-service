package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.model.WorkLog;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.WorkLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;

@RestController
@RequestMapping("/v1")
public class WorkLogController {

    private final WorkLogRepository workLogRepository;
    private final EmployeeRepository employeeRepository;
    private final UserAccountRepository userAccountRepository;

    public WorkLogController(
            WorkLogRepository workLogRepository,
            EmployeeRepository employeeRepository,
            UserAccountRepository userAccountRepository
    ) {
        this.workLogRepository = workLogRepository;
        this.employeeRepository = employeeRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping("/employee/{employeeId}/clock-in")
    public ResponseEntity<?> clockIn(
            @PathVariable String employeeId,
            @RequestParam String username
    ) {
        UserAccount user = userAccountRepository.findById(username).orElse(null);

        if (user == null || !"EMPLOYEE".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(403).body("Only employees can clock in");
        }

        if (!employeeId.equals(user.getEmployeeId())) {
            return ResponseEntity.status(403).body("You can only clock in for yourself");
        }

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
    public ResponseEntity<?> clockOut(
            @PathVariable String employeeId,
            @RequestParam String username
    ) {
        UserAccount user = userAccountRepository.findById(username).orElse(null);

        if (user == null || !"EMPLOYEE".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(403).body("Only employees can clock out");
        }

        if (!employeeId.equals(user.getEmployeeId())) {
            return ResponseEntity.status(403).body("You can only clock out for yourself");
        }

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
