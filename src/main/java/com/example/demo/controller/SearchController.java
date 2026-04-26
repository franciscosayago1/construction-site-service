package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.model.Equipment;
import com.example.demo.model.EquipmentLog;
import com.example.demo.model.WorkLog;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EquipmentLogRepository;
import com.example.demo.repository.EquipmentRepository;
import com.example.demo.repository.WorkLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/search")
public class SearchController {

    private final EmployeeRepository employeeRepository;
    private final EquipmentRepository equipmentRepository;
    private final WorkLogRepository workLogRepository;
    private final EquipmentLogRepository equipmentLogRepository;

    public SearchController(
            EmployeeRepository employeeRepository,
            EquipmentRepository equipmentRepository,
            WorkLogRepository workLogRepository,
            EquipmentLogRepository equipmentLogRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.equipmentRepository = equipmentRepository;
        this.workLogRepository = workLogRepository;
        this.equipmentLogRepository = equipmentLogRepository;
    }

    @GetMapping("/employees")
    public List<Employee> searchEmployeesByName(@RequestParam String name) {
        return employeeRepository.findByEmployeeNameContainingIgnoreCase(name);
    }

    @GetMapping("/equipment/status")
    public List<Equipment> searchEquipmentByStatus(@RequestParam String status) {
        return equipmentRepository.findByStatus(status);
    }

    @GetMapping("/equipment/name")
    public List<Equipment> searchEquipmentByName(@RequestParam String name) {
        return equipmentRepository.findByEquipmentNameContainingIgnoreCase(name);
    }

    @GetMapping("/work-logs/employee/{employeeId}")
    public ResponseEntity<List<WorkLog>> getWorkLogsByEmployee(@PathVariable String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(workLogRepository.findByEmployee(employee));
    }

    @GetMapping("/equipment-logs/equipment/{equipmentId}")
    public ResponseEntity<List<EquipmentLog>> getEquipmentLogsByEquipment(@PathVariable String equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);

        if (equipment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(equipmentLogRepository.findByEquipment(equipment));
    }
}
