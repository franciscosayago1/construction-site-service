package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.Duration;

@Entity
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;

    public Long getDurationInMinutes() {
        if (clockInTime == null || clockOutTime == null) {
            return null;
        }
        return Duration.between(clockInTime, clockOutTime).toMinutes();
    }

    public WorkLog() {
    }

    public WorkLog(Employee employee, LocalDateTime clockInTime) {
        this.employee = employee;
        this.clockInTime = clockInTime;
    }

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(LocalDateTime clockInTime) {
        this.clockInTime = clockInTime;
    }

    public LocalDateTime getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(LocalDateTime clockOutTime) {
        this.clockOutTime = clockOutTime;
    }
}
