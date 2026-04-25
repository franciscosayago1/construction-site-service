package com.example.demo.repository;

import com.example.demo.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.model.Site;

public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    List<Equipment> findBySite(Site site);
}
