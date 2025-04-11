package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.EmploymentContract;
import main.java.hrms.human_resource_system.exception.DLException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContractDAO {

    public List<EmploymentContract> getAll() {
        String sql = "SELECT * FROM EmploymentContract";
        List<EmploymentContract> contracts = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                contracts.add(new EmploymentContract(
                    rs.getInt("contract_id"),
                    rs.getInt("employee_id"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null,
                    rs.getInt("position_id"),
                    rs.getBigDecimal("salary"),
                    rs.getString("contract_type"),
                    rs.getDate("signed_date") != null ? rs.getDate("signed_date").toLocalDate() : null,
                    rs.getString("document_path")
                ));
            }
        } catch (SQLException e) {
            throw new DLException("Error fetching contracts", e);
        }
        return contracts;
    }

    public void insert(EmploymentContract contract) {
        String sql = "INSERT INTO EmploymentContract (employee_id, start_date, end_date, position_id, salary, contract_type, signed_date, document_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, contract.getEmployeeId());
            ps.setDate(2, Date.valueOf(contract.getStartDate()));
            ps.setDate(3, contract.getEndDate() != null ? Date.valueOf(contract.getEndDate()) : null);
            ps.setInt(4, contract.getPositionId());
            ps.setBigDecimal(5, contract.getSalary());
            ps.setString(6, contract.getContractType());
            ps.setDate(7, contract.getSignedDate() != null ? Date.valueOf(contract.getSignedDate()) : null);
            ps.setString(8, contract.getDocumentPath());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DLException("Error inserting contract", e);
        }
    }

    public void update(EmploymentContract contract) {
        String sql = "UPDATE EmploymentContract SET employee_id = ?, start_date = ?, end_date = ?, position_id = ?, salary = ?, contract_type = ?, signed_date = ?, document_path = ? WHERE contract_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, contract.getEmployeeId());
            ps.setDate(2, Date.valueOf(contract.getStartDate()));
            ps.setDate(3, contract.getEndDate() != null ? Date.valueOf(contract.getEndDate()) : null);
            ps.setInt(4, contract.getPositionId());
            ps.setBigDecimal(5, contract.getSalary());
            ps.setString(6, contract.getContractType());
            ps.setDate(7, contract.getSignedDate() != null ? Date.valueOf(contract.getSignedDate()) : null);
            ps.setString(8, contract.getDocumentPath());
            ps.setInt(9, contract.getContractId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DLException("Error updating contract with ID " + contract.getContractId(), e);
        }
    }

    public void delete(int contractId) {
        String sql = "DELETE FROM EmploymentContract WHERE contract_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, contractId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DLException("Error deleting contract with ID " + contractId, e);
        }
    }

    public BigDecimal getBaseSalary(int employeeId, LocalDate from, LocalDate to) {
        String sql = "SELECT salary FROM EmploymentContract WHERE employee_id = ? AND start_date <= ? " +
                "AND (end_date IS NULL OR end_date >= ?) ORDER BY start_date DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("salary");
            }

        } catch (SQLException e) {
            throw new DLException("Error fetching salary for employee ID " + employeeId, e);
        }

        return null;
    }


}