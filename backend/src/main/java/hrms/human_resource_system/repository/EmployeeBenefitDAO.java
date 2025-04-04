package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.Employee;
import main.java.hrms.human_resource_system.model.EmployeeBenefit;
import main.java.hrms.human_resource_system.model.Benefit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeBenefitDAO {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final BenefitDAO benefitDAO = new BenefitDAO();

    public EmployeeBenefit getById(int id) {
        String sql = "SELECT * FROM EmployeeBenefit WHERE employee_benefit_id = ?";
        EmployeeBenefit employeeBenefit = null;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee employee = employeeDAO.getById(rs.getInt("employee_id"));
                    Benefit benefit = benefitDAO.getById(rs.getInt("benefit_id"));

                    employeeBenefit = new EmployeeBenefit(
                            rs.getInt("employee_benefit_id"),
                            employee,
                            benefit,
                            rs.getDate("from_date").toLocalDate(),
                            rs.getDate("to_date") != null ? rs.getDate("to_date").toLocalDate() : null,
                            rs.getBoolean("use_standard_amount"),
                            rs.getBigDecimal("amount"),
                            rs.getBigDecimal("coefficient"),
                            rs.getString("description"),
                            rs.getBoolean("is_active"));
                }
            }

        } catch (SQLException e) {
            throw new DLException("Error retrieving EmployeeBenefit with ID " + id, e);
        }

        return employeeBenefit;
    }

    public List<EmployeeBenefit> getAll() {
        String sql = "SELECT * FROM EmployeeBenefit";
        List<EmployeeBenefit> employeeBenefits = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee employee = employeeDAO.getById(rs.getInt("employee_id"));
                Benefit benefit = benefitDAO.getById(rs.getInt("benefit_id"));

                employeeBenefits.add(new EmployeeBenefit(
                        rs.getInt("employee_benefit_id"),
                        employee,
                        benefit,
                        rs.getDate("from_date").toLocalDate(),
                        rs.getDate("to_date") != null ? rs.getDate("to_date").toLocalDate() : null,
                        rs.getBoolean("use_standard_amount"),
                        rs.getBigDecimal("amount"),
                        rs.getBigDecimal("coefficient"),
                        rs.getString("description"),
                        rs.getBoolean("is_active")));
            }

        } catch (SQLException e) {
            throw new DLException("Error retrieving all EmployeeBenefits", e);
        }

        return employeeBenefits;
    }

    public void insert(EmployeeBenefit employeeBenefit) {
        String sql = "INSERT INTO EmployeeBenefit (employee_id, benefit_id, from_date, to_date, use_standard_amount, " +
                "amount, coefficient, description, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeBenefit.getEmployee().getId());
            ps.setInt(2, employeeBenefit.getBenefit().getBenefitId());
            ps.setDate(3, Date.valueOf(employeeBenefit.getFromDate()));
            if (employeeBenefit.getToDate() != null) {
                ps.setDate(4, Date.valueOf(employeeBenefit.getToDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setBoolean(5, employeeBenefit.isUseStandardAmount());
            ps.setBigDecimal(6, employeeBenefit.getAmount());
            ps.setBigDecimal(7, employeeBenefit.getCoefficient());
            ps.setString(8, employeeBenefit.getDescription());
            ps.setBoolean(9, employeeBenefit.isActive());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error inserting EmployeeBenefit", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM EmployeeBenefit WHERE employee_benefit_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error deleting EmployeeBenefit with ID " + id, e);
        }
    }
}
