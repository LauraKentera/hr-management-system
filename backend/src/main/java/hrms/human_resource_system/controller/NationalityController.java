package hrms.human_resource_system.controller;

import hrms.human_resource_system.exception.CustomErrorResponse;
import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Nationality;
import hrms.human_resource_system.service.NationalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hrms.human_resource_system.mapper.NationalityMapper;
import hrms.human_resource_system.dto.NationalityResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/nationalities")
public class NationalityController {

    private final NationalityService nationalityService;

    @Autowired
    public NationalityController(NationalityService nationalityService) {
        this.nationalityService = nationalityService;
    }

    @GetMapping
    public ResponseEntity<?> getAllNationalities() {
        try {
            List<Nationality> nationalities = nationalityService.getAllNationalities();
            List<NationalityResponseDTO> responseDTOs = nationalities.stream()
                    .map(NationalityMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseDTOs);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving nationalities", 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNationalityById(@PathVariable("id") Integer nationalityId) {
        try {
            Nationality nationality = nationalityService.getNationalityById(nationalityId);
            return nationality != null
                    ? ResponseEntity.ok(NationalityMapper.toDTO(nationality))
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new CustomErrorResponse("Nationality not found with ID: " + nationalityId, 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving nationality", 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> createNationality(@RequestBody Nationality nationality) {
        try {
            Nationality created = nationalityService.addNationality(nationality);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(NationalityMapper.toDTO(created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating nationality", 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNationality(@PathVariable("id") Integer nationalityId,
            @RequestBody Nationality nationality) {
        try {
            if (nationality.getNationalityId() != null && !nationality.getNationalityId().equals(nationalityId)) {
                return ResponseEntity.badRequest()
                        .body(new CustomErrorResponse("ID in path does not match ID in request body", 400));
            }

            nationality.setNationalityId(nationalityId);
            Nationality updated = nationalityService.updateNationality(nationality);
            return ResponseEntity.ok(NationalityMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating nationality", 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNationality(@PathVariable("id") Integer nationalityId) {
        try {
            nationalityService.deleteNationality(nationalityId, 1); // 1 = hardcoded performedBy for now
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse(e.getMessage(), 404));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CustomErrorResponse(e.getMessage(), 409));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error deleting nationality", 500));
        }
    }

}
