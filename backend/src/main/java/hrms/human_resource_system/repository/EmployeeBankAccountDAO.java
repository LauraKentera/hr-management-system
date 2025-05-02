package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.EmployeeBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class EmployeeBankAccountDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeBankAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private EmployeeBankAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EmployeeBankAccount(
                rs.getInt("employee_id"),
                rs.getString("bank_name"),
                rs.getString("account_number"),
                rs.getString("iban")
        );
    }

    public EmployeeBankAccount getById(int employeeId) {
        String sql = "SELECT * FROM EmployeeBankAccount WHERE employee_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRow, employeeId);
        } catch (Exception e) {
            throw new DLException("Error retrieving bank account for employee ID " + employeeId, e);
        }
    }

    public List<EmployeeBankAccount> getAll() {
        String sql = "SELECT * FROM EmployeeBankAccount";
        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new DLException("Error retrieving all employee bank accounts", e);
        }
    }

    public void insert(EmployeeBankAccount account) {
        String sql = "INSERT INTO EmployeeBankAccount (employee_id, bank_name, account_number, iban) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement(sql);
                ps.setInt(1, account.getEmployeeId());
                ps.setString(2, account.getBankName());
                ps.setString(3, account.getAccountNumber());
                if (account.getIban() != null) {
                    ps.setString(4, account.getIban());
                } else {
                    ps.setNull(4, Types.VARCHAR);
                }
                return ps;
            });
        } catch (Exception e) {
            throw new DLException("Error inserting bank account for employee ID " + account.getEmployeeId(), e);
        }
    }

    public void update(EmployeeBankAccount account) {
        String sql = "UPDATE EmployeeBankAccount SET bank_name = ?, account_number = ?, iban = ? WHERE employee_id = ?";
        try {
            jdbcTemplate.update(sql,
                    account.getBankName(),
                    account.getAccountNumber(),
                    account.getIban(),
                    account.getEmployeeId()
            );
        } catch (Exception e) {
            throw new DLException("Error updating bank account for employee ID " + account.getEmployeeId(), e);
        }
    }

    public void delete(int employeeId) {
        String sql = "DELETE FROM EmployeeBankAccount WHERE employee_id = ?";
        try {
            jdbcTemplate.update(sql, employeeId);
        } catch (Exception e) {
            throw new DLException("Error deleting bank account for employee ID " + employeeId, e);
        }
    }
}
