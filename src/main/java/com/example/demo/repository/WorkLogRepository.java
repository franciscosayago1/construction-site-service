package com.example.demo.repository;

import com.example.demo.model.Employee;
import com.example.demo.model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    Optional<WorkLog> findByEmployeeAndClockOutTimeIsNull(Employee employee);

    List<WorkLog> findByEmployee(Employee employee);
}
