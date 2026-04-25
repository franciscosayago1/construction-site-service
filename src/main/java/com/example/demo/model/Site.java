package com.example.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Site {
    @Id
    private String id;
    private String siteName;
    private String location;
    private String status;

    @OneToMany(mappedBy = "site")
    @JsonManagedReference
    private List<Employee> employees;

    @OneToMany(mappedBy = "site")
    @JsonManagedReference
    private List<Equipment> equipments;

    public Site() {
    }

    public Site(String id, String siteName, String location, String status) {
        this.id = id;
        this.siteName = siteName;
        this.location = location;
        this.status = status;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getSiteName() {
        return siteName;
    }
    public String getLocation() {
        return location;
    }
    public String getStatus() {
        return status;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    @Override
    public String toString() {
        return "Site{" +
                "id='" + id + '\'' +
                ", siteName='" + siteName + '\'' +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

