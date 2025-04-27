package hrms.human_resource_system.controller;

import hrms.human_resource_system.exception.CustomErrorResponse;
import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.EmploymentContract;
import hrms.human_resource_system.model.ContractAnnex;
import hrms.human_resource_system.service.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public ResponseEntity<?> getAllContracts() {
        try {
            List<EmploymentContract> contracts = contractService.getAllContracts();
            return ResponseEntity.ok(contracts);
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving contracts", 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContractById(@PathVariable int id) {
        try {
            EmploymentContract contract = contractService.getContractById(id);
            return contract != null
                    ? ResponseEntity.ok(contract)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse("Contract not found with id: " + id, 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving contract", 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> addContract(@RequestBody EmploymentContract contract, @RequestParam int performedBy) {
        try {
            EmploymentContract createdContract = contractService.addContract(contract, performedBy);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContract);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error creating contract", 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContract(@PathVariable int id, @RequestBody EmploymentContract contract, @RequestParam int performedBy) {
        try {
            if (contract.getContractId() != id) {
                return ResponseEntity.badRequest()
                        .body(new CustomErrorResponse("ID in path does not match ID in request body", 400));
            }
            contract.setContractId(id);
            EmploymentContract updatedContract = contractService.updateContract(contract, performedBy);
            return ResponseEntity.ok(updatedContract);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CustomErrorResponse(e.getMessage(), 400));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error updating contract", 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContract(@PathVariable int id, @RequestParam int performedBy) {
        try {
            contractService.deleteContract(id, performedBy);
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
                    .body(new CustomErrorResponse("Error deleting contract", 500));
        }
    }

    @GetMapping("/{id}/annexes")
    public ResponseEntity<?> getAnnexesByContractId(@PathVariable int id) {
        try {
            List<ContractAnnex> annexes = contractService.getAnnexesByContractId(id);
            return ResponseEntity.ok(annexes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomErrorResponse("Contract not found with id: " + id, 404));
        } catch (DLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Database error: " + e.getMessage(), 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomErrorResponse("Error retrieving contract annexes", 500));
        }
    }
}
