package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.model.Payroll;
import main.java.hrms.human_resource_system.exception.DLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DLException("Error inserting payroll", e);
        }
    }

    public void update(Payroll payroll) {
        String sql = "UPDATE Payroll SET employee_id = ?, period_start = ?, period_end = ?, base_salary = ?, bonus = ?, deductions = ?, net_pay = ?, payment_date = ?, status = ? WHERE payroll_id = ?";
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
        } catch (SQLException e) {
            throw new DLException("Error updating payroll with ID " + payroll.getPayrollId(), e);
        }
    }

    public void delete(int payrollId) {
        String sql = "DELETE FROM Payroll WHERE payroll_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, payrollId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DLException("Error deleting payroll with ID " + payrollId, e);
        }
    }
}