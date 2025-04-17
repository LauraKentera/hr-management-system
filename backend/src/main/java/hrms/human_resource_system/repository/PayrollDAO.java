package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.Payroll;
import main.java.hrms.human_resource_system.exception.DLException;
import main.resources.util.AuditLogger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PayrollDAO {

    public List<Payroll> getAll() {
        String sql = "SELECT * FROM Payroll";
        List<Payroll> payrolls = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                payrolls.add(new Payroll(
                        rs.getInt("payroll_id"),
                        rs.getInt("employee_id"),
                        rs.getDate("period_start").toLocalDate(),
                        rs.getDate("period_end").toLocalDate(),
                        rs.getBigDecimal("base_salary"),
                        rs.getBigDecimal("bonus"),
                        rs.getBigDecimal("deductions"),
                        rs.getBigDecimal("net_pay"),
                        rs.getDate("payment_date") != null ? rs.getDate("payment_date").toLocalDate() : null,
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new DLException("Error fetching payrolls", e);
        }
        return payrolls;
    }

    public void insert(Payroll payroll) {
        String sql = "INSERT INTO Payroll (employee_id, period_start, period_end, base_salary, bonus, deductions, net_pay, payment_date, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, payroll.getEmployeeId());
            ps.setDate(2, Date.valueOf(payroll.getPeriodStart()));
            ps.setDate(3, Date.valueOf(payroll.getPeriodEnd()));
            ps.setBigDecimal(4, payroll.getBaseSalary());
            ps.setBigDecimal(5, payroll.getBonus());
            ps.setBigDecimal(6, payroll.getDeductions());
            ps.setBigDecimal(7, payroll.getNetPay());
            ps.setDate(8, payroll.getPaymentDate() != null ? Date.valueOf(payroll.getPaymentDate()) : null);
            ps.setString(9, payroll.getStatus());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payroll.setPayrollId(generatedKeys.getInt(1));
                }
            }

            // Log the change
            AuditLogger.logChange("Payroll", payroll.getPayrollId(), "INSERT", 1, null, payroll);

        } catch (SQLException e) {
            throw new DLException("Error inserting payroll", e);
        }
    }

    public void update(Payroll payroll) {
        String sql = "UPDATE Payroll SET employee_id = ?, period_start = ?, period_end = ?, base_salary = ?, bonus = ?, deductions = ?, net_pay = ?, payment_date = ?, status = ? WHERE payroll_id = ?";
        Payroll oldPayroll = getById(payroll.getPayrollId());

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, payroll.getEmployeeId());
            ps.setDate(2, Date.valueOf(payroll.getPeriodStart()));
            ps.setDate(3, Date.valueOf(payroll.getPeriodEnd()));
            ps.setBigDecimal(4, payroll.getBaseSalary());
            ps.setBigDecimal(5, payroll.getBonus());
            ps.setBigDecimal(6, payroll.getDeductions());
            ps.setBigDecimal(7, payroll.getNetPay());
            ps.setDate(8, payroll.getPaymentDate() != null ? Date.valueOf(payroll.getPaymentDate()) : null);
            ps.setString(9, payroll.getStatus());
            ps.setInt(10, payroll.getPayrollId());
            ps.executeUpdate();

            AuditLogger.logChange("Payroll", payroll.getPayrollId(), "UPDATE", 1, oldPayroll, payroll);

        } catch (SQLException e) {
            throw new DLException("Error updating payroll", e);
        }
    }

    public void delete(int payrollId) {
        String sql = "DELETE FROM Payroll WHERE payroll_id = ?";
        Payroll oldPayroll = getById(payrollId);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, payrollId);
            ps.executeUpdate();

            AuditLogger.logChange("Payroll", payrollId, "DELETE", 1, oldPayroll, null);

        } catch (SQLException e) {
            throw new DLException("Error deleting payroll", e);
        }
    }

    public Payroll getById(int payrollId) {
        String sql = "SELECT * FROM Payroll WHERE payroll_id = ?";
        Payroll payroll = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, payrollId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    payroll = new Payroll(
                            rs.getInt("payroll_id"),
                            rs.getInt("employee_id"),
                            rs.getDate("period_start").toLocalDate(),
                            rs.getDate("period_end").toLocalDate(),
                            rs.getBigDecimal("base_salary"),
                            rs.getBigDecimal("bonus"),
                            rs.getBigDecimal("deductions"),
                            rs.getBigDecimal("net_pay"),
                            rs.getDate("payment_date") != null ? rs.getDate("payment_date").toLocalDate() : null,
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DLException("Error retrieving payroll with ID " + payrollId, e);
        }

        return payroll;
    }
}
