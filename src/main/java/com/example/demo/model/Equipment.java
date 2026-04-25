package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Equipment {
    @Id
    private String id;
    private String equipmentName;
    private String status;

    @ManyToOne
    @JoinColumn(name = "site_id")
    @JsonBackReference
    private Site site;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee assignedTo;

    public Equipment() {
    }

    public Equipment(String id, String equipmentName, String status, Site site) {
        this.id = id;
        this.equipmentName = equipmentName;
        this.status = status;
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public Site getSite() {
        return site;
    }

    public String getStatus() {
        return status;
    }

    public String getToolName() {
        return equipmentName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setToolName(String toolName) {
        this.equipmentName = toolName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Employee getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Employee assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "id='" + id + '\'' +
                ", toolName='" + equipmentName + '\'' +
                ", status='" + status + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
