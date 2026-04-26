package com.example.demo.controller;

import com.example.demo.model.Equipment;
import com.example.demo.model.EquipmentLog;
import com.example.demo.model.WorkLog;
import com.example.demo.repository.EquipmentLogRepository;
import com.example.demo.repository.EquipmentRepository;
import com.example.demo.repository.WorkLogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/reports")
public class ReportController {

    private final EquipmentRepository equipmentRepository;
    private final WorkLogRepository workLogRepository;
    private final EquipmentLogRepository equipmentLogRepository;

    public ReportController(
            EquipmentRepository equipmentRepository,
            WorkLogRepository workLogRepository,
            EquipmentLogRepository equipmentLogRepository
    ) {
        this.equipmentRepository = equipmentRepository;
        this.workLogRepository = workLogRepository;
        this.equipmentLogRepository = equipmentLogRepository;
    }

    @GetMapping("/equipment/in-use")
    public List<Equipment> getEquipmentInUse() {
        return equipmentRepository.findByStatus("In Use");
    }

    @GetMapping("/work-logs")
    public List<WorkLog> getAllWorkLogs() {
        return workLogRepository.findAll();
    }

    @GetMapping("/equipment-logs")
    public List<EquipmentLog> getAllEquipmentLogs() {
        return equipmentLogRepository.findAll();
    }
}
