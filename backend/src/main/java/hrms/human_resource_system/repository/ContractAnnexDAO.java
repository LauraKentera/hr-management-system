package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.ContractAnnex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class ContractAnnexDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContractAnnexDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private ContractAnnex mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ContractAnnex(
                rs.getInt("annex_id"),
                rs.getInt("contract_id"),
                rs.getString("document_path"),
                rs.getString("description")
        );
    }

    public void insert(ContractAnnex entity) {
        String sql = "INSERT INTO ContractAnnex (contract_id, document_path, description) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, entity.getContractId());
                ps.setString(2, entity.getDocumentPath());
                ps.setString(3, entity.getDescription());
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                entity.setAnnexId(keyHolder.getKey().intValue());
            } else {
                throw new DLException("Creating ContractAnnex failed, no ID obtained.");
            }

        } catch (Exception e) {
            throw new DLException("Error creating ContractAnnex", e);
        }
    }

    public Optional<ContractAnnex> getById(int annexId) {
        String sql = "SELECT * FROM ContractAnnex WHERE annex_id = ?";
        try {
            ContractAnnex annex = jdbcTemplate.queryForObject(sql, this::mapRow, annexId);
            return Optional.ofNullable(annex);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<ContractAnnex> getAll() {
        String sql = "SELECT * FROM ContractAnnex";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error retrieving all ContractAnnexes", e);
        }
    }

    public void update(ContractAnnex entity) {
        String sql = "UPDATE ContractAnnex SET contract_id = ?, document_path = ?, description = ? WHERE annex_id = ?";

        try {
            int affected = jdbcTemplate.update(sql,
                    entity.getContractId(),
                    entity.getDocumentPath(),
                    entity.getDescription(),
                    entity.getAnnexId());

            if (affected == 0) {
                throw new DLException("Updating ContractAnnex failed, no rows affected.");
            }
        } catch (Exception e) {
            throw new DLException("Error updating ContractAnnex with ID " + entity.getAnnexId(), e);
        }
    }

    public void delete(int annexId) {
        String sql = "DELETE FROM ContractAnnex WHERE annex_id = ?";
        try {
            int affected = jdbcTemplate.update(sql, annexId);
            if (affected == 0) {
                throw new DLException("Deleting ContractAnnex failed, no rows affected.");
            }
        } catch (Exception e) {
            throw new DLException("Error deleting ContractAnnex with ID " + annexId, e);
        }
    }

    public List<ContractAnnex> getAnnexesByContractId(int contractId) {
        String sql = "SELECT * FROM ContractAnnex WHERE contract_id = ?";
        try {
            return jdbcTemplate.query(sql, this::mapRow, contractId);
        } catch (Exception e) {
            throw new DLException("Error retrieving annexes for contract ID " + contractId, e);
        }
    }
}
