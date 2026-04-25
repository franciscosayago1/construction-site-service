package com.example.demo.controller;

import com.example.demo.model.Equipment;
import com.example.demo.repository.EquipmentRepository;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.model.EquipmentLog;
import com.example.demo.repository.EquipmentLogRepository;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class EquipmentController {

    private static final Logger log = LoggerFactory.getLogger(EquipmentController.class);
    private final EquipmentRepository equipmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EquipmentLogRepository equipmentLogRepository;

    public EquipmentController(EquipmentRepository equipmentRepository, EmployeeRepository employeeRepository,EquipmentLogRepository equipmentLogRepository) {
        this.equipmentRepository = equipmentRepository;
        this.employeeRepository = employeeRepository;
        this.equipmentLogRepository = equipmentLogRepository;
    }

    @PostMapping("/equipment")
    public Equipment post(@RequestBody Equipment Equipment) {
        log.info("Created tool: {}", Equipment);
        return equipmentRepository.save(Equipment);
    }

    @GetMapping("/equipment/{id}")
    public Equipment get(@PathVariable String id) {
        log.info("Retrieving tool by id: {}", id);
        return equipmentRepository.findById(id).orElse(null);
    }

    @GetMapping("/equipment/{equipmentId}/logs")
    public ResponseEntity<List<EquipmentLog>> getLogs(@PathVariable String equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        if (equipment == null) {
            return ResponseEntity.notFound().build();
        }

        List<EquipmentLog> logs = equipmentLogRepository.findAll().stream().filter(log -> log.getEquipment().getId().equals(equipmentId)).toList();

        return ResponseEntity.ok(logs);
    }

    @PutMapping("/equipment/{id}")
    public ResponseEntity<Equipment> update(@PathVariable String id, @RequestBody Equipment Equipment) {
        log.info("Updated tool: {}", Equipment);
        if (!equipmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Equipment.setId(id);
        return ResponseEntity.ok(equipmentRepository.save(Equipment));
    }

    @PutMapping("/equipment/{equipmentId}/assign/{employeeId}")
    public ResponseEntity<Equipment> assignEquipmentToEmployee(@PathVariable String equipmentId, @PathVariable String employeeId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (equipment == null || employee == null) {
            return ResponseEntity.notFound().build();
        }
        if ("In Use".equalsIgnoreCase(equipment.getStatus())) {
            return ResponseEntity.badRequest().build();
        }
        if (!equipment.getSite().getId().equals(employee.getSite().getId())) {
            return ResponseEntity.badRequest().build();
        }

        equipment.setAssignedTo(employee);
        equipment.setStatus("In Use");

        EquipmentLog log = new EquipmentLog(equipment, employee, LocalDateTime.now());
        equipmentLogRepository.save(log);
        return ResponseEntity.ok(equipmentRepository.save(equipment));
    }

    @PutMapping("/equipment/{equipmentId}/unassign")
    public ResponseEntity<Equipment> unassignEquipment(@PathVariable String equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);

        if (equipment == null) {
            return ResponseEntity.notFound().build();
        }
        EquipmentLog log = equipmentLogRepository.findByEquipmentAndReturnedAtIsNull(equipment).orElse(null);
        if (log != null) {
            log.setReturnedAt(LocalDateTime.now());
            equipmentLogRepository.save(log);
        }
        equipment.setAssignedTo(null);
        equipment.setStatus("Available");
        return ResponseEntity.ok(equipmentRepository.save(equipment));
    }

    @DeleteMapping("/equipment/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        log.info("Deleting tool by id: {}", id);
        if (equipmentRepository.existsById(id)) {
            equipmentRepository.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Tool not found");
        }
    }
}
