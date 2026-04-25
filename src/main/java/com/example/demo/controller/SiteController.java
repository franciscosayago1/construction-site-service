package com.example.demo.controller;

import com.example.demo.model.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.SiteRepository;
import java.util.List;
import com.example.demo.model.Employee;
import com.example.demo.model.Equipment;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EquipmentRepository;

@RestController
@RequestMapping("/v1")
public class SiteController {

    private static final Logger log = LoggerFactory.getLogger(SiteController.class);
    private final SiteRepository siteRepository;
    private final EmployeeRepository employeeRepository;
    private final EquipmentRepository equipmentRepository;

    public SiteController(SiteRepository siteRepository, EmployeeRepository employeeRepository, EquipmentRepository equipmentRepository) {
        this.siteRepository = siteRepository;
        this.employeeRepository = employeeRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @PostMapping("/site")
    public Site post(@RequestBody Site site) {
        log.info("Created site: {}", site); //System.out.println()
        return siteRepository.save(site); // change the "return" for the repository instead of a String
    }

    @GetMapping("/site/{id}") // change "/site/id/{id}" for "/site/{id}"
    public Site get(@PathVariable String id) {
        log.info("Retrieving site by id: {}", id);
        return siteRepository.findById(id).orElse(null); // Delete "new" as we are not creating a new one
    }

    @GetMapping("/site/{id}/employees")
    public List<Employee> getEmployeesBySite(@PathVariable String id) {
        Site site = siteRepository.findById(id).orElse(null);
        return employeeRepository.findBySite(site);
    }

    @GetMapping("/site/{id}/equipment")
    public List<Equipment> getEquipmentBySite(@PathVariable String id) {
        Site site = siteRepository.findById(id).orElse(null);
        return equipmentRepository.findBySite(site);
    }

    @PutMapping("/site/{id}")
    public ResponseEntity<Site> update(@PathVariable String id, @RequestBody Site site) {
        if (!siteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        site.setId(id);
        return ResponseEntity.ok(siteRepository.save(site));
    }

    @DeleteMapping("/site/{id}") // change "/site/id/{id}" for "/site/{id}"
    public ResponseEntity<String> delete(@PathVariable String id) {
        log.info("Deleting site by id: {}", id);
        if (siteRepository.existsById(id)) {
            siteRepository.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Site not found");
        }
    }
}
