package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.EmployeeEvaluationRequestDTO;
import main.java.hrms.human_resource_system.dto.EmployeeEvaluationResponseDTO;
import main.java.hrms.human_resource_system.model.EmployeeEvaluation;

import java.time.LocalDate;

public class EmployeeEvaluationMapper {

    public static EmployeeEvaluation toEntity(EmployeeEvaluationRequestDTO dto) {
        EmployeeEvaluation eval = new EmployeeEvaluation();
        entity.setEvaluatorName(dto.getEvaluatorName());
        entity.setComments(dto.getComments());
        entity.setScore(dto.getScore());
        return eval;
    }

    public static EmployeeEvaluationResponseDTO toDTO(EmployeeEvaluation eval) {
        EmployeeEvaluationResponseDTO dto = new EmployeeEvaluationResponseDTO();
        dto.setEvaluatorName(entity.getEvaluatorName());
        dto.setComments(entity.getComments());
        dto.setScore(entity.getScore());
        return dto;
    }
}
