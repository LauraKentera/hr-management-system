package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.dto.ContractResponseDTO;
import main.java.hrms.human_resource_system.mapper.ContractMapper;
import main.java.hrms.human_resource_system.model.ContractAnnex;
import main.java.hrms.human_resource_system.model.EmploymentContract;
import main.java.hrms.human_resource_system.service.ContractService;
import main.java.hrms.human_resource_system.service.EmployeeService;
import main.java.hrms.human_resource_system.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;
    private final EmployeeService employeeService;
    private final PositionService positionService;

    public ContractController(ContractService contractService,
                              EmployeeService employeeService,
                              PositionService positionService) {
        this.contractService = contractService;
        this.employeeService = employeeService;
        this.positionService = positionService;
    }

    @GetMapping
    public ResponseEntity<List<ContractResponseDTO>> getAllContracts() {
        List<EmploymentContract> contracts = contractService.getAllContracts();

        List<ContractResponseDTO> dtos = contracts.stream()
                .map(contract -> {
                    String employeeName = employeeService.getFullNameById(contract.getEmployeeId());
                    String positionName = positionService.getPositionNameById(contract.getPositionId());
                    return ContractMapper.toDTO(contract, employeeName, positionName);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> getContractById(@PathVariable int id) {
        EmploymentContract contract = contractService.getContractById(id);
        if (contract == null) return ResponseEntity.notFound().build();

        String employeeName = employeeService.getFullNameById(contract.getEmployeeId());
        String positionName = positionService.getPositionNameById(contract.getPositionId());

        ContractResponseDTO dto = ContractMapper.toDTO(contract, employeeName, positionName);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> addContract(@RequestBody EmploymentContract contract) {
        contractService.addContract(contract);
        return ResponseEntity.ok("✅ Contract created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContract(@PathVariable int id, @RequestBody EmploymentContract contract) {
        contract.setContractId(id);
        contractService.updateContract(contract);
        return ResponseEntity.ok("🔄 Contract updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContract(@PathVariable int id) {
        contractService.deleteContract(id);
        return ResponseEntity.ok("🗑️ Contract deleted.");
    }

    @GetMapping("/{id}/annexes")
    public ResponseEntity<List<ContractAnnex>> getAnnexesByContractId(@PathVariable int id) {
        return ResponseEntity.ok(contractService.getAnnexesByContractId(id));
    }
}
