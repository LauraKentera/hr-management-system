package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.ContractAnnex;
import main.java.hrms.human_resource_system.exception.DLException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContractAnnexDAO {

    // Insert - Insert a new ContractAnnex
    public void insert(ContractAnnex entity) {
        String sql = "INSERT INTO ContractAnnex (contract_id, document_path, description) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, entity.getContractId());
            ps.setString(2, entity.getDocumentPath());
            ps.setString(3, entity.getDescription());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DLException("Creating ContractAnnex failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setAnnexId(generatedKeys.getInt(1));
                } else {
                    throw new DLException("Creating ContractAnnex failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DLException("Error creating ContractAnnex", e);
        }
    }

    // Read - Get a single ContractAnnex by ID
    public Optional<ContractAnnex> getById(int annexId) {
        String sql = "SELECT * FROM ContractAnnex WHERE annex_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, annexId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new ContractAnnex(
                        rs.getInt("annex_id"),
                        rs.getInt("contract_id"),
                        rs.getString("document_path"),
                        rs.getString("description")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DLException("Error retrieving ContractAnnex with ID " + annexId, e);
        }
        return Optional.empty();
    }

    // Read - Get all annexes (with optional pagination)
    public List<ContractAnnex> getAll() {
        String sql = "SELECT * FROM ContractAnnex";
        List<ContractAnnex> annexes = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                annexes.add(new ContractAnnex(
                    rs.getInt("annex_id"),
                    rs.getInt("contract_id"),
                    rs.getString("document_path"),
                    rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            throw new DLException("Error retrieving all ContractAnnexes", e);
        }
        return annexes;
    }

    // Update - existing method remains the same
    public void update(ContractAnnex entity) {
        String sql = "UPDATE ContractAnnex SET contract_id = ?, document_path = ?, description = ? WHERE annex_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, entity.getContractId());
            ps.setString(2, entity.getDocumentPath());
            ps.setString(3, entity.getDescription());
            ps.setInt(4, entity.getAnnexId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DLException("Updating ContractAnnex failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DLException("Error updating ContractAnnex with ID " + entity.getAnnexId(), e);
        }
    }

    // Delete - Remove a ContractAnnex by ID
    public void delete(int annexId) {
        String sql = "DELETE FROM ContractAnnex WHERE annex_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, annexId);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DLException("Deleting ContractAnnex failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DLException("Error deleting ContractAnnex with ID " + annexId, e);
        }
    }

    // Existing method for getting annexes by contract ID
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