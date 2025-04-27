package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.BenefitItem;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Repository
public class BenefitItemDAO {

    public BenefitItem getById(int id) {
        String sql = "SELECT * FROM BenefitItem WHERE benefit_item_id = ?";
        BenefitItem item = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                item = mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    public List<BenefitItem> getAll() {
        String sql = "SELECT * FROM BenefitItem";
        List<BenefitItem> list = new ArrayList<>();

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

    public List<BenefitItem> getByBenefitId(int benefitId) {
        List<BenefitItem> items = new ArrayList<>();
        String sql = "SELECT * FROM BenefitItem WHERE benefit_id = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
    
            ps.setInt(1, benefitId);
            ResultSet rs = ps.executeQuery();
    
            while (rs.next()) {
                BenefitItem item = new BenefitItem(
                    rs.getInt("benefit_item_id"),
                    rs.getInt("benefit_id"),
                    rs.getInt("region_id"),
                    rs.getDate("from_date").toLocalDate(),
                    rs.getDate("to_date") != null ? rs.getDate("to_date").toLocalDate() : null,
                    rs.getBoolean("allow_coefficient"),
                    rs.getBoolean("use_standard_amount"),
                    rs.getBigDecimal("amount"),
                    rs.getBigDecimal("coefficient")
                );
                items.add(item);
            }
    
        } catch (SQLException e) {
            throw new DLException("Error retrieving benefit items for benefit ID: " + benefitId, e);
        }
    
        return items;
    }
    

    public void insert(BenefitItem item) {
        String sql = "INSERT INTO BenefitItem (benefit_id, region_id, from_date, to_date, " +
                "allow_coefficient, use_standard_amount, amount, coefficient) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getBenefitId());

            if (item.getRegionId() != null)
                ps.setInt(2, item.getRegionId());
            else
                ps.setNull(2, Types.INTEGER);

            ps.setDate(3, Date.valueOf(item.getFromDate()));

            if (item.getToDate() != null)
                ps.setDate(4, Date.valueOf(item.getToDate()));
            else
                ps.setNull(4, Types.DATE);

            ps.setBoolean(5, item.isAllowCoefficient());
            ps.setBoolean(6, item.isUseStandardAmount());

            if (item.getAmount() != null)
                ps.setBigDecimal(7, item.getAmount());
            else
                ps.setNull(7, Types.DECIMAL);

            if (item.getCoefficient() != null)
                ps.setBigDecimal(8, item.getCoefficient());
            else
                ps.setNull(8, Types.DECIMAL);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM BenefitItem WHERE benefit_item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private BenefitItem mapResultSet(ResultSet rs) throws SQLException {
        return new BenefitItem(
                rs.getInt("benefit_item_id"),
                rs.getInt("benefit_id"),
                rs.getObject("region_id") != null ? rs.getInt("region_id") : null,
                rs.getDate("from_date").toLocalDate(),
                rs.getDate("to_date") != null ? rs.getDate("to_date").toLocalDate() : null,
                rs.getBoolean("allow_coefficient"),
                rs.getBoolean("use_standard_amount"),
                rs.getBigDecimal("amount"),
                rs.getBigDecimal("coefficient")
        );
    }

    public void update(int id, BenefitItem item) {
        String sql = "UPDATE BenefitItem SET benefit_id = ?, region_id = ?, from_date = ?, to_date = ?, " +
                "allow_coefficient = ?, use_standard_amount = ?, amount = ?, coefficient = ? " +
                "WHERE benefit_item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getBenefitId());

            if (item.getRegionId() != null)
                ps.setInt(2, item.getRegionId());
            else
                ps.setNull(2, Types.INTEGER);

            ps.setDate(3, Date.valueOf(item.getFromDate()));

            if (item.getToDate() != null)
                ps.setDate(4, Date.valueOf(item.getToDate()));
            else
                ps.setNull(4, Types.DATE);

            ps.setBoolean(5, item.isAllowCoefficient());
            ps.setBoolean(6, item.isUseStandardAmount());

            if (item.getAmount() != null)
                ps.setBigDecimal(7, item.getAmount());
            else
                ps.setNull(7, Types.DECIMAL);

            if (item.getCoefficient() != null)
                ps.setBigDecimal(8, item.getCoefficient());
            else
                ps.setNull(8, Types.DECIMAL);

            ps.setInt(9, id); // Set the ID for the WHERE clause

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DLException("Error updating BenefitItem with ID " + id, e);

        }
    }

}

