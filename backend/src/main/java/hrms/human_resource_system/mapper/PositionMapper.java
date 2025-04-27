package hrms.human_resource_system.mapper;

import hrms.human_resource_system.dto.PositionRequestDTO;
import hrms.human_resource_system.dto.PositionResponseDTO;
import hrms.human_resource_system.model.Position;

public class PositionMapper {

    // Convert PositionRequestDTO to Position
    public static Position fromRequestDTO(PositionRequestDTO requestDTO) {
        Position position = new Position();
        position.setName(requestDTO.getTitle()); // Mapping 'title' to 'name'
        position.setShortName(requestDTO.getDescription()); // Mapping 'description' to 'shortName'
        // Additional mapping logic if required
        return position;
    }

    // Convert Position to PositionResponseDTO
    public static PositionResponseDTO toResponseDTO(Position position) {
        PositionResponseDTO responseDTO = new PositionResponseDTO();
        responseDTO.setId(position.getPositionId());
        responseDTO.setTitle(position.getName()); // Mapping 'name' to 'title'
        responseDTO.setDescription(position.getShortName()); // Mapping 'shortName' to 'description'
        return responseDTO;
    }
}
