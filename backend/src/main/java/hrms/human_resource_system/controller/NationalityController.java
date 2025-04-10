package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.Nationality;
import main.java.hrms.human_resource_system.service.NationalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // This annotation marks the class as a REST controller
@RequestMapping("/api/nationalities")  // This annotation maps all methods in the class to this base URL
public class NationalityController {

    private final NationalityService nationalityService;

    @Autowired  // This annotation ensures that the service is injected
    public NationalityController(NationalityService nationalityService) {
        this.nationalityService = nationalityService;
    }

    // GET all nationalities
    @GetMapping
    public ResponseEntity<List<Nationality>> getAllNationalities() {
        List<Nationality> nationalities = nationalityService.getAllNationalities();
        return ResponseEntity.ok(nationalities);
    }

    // GET a nationality by ID
    @GetMapping("/{id}")
    public ResponseEntity<Nationality> getNationalityById(@PathVariable("id") Integer nationalityId) {
        Nationality nationality = nationalityService.getNationalityById(nationalityId);
        if (nationality != null) {
            return ResponseEntity.ok(nationality);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST new nationality
    @PostMapping
    public ResponseEntity<String> createNationality(@RequestBody Nationality nationality) {
        nationalityService.addNationality(nationality);
        return ResponseEntity.status(201).body("Nationality created successfully.");
    }

    // PUT update an existing nationality
    @PutMapping("/{id}")
    public ResponseEntity<String> updateNationality(@PathVariable("id") Integer nationalityId,
                                                    @RequestBody Nationality nationality) {
        nationality.setNationalityId(nationalityId);
        nationalityService.updateNationality(nationality);
        return ResponseEntity.ok("Nationality updated successfully.");
    }

    // DELETE a nationality
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNationality(@PathVariable("id") Integer nationalityId) {
        nationalityService.deleteNationality(nationalityId);
        return ResponseEntity.ok("Nationality deleted successfully.");
    }
}
