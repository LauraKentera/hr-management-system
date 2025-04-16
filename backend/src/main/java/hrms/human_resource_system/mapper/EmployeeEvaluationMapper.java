package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.EmployeeEvaluationRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeEvaluationResponseDTO;
import main.java.hrms.human_resource_system.model.EmployeeEvaluation;

import java.time.LocalDate;

public class EmployeeEvaluationMapper {

    public static EmployeeEvaluation toEntity(EmployeeEvaluationRequestDTO dto) {
        EmployeeEvaluation eval = new EmployeeEvaluation();
        eval.setUserId(dto.getEmployeeId());
        eval.setScore(dto.getScore());
        eval.setEvaluationDate(LocalDate.now()); // assuming evaluations are done "now"
        return eval;
    }

    public static EmployeeEvaluationResponseDTO toDTO(EmployeeEvaluation eval) {
        EmployeeEvaluationResponseDTO dto = new EmployeeEvaluationResponseDTO();
        dto.setId(eval.getEvaluationId());
        dto.setEmployeeId(eval.getUserId());
        dto.setScore(eval.getScore());
        return dto;
    }
}
