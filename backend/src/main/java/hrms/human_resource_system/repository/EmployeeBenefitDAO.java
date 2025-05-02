package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.Benefit;
import hrms.human_resource_system.model.Employee;
import hrms.human_resource_system.model.EmployeeBenefit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class EmployeeBenefitDAO {

    private final JdbcTemplate jdbcTemplate;
    private final EmployeeDAO employeeDAO;
    private final BenefitDAO benefitDAO;

    @Autowired
    public EmployeeBenefitDAO(JdbcTemplate jdbcTemplate,
                              EmployeeDAO employeeDAO,
                              BenefitDAO benefitDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeDAO = employeeDAO;
        this.benefitDAO = benefitDAO;
    }

    public EmployeeBenefit getById(int id) {
        String sql = "SELECT * FROM EmployeeBenefit WHERE employee_benefit_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapRow(rs), id);
        } catch (Exception e) {
            throw new DLException("Error retrieving EmployeeBenefit with ID " + id, e);
        }
    }

    public List<EmployeeBenefit> getAll() {
        String sql = "SELECT * FROM EmployeeBenefit";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
        } catch (Exception e) {
            throw new DLException("Error retrieving all EmployeeBenefits", e);
        }
    }

    public void insert(EmployeeBenefit employeeBenefit) {
        String sql = """
            INSERT INTO EmployeeBenefit 
            (employee_id, benefit_id, from_date, to_date, use_standard_amount, amount, coefficient, description, is_active)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, employeeBenefit.getEmployee().getId());
                ps.setInt(2, employeeBenefit.getBenefit().getBenefitId());
                ps.setDate(3, Date.valueOf(employeeBenefit.getFromDate()));
                if (employeeBenefit.getToDate() != null)
                    ps.setDate(4, Date.valueOf(employeeBenefit.getToDate()));
                else
                    ps.setNull(4, Types.DATE);
                ps.setBoolean(5, employeeBenefit.isUseStandardAmount());
                ps.setBigDecimal(6, employeeBenefit.getAmount());
                ps.setBigDecimal(7, employeeBenefit.getCoefficient());
                ps.setString(8, employeeBenefit.getDescription());
                ps.setBoolean(9, employeeBenefit.isActive());
                return ps;
            });
        } catch (Exception e) {
            throw new DLException("Error inserting EmployeeBenefit", e);
        }
    }

    public void update(int id, EmployeeBenefit employeeBenefit) {
        String sql = """
            UPDATE EmployeeBenefit 
            SET employee_id = ?, benefit_id = ?, from_date = ?, to_date = ?, use_standard_amount = ?, 
                amount = ?, coefficient = ?, description = ?, is_active = ?
            WHERE employee_benefit_id = ?
        """;
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, employeeBenefit.getEmployee().getId());
                ps.setInt(2, employeeBenefit.getBenefit().getBenefitId());
                ps.setDate(3, Date.valueOf(employeeBenefit.getFromDate()));
                if (employeeBenefit.getToDate() != null)
                    ps.setDate(4, Date.valueOf(employeeBenefit.getToDate()));
                else
                    ps.setNull(4, Types.DATE);
                ps.setBoolean(5, employeeBenefit.isUseStandardAmount());
                ps.setBigDecimal(6, employeeBenefit.getAmount());
                ps.setBigDecimal(7, employeeBenefit.getCoefficient());
                ps.setString(8, employeeBenefit.getDescription());
                ps.setBoolean(9, employeeBenefit.isActive());
                ps.setInt(10, id);
                return ps;
            });
        } catch (Exception e) {
            throw new DLException("Error updating EmployeeBenefit with ID " + id, e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM EmployeeBenefit WHERE employee_benefit_id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new DLException("Error deleting EmployeeBenefit with ID " + id, e);
        }
    }

    private EmployeeBenefit mapRow(ResultSet rs) throws SQLException {
        Employee employee = employeeDAO.getById(rs.getInt("employee_id"));
        Benefit benefit = benefitDAO.getById(rs.getInt("benefit_id"));

        return new EmployeeBenefit(
                rs.getInt("employee_benefit_id"),
                employee,
                benefit,
                rs.getDate("from_date").toLocalDate(),
                rs.getDate("to_date") != null ? rs.getDate("to_date").toLocalDate() : null,
                rs.getBoolean("use_standard_amount"),
                rs.getBigDecimal("amount"),
                rs.getBigDecimal("coefficient"),
                rs.getString("description"),
                rs.getBoolean("is_active")
        );
    }
}
