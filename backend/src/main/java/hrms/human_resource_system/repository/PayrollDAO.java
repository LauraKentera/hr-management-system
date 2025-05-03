package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Payroll;
import hrms.human_resource_system.util.AuditLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class PayrollDAO {

    private final JdbcTemplate jdbcTemplate;
    private final AuditLogger auditLogger;

    @Autowired
    public PayrollDAO(JdbcTemplate jdbcTemplate, AuditLogger auditLogger) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditLogger = auditLogger;
    }

    private Payroll mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Payroll(
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

    public List<Payroll> getAll() {
        String sql = "SELECT * FROM Payroll";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error fetching payrolls", e);
        }
    }

    public Payroll getById(int payrollId) {
        String sql = "SELECT * FROM Payroll WHERE payroll_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, payrollId);
        } catch (Exception e) {
            throw new DLException("Error retrieving payroll with ID " + payrollId, e);
        }
    }

    public void insert(Payroll payroll, int performedBy) {
        String sql = """
            INSERT INTO Payroll 
            (employee_id, period_start, period_end, base_salary, bonus, deductions, net_pay, payment_date, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, payroll.getEmployeeId());
                ps.setDate(2, Date.valueOf(payroll.getPeriodStart()));
                ps.setDate(3, Date.valueOf(payroll.getPeriodEnd()));
                ps.setBigDecimal(4, payroll.getBaseSalary());
                ps.setBigDecimal(5, payroll.getBonus());
                ps.setBigDecimal(6, payroll.getDeductions());
                ps.setBigDecimal(7, payroll.getNetPay());
                if (payroll.getPaymentDate() != null)
                    ps.setDate(8, Date.valueOf(payroll.getPaymentDate()));
                else
                    ps.setNull(8, Types.DATE);
                ps.setString(9, payroll.getStatus());
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                payroll.setPayrollId(keyHolder.getKey().intValue());
            }

            try {
                auditLogger.logChange("Payroll", payroll.getPayrollId(), "CREATE", performedBy, null, payroll);
            } catch (Exception logEx) {
                System.err.println("Audit log failed: " + logEx.getMessage());
            }

        } catch (Exception e) {
            throw new DLException("Error inserting payroll", e);
        }
    }

    public void update(Payroll payroll, int performedBy) {
        String sql = """
            UPDATE Payroll SET 
            employee_id = ?, period_start = ?, period_end = ?, base_salary = ?, bonus = ?, deductions = ?, 
            net_pay = ?, payment_date = ?, status = ? 
            WHERE payroll_id = ?
        """;

        Payroll oldPayroll = getById(payroll.getPayrollId());

        try {
            jdbcTemplate.update(sql,
                    payroll.getEmployeeId(),
                    Date.valueOf(payroll.getPeriodStart()),
                    Date.valueOf(payroll.getPeriodEnd()),
                    payroll.getBaseSalary(),
                    payroll.getBonus(),
                    payroll.getDeductions(),
                    payroll.getNetPay(),
                    payroll.getPaymentDate() != null ? Date.valueOf(payroll.getPaymentDate()) : null,
                    payroll.getStatus(),
                    payroll.getPayrollId()
            );

            try {
                auditLogger.logChange("Payroll", payroll.getPayrollId(), "UPDATE", performedBy, oldPayroll, payroll);
            } catch (Exception logEx) {
                System.err.println("Audit log failed: " + logEx.getMessage());
            }

        } catch (Exception e) {
            throw new DLException("Error updating payroll", e);
        }
    }

    public void delete(int payrollId, int performedBy) {
        String sql = "DELETE FROM Payroll WHERE payroll_id = ?";
        Payroll oldPayroll = getById(payrollId);

        try {
            jdbcTemplate.update(sql, payrollId);

            try {
                auditLogger.logChange("Payroll", payrollId, "DELETE", performedBy, oldPayroll, null);
            } catch (Exception logEx) {
                System.err.println("Audit log failed: " + logEx.getMessage());
            }

        } catch (Exception e) {
            throw new DLException("Error deleting payroll", e);
        }
    }
}
