package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Employee {
    @Id
    private String id;
    private String employeeName;
    private String role;
    private double hourlyRate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "site_id")
    @JsonBackReference
    private Site site;

    public Employee(){
    }

    public Employee(String id, String employeeName, String role,
                    double hourlyRate, String status, Site site) {
        this.id = id;
        this.employeeName = employeeName;
        this.role = role;
        this.hourlyRate = hourlyRate;
        this.status = status;
        this.site = site;
    }

    public Site getSite() {
        return site;
    }

    public String getStatus() {
        return status;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public String getRole() {
        return role;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", role='" + role + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", status='" + status + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
