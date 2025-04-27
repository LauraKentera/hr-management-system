package hrms.human_resource_system.mapper;

import hrms.human_resource_system.dto.EmployeeEvaluationRequestDTO;
import hrms.human_resource_system.dto.EmployeeEvaluationResponseDTO;
import hrms.human_resource_system.model.EmployeeEvaluation;
import java.time.LocalDateTime;

public class EmployeeEvaluationMapper {

    // Mapping DTO to Entity
    public static EmployeeEvaluation toEntity(EmployeeEvaluationRequestDTO dto) {
        EmployeeEvaluation evaluation = new EmployeeEvaluation();

        // Directly use the LocalDateTime from DTO as evaluationDate
        evaluation.setEvaluationDate(dto.getEvaluationDate());  // Now directly sets LocalDateTime

        evaluation.setComment(dto.getComment());
        evaluation.setScore(dto.getScore());
        evaluation.setUserId(dto.getUserId());  // Here employeeId is being used as userId
        evaluation.setEntryDate(dto.getEntryDate()); // Use entryDate from DTO

        return evaluation;
    }

    // Mapping Entity to DTO
    public static EmployeeEvaluationResponseDTO toDTO(EmployeeEvaluation entity) {
        EmployeeEvaluationResponseDTO dto = new EmployeeEvaluationResponseDTO();
        dto.setEvaluationId(entity.getEvaluationId());

        // Directly use the LocalDateTime from Entity as evaluationDate
        dto.setEvaluationDate(entity.getEvaluationDate());  // Keep as LocalDateTime

        dto.setComment(entity.getComment());
        dto.setScore(entity.getScore());
        dto.setUserId(entity.getUserId());
        dto.setEntryDate(entity.getEntryDate()); // Same for entryDate

        return dto;
    }
}
