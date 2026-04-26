package com.example.demo.model;

public class LoginResponse {

    private String username;
    private String role;
    private String employeeId;

    public LoginResponse() {
    }

    public LoginResponse(String username, String role, String employeeId) {
        this.username = username;
        this.role = role;
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
