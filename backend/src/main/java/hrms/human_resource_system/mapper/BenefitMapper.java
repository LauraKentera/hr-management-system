package hrms.human_resource_system.mapper;

import hrms.human_resource_system.dto.BenefitRequestDTO;
import hrms.human_resource_system.dto.BenefitResponseDTO;
import hrms.human_resource_system.model.Benefit;

public class BenefitMapper {

    public static BenefitResponseDTO toDTO(Benefit benefit) {
        BenefitResponseDTO dto = new BenefitResponseDTO();
        dto.setId(benefit.getBenefitId());
        dto.setName(benefit.getName());
        dto.setDescription(benefit.getDescription());
        return dto;
    }

    public static Benefit toEntity(BenefitRequestDTO dto) {
        Benefit benefit = new Benefit();
        benefit.setName(dto.getName());
        benefit.setDescription(dto.getDescription());
        return benefit;
    }
}
