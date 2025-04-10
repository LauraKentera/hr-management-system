package main.java.hrms.human_resource_system.repository;

import main.java.hrms.human_resource_system.exception.DLException;
import main.java.hrms.human_resource_system.model.EmployeeBankAccount;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeBankAccountDAO {

    public EmployeeBankAccount getById(int employeeId) {
        String sql = "SELECT * FROM EmployeeBankAccount WHERE employee_id = ?";
        EmployeeBankAccount account = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    public List<EmployeeBankAccount> getAll() {
        String sql = "SELECT * FROM EmployeeBankAccount";
        List<EmployeeBankAccount> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(EmployeeBankAccount account) {
        String sql = "INSERT INTO EmployeeBankAccount (employee_id, bank_name, account_number, iban) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, account.getEmployeeId());
            ps.setString(2, account.getBankName());
            ps.setString(3, account.getAccountNumber());

            if (account.getIban() != null)
                ps.setString(4, account.getIban());
            else
                ps.setNull(4, Types.VARCHAR);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int employeeId) {
        String sql = "DELETE FROM EmployeeBankAccount WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private EmployeeBankAccount mapResultSet(ResultSet rs) throws SQLException {
        return new EmployeeBankAccount(
                rs.getInt("employee_id"),
                rs.getString("bank_name"),
                rs.getString("account_number"),
                rs.getString("iban")
        );
    }

    public void update(EmployeeBankAccount account) {
        String sql = "UPDATE EmployeeBankAccount SET bank_name = ?, account_number = ?, iban = ? WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account.getBankName());
            ps.setString(2, account.getAccountNumber());

            if (account.getIban() != null)
                ps.setString(3, account.getIban());
            else
                ps.setNull(3, Types.VARCHAR);

            ps.setInt(4, account.getEmployeeId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error updating bank account for employee ID " + account.getEmployeeId(), e);
        }
    }

}

