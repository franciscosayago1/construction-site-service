package com.example.demo.repository;

import com.example.demo.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.model.Site;
import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    List<Equipment> findBySite(Site site);
    List<Equipment> findByStatus(String status);
    List<Equipment> findByEquipmentNameContainingIgnoreCase(String equipmentName);
}
