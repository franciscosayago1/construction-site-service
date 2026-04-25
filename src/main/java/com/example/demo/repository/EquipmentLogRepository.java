package com.example.demo.repository;

import com.example.demo.model.Equipment;
import com.example.demo.model.EquipmentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EquipmentLogRepository extends JpaRepository<EquipmentLog, Long> {
    Optional<EquipmentLog> findByEquipmentAndReturnedAtIsNull(Equipment equipment);
}
