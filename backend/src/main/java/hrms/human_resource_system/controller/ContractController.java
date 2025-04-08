package main.java.hrms.human_resource_system.controller;

import main.java.hrms.human_resource_system.model.EmploymentContract;
import main.java.hrms.human_resource_system.model.ContractAnnex;
import main.java.hrms.human_resource_system.service.ContractService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController() {
        this.contractService = new ContractService();
    }

    @GetMapping
    public List<EmploymentContract> getAllContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/{id}")
    public EmploymentContract getContractById(@PathVariable int id) {
        return contractService.getContractById(id);
    }

    @PostMapping
    public void addContract(@RequestBody EmploymentContract contract) {
        contractService.addContract(contract);
    }

    @PutMapping("/{id}")
    public void updateContract(@PathVariable int id, @RequestBody EmploymentContract contract) {
        contract.setContractId(id);
        contractService.updateContract(contract);
    }

    @DeleteMapping("/{id}")
    public void deleteContract(@PathVariable int id) {
        contractService.deleteContract(id);
    }

    @GetMapping("/{id}/annexes")
    public List<ContractAnnex> getAnnexesByContractId(@PathVariable int id) {
        return contractService.getAnnexesByContractId(id);
    }
}