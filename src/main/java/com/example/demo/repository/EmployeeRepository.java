package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.model.Site;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findBySite(Site site);
}
