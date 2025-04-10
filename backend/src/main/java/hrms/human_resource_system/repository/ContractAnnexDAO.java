package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.ContractAnnex;
import main.java.hrms.human_resource_system.exception.DLException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContractAnnexDAO {

    // Retrieve annexes associated with a specific contract ID
    public List<ContractAnnex> getAnnexesByContractId(int contractId) {
        String sql = "SELECT * FROM ContractAnnex WHERE contract_id = ?";
        List<ContractAnnex> annexes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, contractId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ContractAnnex annex = new ContractAnnex(
                            rs.getInt("annex_id"),
                            rs.getInt("contract_id"),
                            rs.getString("document_path"),
                            rs.getString("description")
                    );
                    annexes.add(annex);
                }
            }

        } catch (SQLException e) {
            throw new DLException("Error retrieving annexes for contract ID " + contractId, e);
        }

        return annexes;
    }
}
