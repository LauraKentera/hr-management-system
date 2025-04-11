package main.java.hrms.human_resource_system.mapper;

import main.java.hrms.human_resource_system.dto.BenefitItemRequestDTO;
import main.java.hrms.human_resource_system.dto.BenefitItemResponseDTO;
import main.java.hrms.human_resource_system.model.BenefitItem;

public class BenefitItemMapper {

    public static BenefitItem toEntity(BenefitItemRequestDTO dto) {
        BenefitItem item = new BenefitItem();
        item.setBenefitId(dto.getBenefitId());
        item.setRegionId(dto.getRegionId());
        item.setFromDate(dto.getFromDate());
        item.setToDate(dto.getToDate());
        item.setAllowCoefficient(dto.isAllowCoefficient());
        item.setUseStandardAmount(dto.isUseStandardAmount());
        item.setAmount(dto.getAmount());
        item.setCoefficient(dto.getCoefficient());
        return item;
    }

    public static BenefitItemResponseDTO toDTO(BenefitItem item) {
        BenefitItemResponseDTO dto = new BenefitItemResponseDTO();
        dto.setBenefitItemId(item.getBenefitItemId());
        dto.setBenefitId(item.getBenefitId());
        dto.setRegionId(item.getRegionId());
        dto.setFromDate(item.getFromDate());
        dto.setToDate(item.getToDate());
        dto.setAllowCoefficient(item.isAllowCoefficient());
        dto.setUseStandardAmount(item.isUseStandardAmount());
        dto.setAmount(item.getAmount());
        dto.setCoefficient(item.getCoefficient());
        return dto;
    }
}
