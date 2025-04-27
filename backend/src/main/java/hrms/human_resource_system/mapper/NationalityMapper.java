package hrms.human_resource_system.mapper;

import hrms.human_resource_system.dto.NationalityResponseDTO;
import hrms.human_resource_system.model.Nationality;

public class NationalityMapper {

    public static NationalityResponseDTO toDTO(Nationality nationality) {
        if (nationality == null) return null;

        NationalityResponseDTO dto = new NationalityResponseDTO();
        dto.setId(nationality.getNationalityId());
        dto.setCountry(nationality.getName());
        return dto;
    }

    public static Nationality toEntity(NationalityResponseDTO dto) {
        if (dto == null) return null;

        Nationality nationality = new Nationality();
        nationality.setNationalityId(dto.getId());
        nationality.setName(dto.getCountry());
        // userId and isActive need to be set manually since DTO doesn’t include them
        return nationality;
    }
}
