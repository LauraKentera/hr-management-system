package hrms.human_resource_system.repository;

import hrms.human_resource_system.exception.DLException;
import hrms.human_resource_system.model.BenefitItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BenefitItemDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BenefitItemDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private BenefitItem mapResultSet(ResultSet rs, int rowNum) throws SQLException {
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

    public BenefitItem getById(int id) {
        String sql = "SELECT * FROM BenefitItem WHERE benefit_item_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapResultSet, id);
        } catch (Exception e) {
            throw new DLException("Error retrieving BenefitItem with ID " + id, e);
        }
    }

    public List<BenefitItem> getAll() {
        String sql = "SELECT * FROM BenefitItem";
        try {
            return jdbcTemplate.query(sql, this::mapResultSet);
        } catch (Exception e) {
            throw new DLException("Error retrieving all BenefitItems", e);
        }
    }

    public List<BenefitItem> getByBenefitId(int benefitId) {
        String sql = "SELECT * FROM BenefitItem WHERE benefit_id = ?";
        try {
            return jdbcTemplate.query(sql, this::mapResultSet, benefitId);
        } catch (Exception e) {
            throw new DLException("Error retrieving BenefitItems for benefit ID: " + benefitId, e);
        }
    }

    public void insert(BenefitItem item) {
        String sql = """
            INSERT INTO BenefitItem 
            (benefit_id, region_id, from_date, to_date, allow_coefficient, use_standard_amount, amount, coefficient)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
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

                return ps;
            });
        } catch (Exception e) {
            throw new DLException("Error inserting BenefitItem", e);
        }
    }

    public void update(int id, BenefitItem item) {
        String sql = """
            UPDATE BenefitItem 
            SET benefit_id = ?, region_id = ?, from_date = ?, to_date = ?, allow_coefficient = ?, 
                use_standard_amount = ?, amount = ?, coefficient = ?
            WHERE benefit_item_id = ?
        """;

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
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

                ps.setInt(9, id);

                return ps;
            });
        } catch (Exception e) {
            throw new DLException("Error updating BenefitItem with ID " + id, e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM BenefitItem WHERE benefit_item_id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new DLException("Error deleting BenefitItem with ID " + id, e);
        }
    }
}
