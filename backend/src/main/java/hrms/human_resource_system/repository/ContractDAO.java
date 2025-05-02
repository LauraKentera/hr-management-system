package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.EmploymentContract;
import hrms.human_resource_system.util.AuditLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ContractDAO {

    private final JdbcTemplate jdbcTemplate;
    private final AuditLogger auditLogger;

    @Autowired
    public ContractDAO(JdbcTemplate jdbcTemplate, AuditLogger auditLogger) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditLogger = auditLogger;
    }

    private EmploymentContract mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EmploymentContract(
                rs.getInt("contract_id"),
                rs.getInt("employee_id"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null,
                rs.getInt("position_id"),
                rs.getBigDecimal("salary"),
                rs.getString("contract_type"),
                rs.getDate("signed_date") != null ? rs.getDate("signed_date").toLocalDate() : null,
                rs.getString("document_path")
        );
    }

    public List<EmploymentContract> getAll() {
        String sql = "SELECT * FROM EmploymentContract";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching contracts", e);
        }
    }

    public EmploymentContract getById(int contractId) {
        String sql = "SELECT * FROM EmploymentContract WHERE contract_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, contractId);
        } catch (Exception e) {
            throw new DLException("Error fetching contract with ID " + contractId, e);
        }
    }

    public void insert(EmploymentContract contract, int performedBy) {
        String sql = """
            INSERT INTO EmploymentContract 
            (employee_id, start_date, end_date, position_id, salary, contract_type, signed_date, document_path)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, contract.getEmployeeId());
                ps.setDate(2, Date.valueOf(contract.getStartDate()));
                ps.setObject(3, contract.getEndDate() != null ? Date.valueOf(contract.getEndDate()) : null, Types.DATE);
                ps.setInt(4, contract.getPositionId());
                ps.setBigDecimal(5, contract.getSalary());
                ps.setString(6, contract.getContractType());
                ps.setObject(7, contract.getSignedDate() != null ? Date.valueOf(contract.getSignedDate()) : null, Types.DATE);
                ps.setString(8, contract.getDocumentPath());
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                contract.setContractId(keyHolder.getKey().intValue());
            }

            auditLogger.logChange("EmploymentContract", contract.getContractId(), "INSERT", performedBy, null, contract);
        } catch (Exception e) {
            throw new DLException("Error inserting contract", e);
        }
    }

    public void update(EmploymentContract contract, int performedBy) {
        String sql = """
            UPDATE EmploymentContract 
            SET employee_id = ?, start_date = ?, end_date = ?, position_id = ?, salary = ?, contract_type = ?, 
                signed_date = ?, document_path = ?
            WHERE contract_id = ?
        """;

        EmploymentContract oldContract = getById(contract.getContractId());

        try {
            jdbcTemplate.update(sql,
                    contract.getEmployeeId(),
                    Date.valueOf(contract.getStartDate()),
                    contract.getEndDate() != null ? Date.valueOf(contract.getEndDate()) : null,
                    contract.getPositionId(),
                    contract.getSalary(),
                    contract.getContractType(),
                    contract.getSignedDate() != null ? Date.valueOf(contract.getSignedDate()) : null,
                    contract.getDocumentPath(),
                    contract.getContractId()
            );

            auditLogger.logChange("EmploymentContract", contract.getContractId(), "UPDATE", performedBy, oldContract, contract);
        } catch (Exception e) {
            throw new DLException("Error updating contract with ID " + contract.getContractId(), e);
        }
    }

    public void delete(int contractId, int performedBy) {
        String sql = "DELETE FROM EmploymentContract WHERE contract_id = ?";
        EmploymentContract oldContract = getById(contractId);

        try {
            jdbcTemplate.update(sql, contractId);
            auditLogger.logChange("EmploymentContract", contractId, "DELETE", performedBy, oldContract, null);
        } catch (Exception e) {
            throw new DLException("Error deleting contract with ID " + contractId, e);
        }
    }

    public BigDecimal getBaseSalary(int employeeId, LocalDate from, LocalDate to) {
        String sql = """
            SELECT salary FROM EmploymentContract
            WHERE employee_id = ? AND start_date <= ? AND (end_date IS NULL OR end_date >= ?)
            ORDER BY start_date DESC LIMIT 1
        """;

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getBigDecimal("salary"),
                    employeeId, Date.valueOf(from), Date.valueOf(to));
        } catch (Exception e) {
            throw new DLException("Error fetching salary for employee ID " + employeeId, e);
        }
    }
}
