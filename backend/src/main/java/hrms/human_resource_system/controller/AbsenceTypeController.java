package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.AbsenceTypeResponseDTO;
import main.java.hrms.human_resource_system.mapper.AbsenceTypeMapper;
import main.java.hrms.human_resource_system.model.AbsenceType;
import main.java.hrms.human_resource_system.service.AbsenceTypeService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/absence-types")
public class AbsenceTypeController {

    private final AbsenceTypeService absenceTypeService;

    public AbsenceTypeController(AbsenceTypeService absenceTypeService) {
        this.absenceTypeService = absenceTypeService;
    }

    @GetMapping
    public ResponseEntity<List<AbsenceTypeResponseDTO>> getAllAbsenceTypes() {
        List<AbsenceType> types = absenceTypeService.getAll();
        List<AbsenceTypeResponseDTO> dtoList = types.stream()
                .map(AbsenceTypeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceType> getById(@PathVariable int id) {
        AbsenceType type = absenceTypeService.getById(id);
        if (type != null) {
            return ResponseEntity.ok(type);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody AbsenceType absenceType) {
        absenceTypeService.insert(absenceType);
        return ResponseEntity.status(HttpStatus.CREATED).body("✅ Absence type created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody AbsenceType updatedType) {
        updatedType.setAbsenceTypeId(id);
        absenceTypeService.update(updatedType);
        return ResponseEntity.ok("✏️ Absence type updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        absenceTypeService.delete(id);
        return ResponseEntity.ok("🗑️ Absence type deleted.");
    }
}
